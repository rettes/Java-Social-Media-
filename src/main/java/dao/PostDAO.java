package main.java.dao;

import main.java.socialmagnet.*;
import java.sql.*;
import java.util.*;

public class PostDAO {
    public ArrayList<String> get3Replies(int postID) {
        ConnectionManager cm = new ConnectionManager();
        // String sql = "select TOP 3 * from Reply where postID = ? order by date asc";
        String sql = "select * from Reply where postID = ? order by date asc LIMIT 3";
        ArrayList<Object> data = new ArrayList<>();
        data.add(postID);
        ArrayList<String> replies = new ArrayList<String>();
        ResultSet rs = (ResultSet)cm.getConnection(sql, data, false);
        try {
            while(rs.next()) {
                String reply = "." + rs.getInt("replyID") + " " + rs.getString("username")
                                + ": " + rs.getString("message");
                replies.add(reply);
            }
        }
        catch (Exception e) {
            System.out.println("There is an issue getting 3 replies from mySQL");
        }
        return replies;
    }

    public ArrayList<Integer> numberOfLikesDislikes (int postID) {
        ConnectionManager cm = new ConnectionManager();
        String sql = "select sum(likes), sum(dislikes) from Like_Dislike where postID = ?";
        ArrayList<Object> data = new ArrayList<>();
        data.add(postID);

        ArrayList<Integer> results = new ArrayList<Integer>();
        ResultSet rs = (ResultSet)cm.getConnection(sql, data, false);

        try {
            if (rs.next()) {
                results.add(rs.getInt(1));
                results.add(rs.getInt(2));
            }
        }
        catch (Exception e) {
            System.out.println("There is an issue getting number of likes and dislikes from mySQL");
        }
        return results;
    }

    public Post getPost (int postID) {
        ConnectionManager cm = new ConnectionManager();
        String sql = "select * from Post where postID = ?";
        ArrayList<Object> data = new ArrayList<>();
        data.add(postID);
        ResultSet rs = (ResultSet)cm.getConnection(sql, data, false);
        Post post = null;
        try {
            while(rs.next()) {
                post = new Post(rs.getInt("postID"), rs.getString("username"), rs.getString("message"), rs.getString("posted_on"), rs.getDate("date"));
            }
        }
        catch (Exception e) {
            System.out.println("There is an issue with mySQL");
        }

        return post;
    }

