package main.java.game;

import main.java.dao.*;
import main.java.socialmagnet.*;
import java.util.*;
import java.sql.Timestamp;

public class FarmLand {
    private Member member;
    private HashMap<Integer, ArrayList<Object>> plots;
    private Member currentUser;

    public FarmLand(Member member) {
        PlotDAO plotDAO = new PlotDAO();
        MemberDAO memberDAO = new MemberDAO();
        this.member = memberDAO.getMember(member.getUsername());
        if(plotDAO.getPlots(member.getUsername()).size() == 0){
            plotDAO.populatePlots(member);
        }

        this.plots = plotDAO.getPlots(member.getUsername());

    }

    public FarmLand(Member currentUser ,Member member, boolean accessFriendGame){
        this.currentUser = currentUser;
        PlotDAO plotDAO = new PlotDAO();
        MemberDAO memberDAO = new MemberDAO();
        this.member = memberDAO.getMember(member.getUsername());
        if(plotDAO.getPlots(member.getUsername()).size() == 0){
            plotDAO.populatePlots(member);
        }

        this.plots = plotDAO.getPlots(member.getUsername());
    }

    public void updateMember(Member member){
        this.member = member;
    }

    public void updatePlot(HashMap<Integer, ArrayList<Object>> plots){
        this.plots = plots;
    }

    public void displayFriendGame(){
        MemberDAO memberDAO = new MemberDAO();
        this.member = memberDAO.getMember(member.getUsername());
        String rank = memberDAO.getRank(member);
        System.out.println();
        System.out.println("Name: " + member.getName());
        System.out.println("Title: " + rank);
        System.out.println("Gold: " + member.getGold());
        for (int i = 1; i <= plots.size(); i++) {
            if (plots.get(i).size() == 0) {
                System.out.println(i + ". <empty>");
            } else {
                String crop = (String) plots.get(i).get(0);
                Timestamp time_planted = (Timestamp) plots.get(i).get(1);
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                long diff_in_minutes = (timestamp.getTime() - time_planted.getTime()) / 60000;
                CropDAO cropDAO = new CropDAO();
                Crop crop_details = cropDAO.getCropDetails(crop);
                crop_details.cropGrowth(i, (int) diff_in_minutes);

            }
        }
        System.out.print("[M]ain | City [F]armers | [S]teal >");

    }

    public void startFriendGame() throws MainMenuException{
        Scanner sc = new Scanner(System.in);
        String choice;
        do {
            displayFriendGame();
            choice = sc.nextLine();
            switch (choice) {
                case "M":
                    throw new MainMenuException("Exiting to Main Menu");
                case "F":
                    break;
                case "S":
                    steal();
                    break;
                default:
                    System.out.println("Enter a choice [M]ain, City [F]armers, [S]teal ");
            }
        } while (!choice.equals("F"));
    }

    public void steal(){
        harvestCrop(true);
    }


    public void display() {
        MemberDAO memberDAO = new MemberDAO();
        this.member = memberDAO.getMember(member.getUsername());
        String rank = memberDAO.getRank(member);
        System.out.println("== Social Magnet :: City Farmers :: My Farmland ==");
        System.out.println("Welcome, " + member.getName() + "!");
        System.out.println("Title: " + rank + "\t\t Gold: " + member.getGold() + " gold");
        System.out.println();

        System.out.println("You have " + plots.size() + " plots of land.");
        for (int i = 1; i <= plots.size(); i++) {
            if (plots.get(i).size() == 0) {
                System.out.println(i + ". <empty>");
            } else {
                String crop = (String) plots.get(i).get(0);
                Timestamp time_planted = (Timestamp) plots.get(i).get(1);
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                long diff_in_minutes = (timestamp.getTime() - time_planted.getTime()) / 60000;
                CropDAO cropDAO = new CropDAO();
                Crop crop_details = cropDAO.getCropDetails(crop);
                crop_details.cropGrowth(i, (int) diff_in_minutes);

            }
        }
        System.out.print("[M]ain | City [F]armers | [P]lant | [C]lear | [H]arvest >");

    }

    public void start() throws MainMenuException {
        Scanner sc = new Scanner(System.in);
        String full_choice;
        String choice;
        do {
            System.out.println();
            display();
            full_choice = sc.nextLine();
            choice = full_choice.charAt(0) + "";
            int plotNumber = 0;
            switch (choice) {
                case "M":
                    throw new MainMenuException("Exiting to MainMenu");
                case "F":
                    break;
                case "P":
                    try{
                        plotNumber = Integer.parseInt(full_choice.substring(1, full_choice.length()));
                        if (plots.size() >= plotNumber) {
                            plantCrop(plotNumber);
                            break;
                        } else {
                            System.out.println("Please enter a plot you own!");
                            break;
                        }
                    }
                    catch (NumberFormatException e){
                        System.out.println("Please enter C<plotNumber>!");
                        break;
                    }
                case "C":
                    try{
                        plotNumber = Integer.parseInt(full_choice.substring(1, full_choice.length()));
                        if (plots.size() >= plotNumber) {
                            clearCrop(plotNumber);
                            break;
                        } else {
                            System.out.println("Please enter a plot you own!");
                            break;
                        }
                    }
                    catch (NumberFormatException e){
                        System.out.println("Please enter C<plotNumber>!");
                        break;
                    }
                case "H":
                    harvestCrop(false);
                    break;
                default:
                    System.out.println("Enter a valid choice! {M , F , P , C , H}");
            }
        } while (!choice.equals("F"));
    }

