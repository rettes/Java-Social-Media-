package main.java.game;

import main.java.dao.*;
import main.java.socialmagnet.*;
import java.util.*;

public class GameMenu{
    private Member member;

    public GameMenu(Member member){
        MemberDAO memberDAO = new MemberDAO();
        this.member = memberDAO.getMember(member.getUsername());
    }

    public void display(){
        MemberDAO memberDAO = new MemberDAO();
        this.member = memberDAO.getMember(member.getUsername());
        String rank = memberDAO.getRank(member);
        System.out.println();
        System.out.println("== Social Magnet :: City Farmers :: My Farmland ==");
        System.out.println("Welcome, " + member.getName() + "!");
        System.out.println("Title: " + rank + "\t\t Gold: "+ member.getGold()+ " gold");
        System.out.println();
        System.out.println("1. My Farmland");
        System.out.println("2. My Store");
        System.out.println("3. My Inventory");
        System.out.println("4. Visit Friend");
        System.out.println("5. Send Gift");
        System.out.print("[M]ain | Enter your choice > ");
    }

    public void start() throws MainMenuException {
        Scanner sc = new Scanner(System.in);
        String choice;
        do {
            display();
            choice = sc.nextLine();
            switch (choice) {
                case "1":
                    showFarmLand();
                    break;
                case "2":
                    showStore();
                    break;
                case "3":
                    showInventory();
                    break;
                case "4":
                    visitFriend();
                    break;
                case "5":
                    sendGift();
                    break;
                case "M":
                    throw new MainMenuException("Exiting to Main Menu");
                default:
                    System.out.println("Enter a valid choice!");
            }
        } while (!choice.equals("M"));
    }

    public void showFarmLand() throws MainMenuException{
        FarmLand farmLand = new FarmLand(member);
        farmLand.start();
    }

    public void showStore() throws MainMenuException{
        CropDAO cropDAO = new CropDAO();
        ArrayList<Crop> cropList = cropDAO.getAllCrop();
        Store store = new Store(member, cropList);
        store.start();
    }

    public void showInventory() throws MainMenuException{
        SeedDAO seedDAO = new SeedDAO();
        ArrayList<Seed> arrayOfSeeds = seedDAO.getAllSeeds(member.getUsername());
        Inventory inventory = new Inventory(member, arrayOfSeeds);
        inventory.start();

    }

    public void visitFriend() throws MainMenuException{
        VisitFriend visitFriend = new VisitFriend(member);
        visitFriend.start();
    }

    public void sendGift() {
        SendGift sendGift = new SendGift(member);
        sendGift.start();

    }
}