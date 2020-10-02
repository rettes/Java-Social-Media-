package main.java.game;

import main.java.dao.*;
import main.java.socialmagnet.*;
import java.util.*;

public class SendGift {
    private Member member;
    private ArrayList<Crop> allCrops;

    public SendGift(Member member){
        this.member = member;
    }

    //starting menu
    public void display(){
        MemberDAO memberDAO = new MemberDAO();
        this.member = memberDAO.getMember(member.getUsername());
        String rank = memberDAO.getRank(member);
        System.out.println();
        System.out.println("== Social Magnet :: City Farmers :: Send a Gift ==");
        System.out.println("Welcome, " + member.getName() + "!");
        System.out.println("Title: " + rank + "\t\t Gold: "+ member.getGold()+ " gold");
        System.out.println();
        System.out.println("Gifts Available:");

        CropDAO cropDAO = new CropDAO();
        ArrayList<Crop> allCrops = cropDAO.getAllCrop();
        this.allCrops = allCrops;
        for(int i = 1; i <= allCrops.size() ; i++ ){
            System.out.println(i + ". " + "1 Bag of " + allCrops.get(i-1).getName() + " Seeds");
        }
        System.out.print("[R]eturn to main | Select choice > ");

    }

    public void start() {
        Scanner sc = null;
        String choice;
        while (sc == null){
            sc = new Scanner(System.in);
            display();
            choice = sc.nextLine();
            if(choice == "R"){
                break;
            }
            try{
                int choiceOfSeed = Integer.parseInt(choice);
                System.out.print("Send to>");
                String friends = sc.nextLine();
                String [] arrayOfFriends = friends.split(",");
                GiftDAO giftDAO = new GiftDAO();
                MemberDAO memberDAO = new MemberDAO();
                for(String friend : arrayOfFriends){
                    FriendsDAO friendsDAO = new FriendsDAO();
                    ArrayList<String> friendList = friendsDAO.getFriends(member.getUsername());
                    if(!friendList.contains(friend)){
                        System.out.println(friend + " is not your friend.");
                        continue;
                    }
                    if(!giftDAO.checkIfGiftGivenToday(member.getUsername(), friend)){
                        giftDAO.giveGift(member.getUsername(), friend, allCrops.get(choiceOfSeed-1).getName());
                        System.out.println("Gift posted to " + friend + "'s wall.");
                    }
                    else{
                        System.out.println("You have given a gift to " +friend + " today already.");
                    }
                }
            }
            catch(NumberFormatException e){
                sc = null;
                System.out.println("Please write only [R]eturn or a number.");
            }
            catch(ArrayIndexOutOfBoundsException e){
                sc= null;
                System.out.println("Please choose a crop number above.");
            }
        }
    }


}