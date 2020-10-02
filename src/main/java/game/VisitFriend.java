package main.java.game;

import main.java.dao.*;
import main.java.socialmagnet.*;
import java.util.*;

public class VisitFriend {
    private Member member;
    private ArrayList<String> friends;

    public VisitFriend(Member member){
        this.member = member;
    }

    public void display(){
        MemberDAO memberDAO = new MemberDAO();
        this.member = memberDAO.getMember(member.getUsername());
        String rank = memberDAO.getRank(member);
        System.out.println();
        System.out.println("== Social Magnet :: City Farmers :: Visit Friend ==");
        System.out.println("Welcome, " + member.getName() + "!");
        System.out.println("Title: " + rank + "\t\t Gold: "+ member.getGold()+ " gold");
        System.out.println();
        System.out.println("My Friends:");
        FriendsDAO friendsDAO = new FriendsDAO();
        ArrayList<String> friends = friendsDAO.getFriends(member.getUsername());
        this.friends = friends;
        if(friends.size() == 0){
            System.out.println("You have no friends currently. Go make a friend!");
        }
        else{
            for(int i = 1 ; i <=friends.size(); i++){
                String name = memberDAO.getMember(friends.get(i-1)).getName();
                System.out.println(i + ". " + name + " ("+  friends.get(i-1) + ")");
            }
        }
        System.out.println();
        System.out.print("[M]ain | City [F]armers | Select choice >");
    }

    public void start() throws MainMenuException{
        Scanner sc = null;
        while(sc == null){
            sc = new Scanner(System.in);
            display();
            String choice = sc.nextLine();
            if(choice.equals("M")){
                throw new MainMenuException("Exiting to MainMenu");
            }
            if(choice.equals("F")){
                break;
            }
            try{
                int personToSteal = Integer.parseInt(choice);
                String usernameToStealFrom = friends.get(personToSteal-1);
                MemberDAO memberDAO = new MemberDAO();
                FarmLand friendFarmLand = new FarmLand(member, memberDAO.getMember(usernameToStealFrom) , true);
                friendFarmLand.startFriendGame();
            }
            catch(NumberFormatException e){
                System.out.println("Please choose [M] , [C] or a number above.");
                sc= null;
            }
            catch(ArrayIndexOutOfBoundsException e){
                System.out.println("Please choose a number above.");
                sc = null;
            }
        }
    }
}