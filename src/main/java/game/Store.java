package main.java.game;

import main.java.dao.*;
import main.java.socialmagnet.*;
import java.util.*;

public class Store{
    private Member member;
    private ArrayList<Crop> cropList;

    public Store(Member member, ArrayList<Crop> cropList){
        MemberDAO memberDAO = new MemberDAO();
        this.member = memberDAO.getMember(member.getUsername());
        this.cropList = cropList;
    }

    public void display(){
        MemberDAO memberDAO = new MemberDAO();
        this.member = memberDAO.getMember(member.getUsername());
        String rank = memberDAO.getRank(member);
        System.out.println();
        System.out.println("== Social Magnet :: City Farmers :: My Store ==");
        System.out.println("Welcome, " + member.getName() + "!");
        System.out.println("Title: " + rank + "\t\t Gold: "+ member.getGold()+ " gold");
        System.out.println();
        System.out.println("Seeds Available:");

        for (int i = 0 ; i<cropList.size(); i++){
            Crop crop = cropList.get(i);
            String space = crop.getSpace();
            System.out.println(i+1+". " +crop.getName()+ space + "- " + crop.getCost() + "gold");
            System.out.println("   Harvest in: " + crop.getRipeTime() + " mins" );
            System.out.println("   XP Gained: " + crop.getXp());
            System.out.println();
        }


    }

    public void start() throws MainMenuException{
        Scanner sc = null;
        int choice;
        while (sc == null){
            display();
            sc = new Scanner(System.in);
            System.out.print("[M]ain | City [F]armers | Select choice > ");
            String option = sc.nextLine();
            if(option.equals("M")){
                throw new MainMenuException("Exiting MainMenu");
            }
            else if(option.equals("F")){
                break;
            }

            try{
                System.out.print("Enter quantity > ");
                int quantity = sc.nextInt();
                choice = Integer.parseInt(option);
                Crop crop = cropList.get(choice-1);
                int currentGold = member.getGold();
                int cost = crop.getCost() * quantity;

                if( currentGold - cost < 0){
                    System.out.println("You have not enough gold to purchase the item. Please choose again.");
                    sc = null;
                    continue;
                }
                buySeeds(crop,quantity);
                sc = null;
            }
            catch(NumberFormatException e){
                System.out.println("Please enter a valid choice!");
                sc = null;
            }


        }
    }

    public void buySeeds(Crop crop , int quantity){
        int currentGold = member.getGold();
        int cost = crop.getCost() * quantity;
        SeedDAO seedDAO = new SeedDAO();
        int seedQuantity = seedDAO.getSeedQuantity(member.getUsername(),crop.getName());
        boolean updateSeedsSuccess = false;
        updateSeedsSuccess = seedDAO.updateSeeds(member.getUsername(), crop.getName(), quantity);
        if(updateSeedsSuccess){
            System.out.println(quantity + " bags of seeds purchased for " + cost  + " golds");
            MemberDAO memberDAO = new MemberDAO();
            memberDAO.updateGold(member, currentGold - cost);
                }
    }
}