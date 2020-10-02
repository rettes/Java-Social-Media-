package main.java.socialmagnet;

import main.java.game.*;
import java.util.*;

public class MainMenu{
    private Member member;

    public MainMenu(Member member){
        this.member = member;
    }

    public void display(){
        System.out.println();
        System.out.println("== Social Magnet :: Main Menu ==");
        System.out.println("Welcome, " +member.getName() + "!");
        System.out.println("1. News Feed");
        System.out.println("2. My Wall");
        System.out.println("3. My Friends");
        System.out.println("4. City Farmers");
        System.out.println("5. Logout");
        System.out.print("Enter your choice > ");

    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        String choice;
        do {
            display();
            choice = sc.nextLine();
            switch (choice) {
                case "1":
                    showNewsFeed();
                    break;
                case "2":
                    showWall();
                    break;
                case "3":
                    try {
                        showFriends();
                    } catch (MainMenuException e) {
                        continue;
                    }
                case "4":
                    try{
                        startGame();
                    }
                    catch(MainMenuException e){
                        continue;
                    }
                case "5":
                    break;
                default:
                    System.out.println("Enter a choice between 1 to 5 ");
            }
        } while (!choice.equals("5"));
    }

    public void showNewsFeed(){
        NewsFeed newsFeed = new NewsFeed(member.getUsername());
        newsFeed.start();
    }

    public void showWall(){
        Wall wall = new Wall(member, member.getUsername());
        wall.start();
    }

    public void startGame() throws MainMenuException{
        GameMenu gameMenu = new GameMenu(member);
            gameMenu.start();

    }

    public void showFriends() throws MainMenuException {
        MyFriends mf = new MyFriends(member);
        mf.start();
    }
}
