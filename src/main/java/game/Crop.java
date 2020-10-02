package main.java.game;


public class Crop{
    private String name;
    private int cost;
    private int ripeTime;
    private int xp;
    private int minYield;
    private int maxYield;
    private int salePrice;

    public Crop(String name, int cost, int ripeTime, int xp, int minYield, int maxYield, int salePrice){
        this.name = name;
        this.cost = cost;
        this.ripeTime = ripeTime;
        this.xp = xp;
        this.minYield = minYield;
        this.maxYield = maxYield;
        this.salePrice= salePrice;
    }

    public String getName(){
        return name;
    }

    public int getCost(){
        return cost;
    }

    public int getRipeTime(){
        return ripeTime;
    }

    public int getXp(){
        return xp;
    }
    // get yield according to crop randomly in range
    public int getYield(){
        return (int)(Math.random() * ((maxYield - minYield) + 1)) + minYield;
    }

    public int getSalePrice(){
        return salePrice;
    }

    public String getSpace(){
        String space = "";
        for(int i= 0; i<11-name.length() ; i++){
            space += " ";
        }
        return space;
    }
    //shows the growth of the crop visually
    public String cropGrowth(int plot_number, int amountOfTimePassed){
        String space = getSpace();
        String growth = "";
        if((double)amountOfTimePassed/ripeTime <=1){
            int progress_in_percentage = (int)Math.floor(((double)amountOfTimePassed/ripeTime) * 100);
            int progress = (int)Math.floor(((double)amountOfTimePassed/ripeTime) * 10);
            String visualProgress = "";
            for(int i = 0; i<progress ; i++){
                visualProgress += "#";
            }
            for(int i = 0; i<10-progress ; i++){
                visualProgress += "-";
            }

            System.out.println(plot_number +". "+ name + space + "[" + visualProgress + "] " + progress_in_percentage+ "%");
            growth = plot_number +". "+ name + space + "[" + visualProgress + "] " + progress_in_percentage+ "%";
        }
        else{
            if(amountOfTimePassed - ripeTime> ripeTime){
                System.out.println(plot_number + ". " + name + space + "[  wilted  ]");
                growth = plot_number + ". " + name + space + "[  wilted  ]";
            }
            else{
                System.out.println(plot_number +". "+ name + space + "[##########] 100%");
                growth = plot_number +". "+ name + space + "[##########] 100%";
            }
        }
        return growth;
    }




}