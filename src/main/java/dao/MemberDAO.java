package main.java.dao;

import main.java.socialmagnet.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class MemberDAO {

    public Member getMember(String username) {
        ConnectionManager cm = new ConnectionManager();
        ArrayList<Object> data = new ArrayList<>();
        data.add(username);
        String sql = "select * from member where username = ?";

        ResultSet rs = (ResultSet)cm.getConnection(sql, data, false);
        Member member = null;
        try {
            while(rs!= null && rs.next()) {
                member = new Member(rs.getString("username"), rs.getString("name"), rs.getString("password"), rs.getInt("experience"), rs.getInt("gold"));
            }
        }
        catch (SQLException e) {
            System.out.println("Member cannot be found.");
        }
        return member;
    }

    public boolean addMember(String username, String name, String password, int experience, int gold) {
        ConnectionManager cm = new ConnectionManager();
        String sql = "INSERT INTO member (username, name, password, experience, gold) VALUES (?, ?, ?, ?, ?)";
        ArrayList<Object> data = new ArrayList<>();
        data.add(username);
        data.add(name);
        data.add(password);
        data.add(experience);
        data.add(gold);
        int if_sql_goes_through = (Integer)cm.getConnection(sql, data, true);
        if (if_sql_goes_through == 1) {
            return true;
        } else {
            return false;
        }
    }

    public String getRank(Member member){
        String rank = null;
        int xp = 0;
        int no_of_plots = 0;
        int count = 0;
        int currentExperience = member.getExperience();
        ArrayList<String> rankAccordingToXP = new ArrayList<>();
        try{
        Scanner sc = new Scanner(new FileInputStream("data/rank.csv"));
        sc.nextLine();
        sc.useDelimiter("\r\n|,");
        while(sc.hasNext()){
            rank = sc.next();
            xp = sc.nextInt();
            no_of_plots = sc.nextInt();
            rankAccordingToXP.add(rank);
            if(currentExperience == 0){
                return "Novice";
            }
            if(currentExperience < xp ){
                return rankAccordingToXP.get(count-1);
            }
            count++;
        }
        }
        catch(FileNotFoundException e){
            System.out.println("File not found.");
        }
        return rank;
    }

    public String getRankAccordingToExperience(int experience){
        String rank = null;
        int xp = 0;
        int no_of_plots = 0;
        int count = 0;
        int currentExperience = experience;
        ArrayList<String> rankAccordingToXP = new ArrayList<>();
        try{
        Scanner sc = new Scanner(new FileInputStream("data/rank.csv"));
        sc.nextLine();
        sc.useDelimiter("\r\n|,");
        while(sc.hasNext()){
            rank = sc.next();
            xp = sc.nextInt();
            no_of_plots = sc.nextInt();
            rankAccordingToXP.add(rank);
            if(currentExperience == 0){
                return "Novice";
            }
            if(currentExperience < xp ){
                return rankAccordingToXP.get(count-1);
            }
            count++;
        }
        }
        catch(FileNotFoundException e){
            System.out.println("File not found.");
        }
        return rank;
    }

    //return the number of plots a farmer is supposed to have according to his/her xp
    public int getPlotsAccordingToRank(Member member){
        String rank = null;
        int xp = 0;
        int no_of_plots = 0;
        int count = 0;
        ArrayList<Integer> plotNumberAccordingToXP = new ArrayList<>();
        try{
        Scanner sc = new Scanner(new FileInputStream("data/rank.csv"));
        sc.nextLine();
        sc.useDelimiter("\r\n|,");
        while(sc.hasNext()){
            rank = sc.next();
            xp = Integer.parseInt(sc.next());
            no_of_plots = Integer.parseInt(sc.next());
            plotNumberAccordingToXP.add(no_of_plots);
            if(member.getExperience() < xp){
                return plotNumberAccordingToXP.get(count-1);
            }
            count++;
        }
        }
        catch(FileNotFoundException e){
            System.out.println("File not found.");
        }
        return count;
    }

    public boolean updateGold(Member member, int quantityOfGold){
        ConnectionManager cm = new ConnectionManager();
        ArrayList<Object> data = new ArrayList<>();
        data.add(quantityOfGold);
        data.add(member.getUsername());
        String sql = "UPDATE member SET gold = ? WHERE username = ?";
        Integer updateResult = (Integer)cm.getConnection(sql, data, true);
        if(updateResult == 1){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean updateExperience(Member member , int experience){
        ConnectionManager cm = new ConnectionManager();
        ArrayList<Object> data = new ArrayList<>();
        data.add(experience);
        data.add(member.getUsername());
        String sql = "UPDATE member SET experience = ? WHERE username = ?";
        Integer updateResult = (Integer)cm.getConnection(sql, data, true);
        if(updateResult == 1){
            return true;
        }
        else{
            return false;
        }
    }
}