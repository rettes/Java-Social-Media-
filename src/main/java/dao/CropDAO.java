package main.java.dao;

import main.java.game.*;
import java.util.*;
import java.io.*;

public class CropDAO{

    public Crop getCropDetails(String cropName){
        Crop crop= null;
        Scanner sc = null;
        try{
            sc = new Scanner(new FileInputStream("data/crop.csv"));
            sc.nextLine();
            sc.useDelimiter("\r\n|,");
            while(sc.hasNext()){
                String crop_name = sc.next();
                int cost = sc.nextInt();
                int time_taken_to_be_ripe = Integer.parseInt(sc.next());
                int xp = sc.nextInt();
                int minYield = sc.nextInt();
                int maxYield = sc.nextInt();
                int salePrice = sc.nextInt();
                if(crop_name.equals(cropName)){
                    return new Crop(crop_name, cost, time_taken_to_be_ripe, xp, minYield, maxYield,salePrice);
                }
            }
        }
        catch(FileNotFoundException e){
            System.out.println("Crop File not found.");
        }
        finally{
            sc.close();
        }
        return crop;
    }

    public ArrayList<Crop> getAllCrop(){
        Scanner sc = null;
        ArrayList<Crop> allCrops = new ArrayList<>();
        try{
            sc = new Scanner(new FileInputStream("data/crop.csv"));
            sc.nextLine();
            sc.useDelimiter("\r\n|,");
            while(sc.hasNext()){
                String crop_name = sc.next();
                int cost = sc.nextInt();
                int time_taken_to_be_ripe = Integer.parseInt(sc.next());
                int xp = sc.nextInt();
                int minYield = sc.nextInt();
                int maxYield = sc.nextInt();
                int salePrice = sc.nextInt();
                allCrops.add(new Crop(crop_name, cost, time_taken_to_be_ripe, xp, minYield, maxYield,salePrice));
            }
        }
        catch(FileNotFoundException e){
            System.out.println("Crop File not found.");
        }
        finally{
            sc.close();
        }
        return allCrops;
    }
}