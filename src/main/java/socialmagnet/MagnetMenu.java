package main.java.socialmagnet;

import main.java.dao.*;
import java.util.*;

public class MagnetMenu {

    public void display() {
        System.out.println("== Social Magnet :: Welcome ==");
        System.out.println("Good morning, anonymous!");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Enter your choice:");
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            display();
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    registration();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    System.out.println("Thank you for using Social Magnet!");
                    break;
                // case 4:
                // processListAllRentals();
                // break;
                default:
                    System.out.println("Please enter a choice between 1 & 3!");
            }
        } while (choice != 3);
    }

    public void registration() {
        System.out.println();
        System.out.println("== Social Magnet :: Registration ==");
        Scanner sc = null;

        // credentials will contain [username, fullname, password] in this order
        ArrayList<String> credentials = new ArrayList<>();

        while (sc == null) {
            sc = new Scanner(System.in);
            System.out.print("Enter your username > ");
            String username = sc.nextLine();
            if (!isAlphaNumeric(username)) {
                sc = null;
                System.out.println("Your username should only contain alphanumeric characters.");
            }
            credentials.add(username);
        }
        System.out.print("Enter your Full name > ");
        String fullname = sc.nextLine();
        credentials.add(fullname);
        sc = null;
        while (sc == null) {
            sc = new Scanner(System.in);
            System.out.print("Enter your password > ");
            String password = sc.nextLine();
            System.out.print("Confirm your password > ");
            String confirm_password = sc.nextLine();
            System.out.println();
            if (!password.equals(confirm_password)) {
                sc = null;
                System.out.println("Your password is not the same as your confirmation password.");
            }
            credentials.add(password);
        }
        MemberDAO memberDAO = new MemberDAO();
        boolean check = memberDAO.addMember(credentials.get(0), credentials.get(1), credentials.get(2), 0, 50);
        if (check) {
            System.out.println(credentials.get(0) + ", your account is successfully created!");
        } else {
            System.out.println(credentials.get(0) + ", you already have an account with us!");
        }

    }

    public void login() {
        System.out.println();
        System.out.println("== Social Magnet :: Login ==");
        Scanner sc = null;
        while (sc == null) {
            sc = new Scanner(System.in);
            System.out.print("Enter your username > ");
            String username = sc.nextLine();
            System.out.print("Enter your password > ");
            String password = sc.nextLine();
            MemberDAO memberDAO = new MemberDAO();
            if (memberDAO.getMember(username) == null) {
                System.out.println("Invalid username/password.");
                System.out.println();
                sc = null;
            } else {
                Member member = memberDAO.getMember(username);
                if (!member.authentication(password)) {
                    System.out.println("Invalid password.");
                    System.out.println();
                    sc = null;
                } else {
                    System.out.println();
                    MainMenu mainMenu = new MainMenu(member);
                    mainMenu.start();
                }
            }
        }
    }

    public boolean isAlphaNumeric(String s) {
        return s != null && s.matches("^[a-zA-Z0-9]*$");
    }
}