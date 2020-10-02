package main.java.dao;

import java.util.*;
import java.sql.*;

public class GiftDAO {

    public boolean checkIfGiftGivenToday(String username, String friendUsername){
        boolean giftavailable = true;
        ConnectionManager cm = new ConnectionManager();
        ArrayList<Object> data = new ArrayList<>();
        data.add(username);
        data.add(friendUsername);
        String sql = "SELECT * FROM gifts where sender_username = ? and receiver_username = ? ORDER BY time_given DESC LIMIT 1;";
        ResultSet rs = (ResultSet) cm.getConnection(sql, data, false);


        try{
            if(rs.next() == false){
                giftavailable = false;
            }
            else{
                do{
                Timestamp time_given = rs.getTimestamp("time_given");
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                long diff_in_minutes = (timestamp.getTime() - time_given.getTime()) / 60000;
                if(diff_in_minutes > 1440){
                    giftavailable = false;
                }
            }while(rs!= null && rs.next());
            }


        }
        catch(SQLException e){
            System.out.println("Gift Table cannot be found.");
        }
        return giftavailable;

    }

    public void giveGift(String username, String friendUsername , String crop){
        ConnectionManager cm = new ConnectionManager();
        ArrayList<Object> data = new ArrayList<>();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        data.add(username);
        data.add(friendUsername);
        data.add(crop);
        data.add(timestamp);
        String sql = "INSERT INTO gifts (sender_username, receiver_username, gift, time_given) values (?, ? , ? , ?)";
        cm.getConnection(sql, data, true);
    }

    public ArrayList<ArrayList<String>> getGifts(String friendUsername){
        ConnectionManager cm = new ConnectionManager();
        ArrayList<Object> data = new ArrayList<>();
        ArrayList<ArrayList<String>> listOfGifts = new ArrayList<ArrayList<String>>();
        ArrayList<String> gift = new ArrayList<String>();

        String sql = "SELECT sender_username, receiver_username, gift from gifts where receiver_username = ? order by time_given desc";

        data.add(friendUsername);

        ResultSet rs = (ResultSet) cm.getConnection(sql, data, false);

        try {
            while(rs.next()) {
                gift.add(rs.getString("sender_username"));
                gift.add(rs.getString("receiver_username"));
                gift.add(rs.getString("gift"));

                listOfGifts.add(gift);
                gift = new ArrayList<String>();
            }
        } catch (Exception e) {
            System.out.println("There is an issue getting gifts from MySQL!");
        }
        return listOfGifts;
    }

    public boolean removeGifts(String friendUsername) {

        ConnectionManager cm = new ConnectionManager();
        ArrayList<Object> data = new ArrayList<>();

        String sql = "DELETE FROM gifts where receiver_username = ?";

        data.add(friendUsername);

        try {
            int if_sql_goes_through = (Integer)cm.getConnection(sql, data, true);

            if (if_sql_goes_through == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Unable to remove gifts!");
        }
        return true;
    }
}