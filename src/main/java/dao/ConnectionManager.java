package main.java.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.*;

public class ConnectionManager {

    public Object getConnection(String sql, ArrayList<Object> data, boolean insertUpdateDelete) {
        String host = "localhost";
        int port = 3306;
        String dbName = "magnet";
        String dbURL = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?useSSL=false";

        String username = "root";
        String password = "";

        ResultSet rs = null;

        // step 1: Loads the JDBC driver
        try{
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();
        }
        catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e){
            System.out.println(e);
            System.out.println("Driver not found.");
        }

        // step 2: Gets a connection to the database
        try {
            Connection conn = DriverManager.getConnection(dbURL, username, password);
            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);
            // System.out.println(data);
            for (int i = 1; i<data.size()+1; i++) {
                if (data.get(i-1) instanceof java.lang.String) {
                    stmt.setString(i, (String) data.get(i-1));
                }
                if (data.get(i-1) instanceof java.lang.Integer) {
                    stmt.setInt(i, (Integer) data.get(i-1));
                }
                if (data.get(i-1) instanceof java.sql.Timestamp) {
                    stmt.setTimestamp(i, (Timestamp) data.get(i-1));
                }
            }
            // step 4: executes the query
            if (insertUpdateDelete) {
                return stmt.executeUpdate();
            } else {
                rs = stmt.executeQuery();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Please check SQL query again.");
            // e.printStackTrace();
        }

        return rs;
    }
}