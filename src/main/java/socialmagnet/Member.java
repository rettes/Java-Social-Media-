package main.java.socialmagnet;

public class Member {
    private String username;
    private String name;
    private String password;
    private int experience;
    private int gold;

    public Member(String username, String name, String password, int experience, int gold) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.experience = experience;
        this.gold = gold;
    }

    public String getUsername() {
        return this.username;
    }

    public String getName() {
        return this.name;
    }

    public int getExperience() {
        return this.experience;
    }

    public int getGold() {
        return this.gold;
    }

    public boolean authentication(String pass){
        if(!password.equals(pass)){
            return false;
        }

        return true;
    }

}