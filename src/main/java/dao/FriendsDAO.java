package main.java.dao;

import java.sql.*;
import java.util.*;

public class FriendsDAO{

    public ArrayList<String> getFriends(String username){
        ConnectionManager cm = new ConnectionManager();
        ArrayList<Object> data = new ArrayList<>();
        ArrayList<String> result = new ArrayList<String>();
        String sql = "select username, friend_username from friends where (username = ? or friend_username = ?) and status = 'accept'";

        data.add(username);
        data.add(username);

        ResultSet rs = (ResultSet)cm.getConnection(sql, data, false);
        try {
            while (rs.next()) {
                String name = rs.getString("username");
                String friend_username = rs.getString("friend_username");
                if (name.equals(username)) {
                    result.add((rs.getString("friend_username")));
                } else if (friend_username.equals(username)) {
                    result.add((rs.getString("username")));
                }
            }
        } catch (SQLException e) {
            System.out.println("There is an issue with MySQL");
        }

        return result;
    }

    public ArrayList<String> getFriendRequests(String username){
        ConnectionManager cm = new ConnectionManager();
        ArrayList<Object> data = new ArrayList<>();
        ArrayList<String> result = new ArrayList<String>();
        String sql = "select username from friends where friend_username = ? and status = 'pending'";

        data.add(username);

        ResultSet rs = (ResultSet)cm.getConnection(sql, data, false);
        try {
            while (rs.next()) {
                result.add((rs.getString("username")));
            }
        } catch (SQLException e) {
            System.out.println("There is an issue with MySQL");
        }

        return result;
    }

    public int sendRequest(String ownUsername, String usernameToAdd) {
        ConnectionManager cm = new ConnectionManager();
        ArrayList<Object> data = new ArrayList<>();
        int count = 0;
        int isAdded = 0; //1 for success, 0 for request exist already, 2 for user do not exist

        //check if requested person exists
        String sql = "select count(*) from member where username = ?";

        data.add(usernameToAdd);

        ResultSet rs = (ResultSet)cm.getConnection(sql, data, false);

        try {
            while (rs.next()) {
                count = rs.getInt("count(*)");
            }
        } catch (SQLException e) {
            System.out.println("There is an issue with MySQL");
        }

        if (count == 0 ) {
            return isAdded = 2;
        }

        data.clear();
        //check if the person has requested for you, or if you have requested for the person
        sql = "select count(*) from friends where (username = ? and friend_username = ?) or (username = ? and friend_username = ?) and status = 'pending'";

        data.add(ownUsername);
        data.add(usernameToAdd);
        data.add(usernameToAdd);
        data.add(ownUsername);

        rs = (ResultSet)cm.getConnection(sql, data, false);
        try {
            while (rs.next()) {
                count = rs.getInt("count(*)");
            }
        } catch (SQLException e) {
            System.out.println("There is an issue with MySQL");
        }
        if (count == 0) {
            data.clear();
            sql = "insert into friends values (? , ? , 'pending')";
            data.add(ownUsername);
            data.add(usernameToAdd);

            isAdded = (Integer)cm.getConnection(sql, data, true);
        }
        return isAdded;
    }

    public void acceptRequest(String ownUsername, String usernameToAccept) {
        ConnectionManager cm = new ConnectionManager();
        ArrayList<Object> data = new ArrayList<>();
        String sql = "update friends set status = 'accept' where username = ? and friend_username = ? and status = 'pending'";

        data.add(usernameToAccept);
        data.add(ownUsername);

        cm.getConnection(sql, data, true);
    }

    public void rejectRequest(String ownUsername, String usernameToReject) {
        ConnectionManager cm = new ConnectionManager();
        ArrayList<Object> data = new ArrayList<>();
        String sql = "delete from friends where username = ? and friend_username = ? and status = 'pending'";

        data.add(usernameToReject);
        data.add(ownUsername);

        cm.getConnection(sql, data, true);
    }

    public void removeFriend(String ownUsername, String usernameToRemove) {
        ConnectionManager cm = new ConnectionManager();
        ArrayList<Object> data = new ArrayList<>();
        String sql = "DELETE FROM friends where (username = ? and friend_username = ?) or (username = ? and friend_username = ?) and status = 'accept'";

        data.add(ownUsername);
        data.add(usernameToRemove);
        data.add(usernameToRemove);
        data.add(ownUsername);

        cm.getConnection(sql, data, true);
    }

    public boolean checkFriendExists(String username, String friendUsername){
        ConnectionManager cm = new ConnectionManager();
        ArrayList<Object> data = new ArrayList<>();
        data.add(username);
        data.add(friendUsername);
        String sql = "select * from friends where username = ? and friend_username = ? and status = 'accept'";
        ResultSet rs = (ResultSet)cm.getConnection(sql, data, false);
        boolean ifFriendExists = false;
        try{
            while(rs!= null && rs.next()){
                ifFriendExists = true;
            }
        }
        catch(SQLException e){
            System.out.println("There is an issue with your SQL Query.");
        }
        return ifFriendExists;
    }
}