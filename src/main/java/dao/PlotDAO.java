package main.java.dao;

import main.java.socialmagnet.*;
import java.sql.*;
import java.util.*;

public class PlotDAO {

    public HashMap<Integer, ArrayList<Object>> getPlots(String username) {
        ConnectionManager cm = new ConnectionManager();
        ArrayList<Object> data = new ArrayList<>();
        data.add(username);
        String sql = "select * from plot where username = ?";
        ResultSet rs = (ResultSet) cm.getConnection(sql, data, false);
        HashMap<Integer, ArrayList<Object>> plots = new HashMap<>();
        try {
            while (rs != null && rs.next()) {
                ArrayList<Object> crop_info = new ArrayList<>();
                if (rs.getString("crop") == null || rs.getString("time_planted") == null) {
                    plots.put(rs.getInt("plot_number"), crop_info);
                } else {
                    crop_info.add(rs.getString("crop"));
                    crop_info.add(rs.getTimestamp("time_planted"));
                    plots.put(rs.getInt("plot_number"), crop_info);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Plot cannot be found.");
        }
        return plots;
    }

    public boolean clearPlot(String username, int plot_number) {
        ConnectionManager cm = new ConnectionManager();
        ArrayList<Object> data = new ArrayList<>();
        data.add(username);
        data.add(plot_number);
        String sql = "UPDATE plot SET crop= null ,time_planted = null where username = ? and plot_number = ?";
        int rows_changed = (Integer) cm.getConnection(sql, data, true);
        if (rows_changed == 1) {
            return true;
        }
        return false;
    }

    public boolean plantPlot(String username, int plot_number, String cropName) {
        ConnectionManager cm = new ConnectionManager();
        ArrayList<Object> data = new ArrayList<>();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        data.add(cropName);
        data.add(timestamp);
        data.add(username);
        data.add(plot_number);
        String sql = "UPDATE plot SET crop= ? ,time_planted = ? where username = ? and plot_number = ?";
        int rows_changed = (Integer) cm.getConnection(sql, data, true);
        if (rows_changed == 1) {
            return true;
        }
        return false;
    }

    public void populatePlots(Member member) {
        if (member.getGold() <= 50 && member.getExperience() == 0) {
            for (int i = 1; i <= 5; i++) {
                ConnectionManager cm = new ConnectionManager();
                ArrayList<Object> data = new ArrayList<>();
                Timestamp timestamp = null;
                data.add(member.getUsername());
                data.add(i);
                String sql = "INSERT INTO plot (username, plot_number, crop, time_planted) values (?,?,null,null)";
                int rows_changed = (Integer) cm.getConnection(sql, data, true);

            }
        }

    }

    public void addPlot(Member member){
        ConnectionManager cm = new ConnectionManager();
        ArrayList<Object> data = new ArrayList<>();
        HashMap<Integer,ArrayList<Object>> plots = getPlots(member.getUsername());
        data.add(member.getUsername());
        data.add(plots.size()+ 1 );
        String sql = "INSERT INTO plot (username, plot_number, crop, time_planted) values (?,?,null,null)";
        int rows_changed = (Integer) cm.getConnection(sql, data, true);
    }

    public void checkIfCorrectNumberOfPlots(Member member , HashMap<Integer, ArrayList<Object>> plots){
        MemberDAO memberDAO = new MemberDAO();
        String rank = memberDAO.getRank(member);
        int numberOfPlots = memberDAO.getPlotsAccordingToRank(member);
        if(plots.size() < numberOfPlots){
            int difference = numberOfPlots - plots.size();
            for(int i = 0 ; i <difference ; i++){
                addPlot(member);
            }
        }



    }
}