    //plant crops on empty plot
    public void plantCrop(int plotNumber) throws MainMenuException{
        Scanner sc = null;
        while (sc == null) {
            sc = new Scanner(System.in);
            System.out.println();
            System.out.println("Select the crop:");
            try {
                SeedDAO seedDAO = new SeedDAO();
                ArrayList<Seed> allSeeds = seedDAO.getAllSeeds(member.getUsername());
                for (int i = 0; i < allSeeds.size(); i++) {
                    System.out.println(i + 1 + ". " + allSeeds.get(i).getCropName());
                }
                System.out.println("[M]ain | City [F]armers | Select Choice > ");

                String decision = sc.nextLine();
                if(decision.equals("M")){
                    throw new MainMenuException("Exiting to MainMenu");
                }
                else if(decision.equals("F")){
                    break;
                }
                int choice = Integer.parseInt(decision);
                if (choice > allSeeds.size()) {
                    sc = null;
                    System.out.println("Enter a valid choice!");
                    continue;
                }
                else if (plots.get(plotNumber).size() != 0) {
                    System.out.println("There is a crop already planted there. Please clear it first.");
                    sc = null;
                    continue;
                }

                seedDAO.updateSeeds(member.getUsername(), allSeeds.get(choice - 1).getCropName(),allSeeds.get(choice - 1).getQuantity() - 1);
                PlotDAO plotDAO = new PlotDAO();
                plotDAO.plantPlot(member.getUsername(), plotNumber, allSeeds.get(choice - 1).getCropName());
                ArrayList<Object> plotDetails = new ArrayList<>();
                plotDetails.add(allSeeds.get(choice - 1).getCropName());
                plotDetails.add(new Timestamp(System.currentTimeMillis()));
                plots.put(plotNumber, plotDetails);
                System.out.println();
            } catch (InputMismatchException e) {
                System.out.println("Enter a valid choice!");
                sc = null;
            }

        }

    }

    // clear crop according to plotnumber if any crops are wilted
    public void clearCrop(int plotNumber) {
        PlotDAO plotDAO = new PlotDAO();
        boolean clearing_success = plotDAO.clearPlot(member.getUsername(), plotNumber);
        if (clearing_success) {
            plots.put(plotNumber, new ArrayList<Object>());
            MemberDAO memberDAO = new MemberDAO();
            memberDAO.updateGold(member, member.getGold() -5);
        } else {
            System.out.println("That plot does not exist.");
        }

    }

    //harvest any crops that are ready to be harvested
    public void harvestCrop(boolean checkIfFriendWall) {
        //declaring variables to be used
        MemberDAO memberDAO = new MemberDAO();
        Set<Integer> keys = plots.keySet();
        HashMap<String, Integer> harvestCropsAndQuantity = new HashMap<>();
        CropDAO cropDAO = new CropDAO();
        PlotDAO plotDAO = new PlotDAO();

        //Find out which crop is ready to be harvested
        for (Integer k : keys) {
            if(plots.get(k).size() != 0){
                Timestamp timePlanted = (Timestamp) plots.get(k).get(1);
            String cropName = (String) plots.get(k).get(0);
            Crop crop = cropDAO.getCropDetails(cropName);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            long diffInTime = (timestamp.getTime() - timePlanted.getTime());
            if (2 * crop.getRipeTime() >= (double) diffInTime/60000 && (double)diffInTime/60000 >= crop.getRipeTime()) {
                clearCrop(k);
                int randomYield = crop.getYield();
                if(harvestCropsAndQuantity.get(crop.getName()) != null){
                    harvestCropsAndQuantity.put(crop.getName(), randomYield+ harvestCropsAndQuantity.get(crop.getName()));
                }
                else{
                    harvestCropsAndQuantity.put(crop.getName(), randomYield);
                }
            }
            }

        }
        // check if any crops can be harvest and if possible, harvest
        if(harvestCropsAndQuantity.size()!= 0){
            Set<String> cycle = harvestCropsAndQuantity.keySet();

            for (String cropName : cycle){
                System.out.println(harvestCropsAndQuantity.get(cropName));
                Crop crop = cropDAO.getCropDetails(cropName);
                int totalXpGained = crop.getXp() * harvestCropsAndQuantity.get(cropName);
                int totalGoldGained = crop.getSalePrice() * harvestCropsAndQuantity.get(cropName);
                System.out.println(totalXpGained + " " + totalGoldGained);
                System.out.println(member.getGold() + totalGoldGained);

                //update gold and experience level from the harvest


                if(checkIfFriendWall){
                    memberDAO.updateGold(currentUser, currentUser.getGold() + totalGoldGained);
                    memberDAO.updateExperience(currentUser, currentUser.getExperience() + totalXpGained);
                }
                else{
                    memberDAO.updateGold(member, member.getGold() + totalGoldGained);
                    memberDAO.updateExperience(member, member.getExperience() + totalXpGained);
                    updateMember(memberDAO.getMember(member.getUsername()));
                }
                // Updating the member obj
                updateMember(memberDAO.getMember(member.getUsername()));

                //check if the person levelled up and add plots as given
                plotDAO.checkIfCorrectNumberOfPlots(member, plots);

                //update the plots
                updatePlot(plotDAO.getPlots(member.getUsername()));

                //inform user that the harvest has been successful
                if(checkIfFriendWall){
                    System.out.println("You have successfully stolen " + harvestCropsAndQuantity.get(cropName) + " " + cropName + " for " +totalXpGained + " XP and " + totalGoldGained + " gold." );
                }
                else{
                    System.out.println("You have harvested " + harvestCropsAndQuantity.get(cropName) + " " + cropName + " for " +totalXpGained + " XP and " + totalGoldGained + " gold." );
                }
            }

        }
        else{
            //inform user if none of the crops are ready for harvest
            if(checkIfFriendWall){
                System.out.println("None of your friend's crops are ready to be steal.");
            }
            else{
                System.out.println("None of your crops are ready to be harvested.");
            }

        }

    }
}