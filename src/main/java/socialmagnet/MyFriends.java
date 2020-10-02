package main.java.socialmagnet;

import main.java.dao.*;
import java.util.*;

public class MyFriends {
    private Member member;
    private FriendsDAO fDAO;
    private ArrayList<String> friends;
    private ArrayList<String> friendRequests;
    private HashMap<Integer,String> mapFriends;
    private HashMap<Integer,String> mapRequests;


    public MyFriends(Member member) {
        this.member = member;
        mapFriends = new HashMap<Integer,String>();
        mapRequests = new HashMap<Integer,String>();
    }

    public void display() {
        int count = 1;
        System.out.println("== Social Magnet :: My Friends ==");
        System.out.println("Welcome " + member.getName() + "!");

        System.out.println();

        System.out.println("My Friends:");
        fDAO = new FriendsDAO();
        friends = fDAO.getFriends(member.getUsername());

        for (String friend : friends) {
            mapFriends.put(count,friend);
            System.out.println(count + ". " + friend);
            count++;
        }

        System.out.println();

        System.out.println("My Requests:");
        friendRequests = fDAO.getFriendRequests(member.getUsername());

        for (String friendRequest : friendRequests) {
            mapRequests.put(count,friendRequest);
            System.out.println(count + ". " + friendRequest);
            count++;
        }

        System.out.println();

    }

    public void start() throws MainMenuException {
        Scanner sc = new Scanner(System.in);
        String choice;
        String choice_alpha;
        int num;

        do {
            display();
            System.out.print("[M]ain | [U]nfriend | re[Q]uest | [A]ccept | [R]eject | [V]iew > ");
            choice = sc.nextLine();
            choice_alpha = "" + choice.charAt(0);
            switch (choice_alpha) {
                case "M":
                    throw new MainMenuException("Exiting to Main Menu");
                case "U":
                    if (choice.length() > 1) {
                        num = Integer.parseInt(choice.substring(1));
                        unFriend(num);
                    } else {
                        System.out.println("Select in the following format U<ID>");
                        System.out.println();
                    }
                    break;
                case "Q":
                    sendRequest();
                    break;
                case "A":
                    if (choice.length() > 1) {
                        num = Integer.parseInt(choice.substring(1));
                        acceptRequest(num);
                    } else {
                        System.out.println("Select in the following format A<ID>");
                        System.out.println();
                    }
                    break;
                case "R":
                    if (choice.length() > 1) {
                        num = Integer.parseInt(choice.substring(1));
                        rejectRequest(num);
                    } else {
                        System.out.println("Select in the following format R<ID>");
                        System.out.println();
                    }
                    break;
                case "V":
                    if (choice.length() > 1) {
                        num = Integer.parseInt(choice.substring(1));
                        displayFriendAbout(num);
                    } else {
                        System.out.println("Select in the following format V<ID>");
                        System.out.println();
                    }
                    break;
                default:
                    System.out.println("Enter a valid option");
                    System.out.println();

            }
        } while (!choice_alpha.equals("M"));
    }

    public void sendRequest() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the username > ");
        String usernameToAdd = sc.nextLine();
        int result = fDAO.sendRequest(member.getUsername(), usernameToAdd);

        if (result == 0) {
            System.out.println("A friend request from you or " + usernameToAdd + " already exists");
            System.out.println();
        } else if (result == 1) {
            System.out.println("A friend request has been sent to " + usernameToAdd);
            System.out.println();
        } else {
            System.out.println(usernameToAdd + " does not exist");
            System.out.println();
        }
    }

    public void acceptRequest(int num) {
        if (mapRequests.containsKey(num)) {
            String usernameToAccept = mapRequests.get(num);
            fDAO.acceptRequest(member.getUsername(), usernameToAccept);
            System.out.println("You have accepted " + usernameToAccept + "'s friend request");
            System.out.println();
        } else {
            System.out.println("Invalid ID");
            System.out.println();
        }
    }

    public void rejectRequest(int num) {
        if (mapRequests.containsKey(num)) {
            String usernameToReject = mapRequests.get(num);
            fDAO.rejectRequest(member.getUsername(), usernameToReject);
            System.out.println("You have rejected " + usernameToReject + "'s friend request");
            System.out.println();
        } else {
            System.out.println("Invalid ID");
            System.out.println();
        }
    }

    public void unFriend(int num) {
        if (mapFriends.containsKey(num)) {
            String usernameToRemove = mapFriends.get(num);
            fDAO.removeFriend(member.getUsername(), usernameToRemove);
            System.out.println("You have unfriended " + usernameToRemove);
            System.out.println();
        } else {
            System.out.println("Invalid ID");
            System.out.println();
        }
    }

    public void displayFriendAbout(int num) {
        if (mapFriends.containsKey(num)) {
            String usernameToView = mapFriends.get(num);
            Wall wall = new Wall(member, usernameToView);
            wall.start();
        } else {
            System.out.println("Invalid ID");
            System.out.println();
        }
    }
}