    public boolean addPost(String username, String message, String postedOn, Timestamp datetime) {
        ConnectionManager cm = new ConnectionManager();
        String sql = "INSERT INTO post (username, message, posted_on, date) VALUES (?,?,?,?)";

        ArrayList<Object> data = new ArrayList<>();
        data.add(username);
        data.add(message);
        data.add(postedOn);
        data.add(datetime);

        try {
            int if_sql_goes_through = (Integer)cm.getConnection(sql, data, true);
            System.out.println(if_sql_goes_through);
            if (if_sql_goes_through == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
            System.out.println("There is an error in posting your message to MySQL.");
        }
        return false;

    }

    /*
    get username of everyone who Liked the post
    */
    public ArrayList<String> getLikes (int postID) {
        ArrayList<String> likes = new ArrayList<>();

        ConnectionManager cm = new ConnectionManager();
        String sql = "select username from Like_Dislike where postID = ? and likes = 1";
        ArrayList<Object> data = new ArrayList<Object>();
        data.add(postID);
        ResultSet rs = (ResultSet)cm.getConnection(sql, data, false);
        try {
            while(rs.next()) {
                likes.add(rs.getString("username"));
            }
        }
        catch (Exception e) {
            System.out.println("There is an issue with mySQL");
        }
        return likes;
    }

    /*
    get username of everyone who Disliked the post
    */
    public ArrayList<String> getDislikes (int postID) {
        ArrayList<String> likes = new ArrayList<>();

        ConnectionManager cm = new ConnectionManager();
        String sql = "select username from Like_Dislike where postID = ? and dislikes = 1";
        ArrayList<Object> data = new ArrayList<Object>();
        data.add(postID);
        ResultSet rs = (ResultSet)cm.getConnection(sql, data, false);
        try {
            while(rs.next()) {
                likes.add(rs.getString("username"));
            }
        }
        catch (Exception e) {
            System.out.println("There is an issue with mySQL");
        }
        return likes;
    }

    /*
    Helper function: Get the number of replies for the post
    */
    public int numberOfReplies (int postID) {
        int num = 0;
        ConnectionManager cm = new ConnectionManager();
        String sql = "select count(*) from reply where postID = ?";
        ArrayList<Object> data = new ArrayList<>();
        data.add(postID);
        ResultSet rs = (ResultSet)cm.getConnection(sql, data, false);
        try {
            while(rs.next()) {
                num = rs.getInt("count(*)");
            };
        }
        catch (Exception e) {
            System.out.println("There is an issue with mySQL");
        }
        return num;
    }

    /*
    Insert tagged users into tagged_people table
    */
    public boolean addTaggedUsers(int latestPostID, String username) {
        ArrayList<Object> data = new ArrayList<>();

        ConnectionManager cm = new ConnectionManager();

        String sql = "insert into tagged_people (postID, tagged_username) VALUES (?,?)";

        data.add(latestPostID);
        data.add(username);

        int if_sql_goes_through = (Integer)cm.getConnection(sql, data, true);

        if (if_sql_goes_through == 1) {
            return true;
        } else {
            return false;
        }
    }

    /*
    Get TOP 5 most-recent posts and gift posts
    */
    public ArrayList<Post> get5Posts (String username) {
        ConnectionManager cm = new ConnectionManager();

        String sql = "select post.postID, username, message, posted_on, date from POST, tagged_people where post.postID = tagged_people.postID and tagged_username = ? UNION select * from POST where username = ? and posted_on = ? UNION select * from POST where posted_on = ? UNION select null as postID, sender_username as username, gift, receiver_username as posted_on, time_given as date from gifts where receiver_username = ? order by date desc LIMIT 5";

        ArrayList<Object> data = new ArrayList<Object>();
        ArrayList<Post> post = new ArrayList<Post>();

        data.add(username);
        data.add(username);
        data.add(username);
        data.add(username);
        data.add(username);

        ResultSet rs = (ResultSet)cm.getConnection(sql, data, false);

        try {
            while(rs.next()) {
                post.add(new Post(rs.getInt("postID"), rs.getString("username"), rs.getString("message"), rs.getString("posted_on"), rs.getDate("date")));
            }
        }
        catch (Exception e) {
            System.out.println("There is an issue with mySQL");
        }

        return post;
    }

    /*
    Add a reply to a specific post
    */
    public boolean addReply(int replyID, int postID, String username, String reply, Timestamp datetime) {
        ConnectionManager cm = new ConnectionManager();
        String sql = "INSERT INTO Reply values (?, ?, ?, ?, ?)";

        ArrayList<Object> data = new ArrayList<>();
        data.add(replyID);
        data.add(postID);
        data.add(username);
        data.add(reply);
        data.add(datetime);

        // System.out.println(data);

        try {
            // System.out.println((Integer)cm.getConnection(sql, data, true));
            int if_sql_goes_through = (Integer)cm.getConnection(sql, data, true);
            // System.out.println(if_sql_goes_through);
            if (if_sql_goes_through == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }

    /*
    GET latest post id by userID
    */
    public int getLatestPostID (String username) {
        ConnectionManager cm = new ConnectionManager();

        String sql = "select postID from POST where username = ? order by postID desc limit 1";

        ArrayList<Object> data = new ArrayList<Object>();
        int postID = 0;

        data.add(username);

        ResultSet rs = (ResultSet)cm.getConnection(sql, data, false);

        try {
            while (rs.next()) {
                postID = rs.getInt("postID");
            }
        } catch (Exception e) {
            System.out.println("There is an issue getting the latest Post ID by " + username);
        }
        return postID;
    }

    /*
    Check if use has liked or disliked a post
    */
    public int[] checkLike(int postID, String username) {
        ConnectionManager cm = new ConnectionManager();

        String sql = "select * from like_dislike where postID = ? and username = ?";

        ArrayList<Object> data = new ArrayList<Object>();
        int[] likeDislike = new int[0];
        data.add(postID);
        data.add(username);

        try {
            ResultSet rs = (ResultSet)cm.getConnection(sql, data, false);
            // if (rs.wasNull()) {
            // }
            while (rs.next()) {
                likeDislike = new int[2];
                likeDislike[0] = rs.getInt("likes");
                likeDislike[1] = rs.getInt("dislikes");
            }
        } catch (Exception e) {
            System.out.println("There is an issue getting the latest Post ID by " + username);
        }
        return likeDislike;
    }

    public boolean updateLikeStatus (int postID, String username, char status, boolean newStatus) {
        ConnectionManager cm = new ConnectionManager();

        String sql = "";
        if (newStatus) {
            sql = "INSERT INTO like_dislike VALUES (?, ?, ?, ?)";
        } else {
            sql = "UPDATE like_dislike SET likes = ?, dislikes = ? WHERE postID = ? AND username = ?";
        }

        ArrayList<Object> data = new ArrayList<Object>();

        data.add(postID);
        data.add(username);
        if (status == 'L') {
            data.add(1);
            data.add(0);
        } else if (status == 'D') {
            data.add(0);
            data.add(1);
        }

        try {
            int if_sql_goes_through = (Integer)cm.getConnection(sql, data, true);
            if (if_sql_goes_through == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error with Like/Dislike. Please try again.");
        }
        return false;
    }

    public String getauthor (int postID) {
        ConnectionManager cm = new ConnectionManager();

        String sql = "select username from POST where postID = ?";

        ArrayList<Object> data = new ArrayList<Object>();
        String author = "";

        data.add(postID);

        ResultSet rs = (ResultSet)cm.getConnection(sql, data, false);

        try {
            while (rs.next()) {
                author = rs.getString("username");
            }
        } catch (Exception e) {
            System.out.println("There is an issue getting the author of Post ID " + postID);
        }
        return author;
    }

    public boolean deleteTag (int postID, String username) {
        ConnectionManager cm = new ConnectionManager();

        String sql = "DELETE FROM tagged_people WHERE postID = ? and tagged_username = ?";

        ArrayList<Object> data = new ArrayList<Object>();
        data.add(postID);
        data.add(username);

        try {
            int if_sql_goes_through = (Integer)cm.getConnection(sql, data, true);
            if (if_sql_goes_through == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Unable to delete tag.");
        }
        return false;
    }

    public boolean deleteAllTags (int postID) {
        if (!hasExists("tagged_people", postID)) {
            return true;
        }
        ConnectionManager cm = new ConnectionManager();

        String sql = "DELETE FROM tagged_people WHERE postID = ?";

        ArrayList<Object> data = new ArrayList<Object>();
        data.add(postID);

        try {
            int if_sql_goes_through = (Integer)cm.getConnection(sql, data, true);
            if (if_sql_goes_through == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Unable to delete tags.");
        }
        return true;
    }

    public boolean deleteAllReplies (int postID) {
        if (!hasExists("reply", postID)) {
            return true;
        }
        ConnectionManager cm = new ConnectionManager();

        String sql = "DELETE FROM reply WHERE postID = ?";

        ArrayList<Object> data = new ArrayList<Object>();
        data.add(postID);

        try {
            int if_sql_goes_through = (Integer)cm.getConnection(sql, data, true);
            if (if_sql_goes_through == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Unable to delete replies.");
        }
        return true;
    }

    public boolean deleteAllLikeDislike(int postID) {
        if (!hasExists("like_dislike", postID)) {
            return true;
        }
        ConnectionManager cm = new ConnectionManager();

        String sql = "DELETE FROM like_dislike WHERE postID = ?";

        ArrayList<Object> data = new ArrayList<Object>();
        data.add(postID);

        try {
            int if_sql_goes_through = (Integer)cm.getConnection(sql, data, true);
            if (if_sql_goes_through == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Unable to delete likes.");
        }
        return true;
    }
    public boolean deletePost (int postID) {
        if (!hasExists("post", postID)) {
            return true;
        }
        ConnectionManager cm = new ConnectionManager();

        String sql = "DELETE FROM post WHERE postID = ?";

        ArrayList<Object> data = new ArrayList<Object>();
        data.add(postID);

        try {
            int if_sql_goes_through = (Integer)cm.getConnection(sql, data, true);
            if (if_sql_goes_through == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Unable to delete post.");
        }
        return true;
    }

    public boolean hasExists(String tableName, int postID) {
        ConnectionManager cm = new ConnectionManager();
        String sql = "select count(*) from " + tableName + " where postID = ?";
        ArrayList<Object> data = new ArrayList<>();
        data.add(postID);
        ResultSet rs = (ResultSet)cm.getConnection(sql, data, false);

        try {
            while(rs.next()) {
                if (rs.getInt("count(*)") == 0) {
                    return false;
                }
                else {
                    return true;
                }
            };
        }
        catch (Exception e) {
            System.out.println("There is an issue with mySQL");
        }
        return false;
    }

    public ArrayList<String> getTaggedPeople (int postID) {
        ConnectionManager cm = new ConnectionManager();
        String sql = "select tagged_username from Tagged_People where postID = ?";

        ArrayList<Object> data = new ArrayList<>();
        data.add(postID);

        ResultSet rs = (ResultSet)cm.getConnection(sql, data, false);
        ArrayList<String> taggedUsernames = new ArrayList<String>();

        try {
            while(rs.next()) {
                taggedUsernames.add(rs.getString("tagged_username"));
            }
        }
        catch (Exception e) {
            System.out.println("There is an issue getting tagged_usernames from MySQL!");
        }
        return taggedUsernames;
    }
}
