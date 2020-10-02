package main.java.socialmagnet;

import main.java.dao.*;
import java.sql.*;
import java.util.*;

public class NewsFeed {
    private FriendsDAO friendsDAO = new FriendsDAO();
    private PostDAO postDAO = new PostDAO();
    private String username;

    public NewsFeed(String username) {
        this.username = username;
    }

    public void start () {
        char key;
        do {
            HashMap<Integer, Integer> postIDMap = display();
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            key = input.charAt(0);
            switch (key) {
                case 'M':
                    break;
                case 'T':
                    int post = Integer.parseInt("" + input.charAt(1));
                    ViewThread thread = new ViewThread(postIDMap.get(post), username);

                    thread.start();
            }
        } while (key != 'M');
    }

    public void menu() {
        // This function is to display the menu for the news feed
        System.out.print("[M]ain | [T]hread > ");
    }

    public HashMap<Integer, Integer> display () {
        System.out.println("\n\n== Social Magnet :: News Feed ==");

        ArrayList<String> userFriends = friendsDAO.getFriends(username);
        ArrayList<Object> data = new ArrayList<>();
        data.add(username);
        for (String friend : userFriends) {
            data.add(friend);
        }

        // Retrieve the top 5 posts based on timing
        ConnectionManager cm = new ConnectionManager();
        String sql = "select * from Post where username in (";
        for (int i = 0; i < data.size(); i++) {
            sql += "?";
            if (i != data.size() - 1) {
                sql += ",";
            }
        }
        sql += ") order by date desc limit 5";
        ResultSet rs = (ResultSet)cm.getConnection(sql, data, false);
        ArrayList<Post> posts = new ArrayList<Post>();
        HashMap<Integer, Integer> postIDs = new HashMap<Integer, Integer>();
        try {
            int i = 1;
            while(rs.next()) {
                posts.add(new Post(rs.getInt("postID"), rs.getString("username"), rs.getString("message"), rs.getDate("date")));
                postIDs.put(i, rs.getInt("postID"));
                i++;
            }
        }
        catch (Exception e) {
            System.out.println("There is an issue with mySQL");
        }

        // Print out Message , Likes & Dislikes and Replies for each Post
        int count = 1;
        for (Post post : posts) {
            System.out.println(count + " " + post.getAuthor() + ": " + post.getMessage());
            ArrayList<Integer> likeDislikes = postDAO.numberOfLikesDislikes(post.getPostID());
            System.out.println("[ " + likeDislikes.get(0) + " like, " + likeDislikes.get(1) + " dislikes ]");

            ArrayList<String> replies = postDAO.get3Replies(post.getPostID());
            for (String reply : replies) {
                System.out.println("  " + count + reply);
            }
            System.out.println();
            count++;
        }
        menu();
        return postIDs;
    }
}