package main.java.dao;

import main.java.game.*;
import java.util.*;
import java.sql.*;

public class SeedDAO{

    public ArrayList<Seed> getAllSeeds(String username){
        //Creating an array to hold seed objects
        ArrayList<Seed> arrayOfSeeds = new ArrayList<>();
        //Creating an array to hold the parameters needed for the sql statement
        ArrayList<Object> data = new ArrayList<>();
        //Adding the parameters into the ArrayList
        data.add(username);

        ConnectionManager cm = new ConnectionManager();
        String sql = "select * from seeds where username = ?";
        ResultSet rs = (ResultSet)cm.getConnection(sql, data, false);
        if(rs == null){
            return arrayOfSeeds;
        }
        try{
            while(rs.next()){
                arrayOfSeeds.add(new Seed(rs.getString("username"), rs.getString("crop"), rs.getInt("quantity")));
            }
        }
        catch(SQLException e){
            System.out.println("There is an issue with SQL");
        }

        return arrayOfSeeds;

    }

    public int getSeedQuantity(String username, String cropName){
        ConnectionManager cm = new ConnectionManager();
        String sql = "select * from seeds where username = ? and crop = ?";
        ArrayList<Object> data = new ArrayList<>();
        data.add(username);
        data.add(cropName);
        ResultSet rs = (ResultSet)cm.getConnection(sql, data, false);
        if (rs == null){
            return 0;
        }
        else{
            try{
                while(rs.next()){
                    return rs.getInt("quantity");
                }
            }
            catch (SQLException e){
                System.out.println("SQL issue");
                return 0;
            }

        }
        return 0;
    }

    public boolean addSeeds(String username, String cropName, int quantity){
        ConnectionManager cm = new ConnectionManager();
        String sql = "insert into seeds (username, crop, quantity) values (? , ? ,?)";
        ArrayList<Object> data = new ArrayList<>();
        data.add(username);
        data.add(cropName);
        data.add(quantity);
        Integer number = (Integer)cm.getConnection(sql,data, true);
        if(number == 1){
            return true;
        }
        return false;

    }

    public boolean updateSeeds(String username, String cropName, int quantity){
        ConnectionManager cm = new ConnectionManager();
        int checkIfSeedAvailable = getSeedQuantity(username,cropName);
        if(checkIfSeedAvailable == 0){
            addSeeds(username, cropName,quantity);
            return true;
        }
        String sql = "UPDATE seeds SET quantity = ? WHERE username = ? and crop = ?";
        ArrayList<Object> data = new ArrayList<>();
        data.add(quantity + checkIfSeedAvailable);
        data.add(username);
        data.add(cropName);
        Integer number = (Integer)cm.getConnection(sql,data,true);
        if(number == 1){
            return true;
        }
        return false;
    }
}