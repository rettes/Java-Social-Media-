package main.java.game;

public class Seed{
    private String username;
    private String cropName;
    private int quantity;

    public Seed(String username, String cropName,int quantity){
        this.username = username;
        this.cropName = cropName;
        this.quantity = quantity;
    }

    public String getCropName(){
        return cropName;
    }

    public int getQuantity(){
        return quantity;
    }
}

