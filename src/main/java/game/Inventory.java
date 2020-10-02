package main.java.game;

import main.java.dao.*;
import main.java.socialmagnet.*;
import java.util.*;

public class Inventory{
    private Member member;
    private ArrayList<Seed> arrayOfSeeds;

    public Inventory(Member member, ArrayList<Seed> arrayOfSeeds){
        this.member = member;
        this.arrayOfSeeds = arrayOfSeeds;
    }

    public void display(){
        MemberDAO memberDAO = new MemberDAO();
        this.member = memberDAO.getMember(member.getUsername());
        String rank = memberDAO.getRank(member);
        System.out.println();
        System.out.println("== Social Magnet :: City Farmers :: Inventory ==");
        System.out.println("Welcome, " + member.getName() + "!");
        System.out.println("Title: " + rank + "\t\t Gold: "+ member.getGold()+ " gold");
        System.out.println();
        System.out.println("My Seeds");
        for(int i = 0 ; i< arrayOfSeeds.size(); i++){
            System.out.println(i+1 + ". " + arrayOfSeeds.get(i).getQuantity() + " Bags of " + arrayOfSeeds.get(i).getCropName());
        }
        System.out.print("[M]ain | City [F]armers | Enter your choice > ");
    }

    public void start() throws MainMenuException {
        Scanner sc = new Scanner(System.in);
        String choice;

        do{
            display();
            choice = sc.nextLine();
            switch(choice){
                case "M":
                    throw new MainMenuException("Exiting to MainMenu");
                case "F":
                    break;
            }
        } while (!choice.equals("F"));
    }
}