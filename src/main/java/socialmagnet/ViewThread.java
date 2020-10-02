package main.java.socialmagnet;

import main.java.dao.*;
import java.util.*;
import java.sql.*;

public class ViewThread {
    private int postID;
    private String username;
    private PostDAO postDAO = new PostDAO();
    private MemberDAO memberDAO = new MemberDAO();

    public ViewThread(int postID, String username) {
        this.postID = postID;
        this.username = username;
    }

    public void start() {
        char key;
        if (!(postDAO.getPost(postID) == null)) {
            do {
                display();
                Scanner sc = new Scanner(System.in);
                String input = sc.nextLine();
                key = input.charAt(0);
                switch (key) {
                    case 'M':
                        break;
                    case 'K':
                        killPost();
                        break;
                    case 'R':
                        replyPost();
                        break;
                    case 'L':
                        likeDislike('L');
                        break;
                    case 'D':
                        likeDislike('D');
                        break;
                }
            } while (key != 'M' && key != 'K');
        }
    }

    public void display() {
        /*
         * This function displays a specific post and all it's replies, likes and
         * dislikes
         */
        System.out.println("\n\n== Social Magnet :: View a Thread ==");
        // Display post
        Post post = postDAO.getPost(postID);
        System.out.printf("1 %s: %s%n", post.getAuthor(), post.getMessage());

        // Display replies
        ArrayList<String> replies = retrieveReplies();
        for (String reply : replies) {
            System.out.println("  1" + reply);
        }

        System.out.println();

        // Display Likes & dislikes
        System.out.println("Who likes this post:");
        ArrayList<String> likes = postDAO.getLikes(postID);
        int count = 1;
        for (String user : likes) {
            Member member = memberDAO.getMember(user);
            System.out.printf("  %d. %s (%s)%n", count, member.getName(), user);
            count++;
        }

        System.out.println();

        System.out.println("Who dislikes this post:");
        ArrayList<String> dislikes = postDAO.getDislikes(postID);
        count = 1;
        for (String user : dislikes) {
            Member member = memberDAO.getMember(user);
            System.out.printf("  %d. %s (%s)%n", count, member.getName(), user);
        }
        menu();
    }

    public ArrayList<String> retrieveReplies() {
        ConnectionManager cm = new ConnectionManager();
        String sql = "select * from Reply where postID = ? order by date asc";
        ArrayList<Object> data = new ArrayList<>();
        data.add(postID);
        ArrayList<String> replies = new ArrayList<String>();
        ResultSet rs = (ResultSet) cm.getConnection(sql, data, false);
        try {
            while (rs.next()) {
                String reply = "." + rs.getInt("replyID") + " " + rs.getString("username") + ": "
                        + rs.getString("message");
                replies.add(reply);
            }
        } catch (Exception e) {
            System.out.println("There is an issue with mySQL");
        }
        return replies;
    }

    public static void menu() {
        System.out.print("       [M]ain | [K]ill | [R]eply | [L]ike | [D]islike > ");
    }

    public void replyPost() {
        /*
         * This function should be called when a user views a post/thread on their wall/
         * feed and chooses to reply Gets the reply message from user and adds message
         * to database
         */

        System.out.println("Reply > ");
        Scanner sc = new Scanner(System.in);
        String reply = sc.nextLine();

        /*
         * TODO: Add message into the replies table Make sure to get the timestamp of
         * the reply Make sure to add the Reply ID according to the post ID
         */
        int replyID = postDAO.numberOfReplies(postID) + 1;
        // System.out.println("```" + postDAO.numberOfReplies(postID) + "```");
        Timestamp datetime = new Timestamp(System.currentTimeMillis());

        boolean replyAdded = postDAO.addReply(replyID, postID, username, reply, datetime);

        if (replyAdded) {
            System.out.println("Reply successful.");
        } else {
            System.out.println("Reply unsuccessful. Please try again.");
        }
    }

    public void likeDislike(char option) {

        int[] likeStatus = postDAO.checkLike(postID, username);
        boolean status = false;

        if (likeStatus.length == 0) {
            status = true;
        }

        boolean success = postDAO.updateLikeStatus(postID, username, option, status);

        if (success) {
            String str = "";
            if (option == 'L') {
                str = "liked";
            } else if (option == 'D') {
                str = "disliked";
            }
            System.out.println("You " + str + " the post!");
        } else {
            System.out.println("An error occurred. Please try again.");
        }
    }

    public void killPost() {
        // if author of post is not you, delete tags
        // if author is you, delete post, all replies and all tags
        Post curPost = postDAO.getPost(postID);
        if (username.equals(postDAO.getauthor(postID)) || username.equals(curPost.getPostedOn())) {
            boolean deletedTags = postDAO.deleteAllTags(postID);

            if (deletedTags) {
                boolean deletedReplies = postDAO.deleteAllReplies(postID);
                System.out.println("All tags of " + postID + " has been wiped");

                if (deletedReplies) {
                    boolean deleteLikeDislike = postDAO.deleteAllLikeDislike(postID);
                    System.out.println("All replies of " + postID + " has been wiped");

                    if (deleteLikeDislike) {
                        System.out.println("All likes & dislikes of " + postID + " has been wiped");
                        boolean deletePost = postDAO.deletePost(postID);

                        if (deletePost) {
                            System.out.println("Post has been wiped.");
                        } else {
                            System.out.println("Never delete post!");
                        }
                    } else {
                        System.out.println("Never delete likes!");
                    }

                } else {
                    System.out.println("Never delete replies!");
                }
            } else {
                System.out.println("Never delete tags!");
            }
        } else {
            // delete the tag
            boolean success = postDAO.deleteTag(postID, username);

            if (success) {
                System.out.println("Post has been removed from your wall");
            } else {
                System.out.println("Failed to delete tag.");
            }
        }
    }
}