package test.java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import main.java.game.*;
import main.java.dao.*;
import main.java.socialmagnet.*;

public class StoreTest {
    @Test
    public void testIfBuyerReceivedTheSeed(){
        SeedDAO seedDAO = new SeedDAO();
        int intialSeedQuantity = seedDAO.getSeedQuantity("apple", "Papaya");

        MemberDAO memberDAO = new MemberDAO();
        Member member = memberDAO.getMember("apple");
        CropDAO cropDAO = new CropDAO();
        ArrayList<Crop> cropList = cropDAO.getAllCrop();
        Store store = new Store(member, cropList);
        store.buySeeds(cropDAO.getCropDetails("Papaya"), 2); //buys 2 bag of Papaya Seed
        int postSeedQuantity = seedDAO.getSeedQuantity("apple", "Papaya");

        assertEquals(intialSeedQuantity + 2, postSeedQuantity);
    }
    @Test
    public void testIfBuyerPaidForTheSeeds(){
        MemberDAO memberDAO = new MemberDAO();
        Member member = memberDAO.getMember("apple");
        int intialMoney = member.getGold();
        System.out.println(intialMoney);
        CropDAO cropDAO = new CropDAO();
        ArrayList<Crop> cropList = cropDAO.getAllCrop();
        Store store = new Store(member, cropList);
        store.buySeeds(cropDAO.getCropDetails("Papaya"), 2); // buying 2 papaya costs 40 dollars
        Member member2 = memberDAO.getMember("apple");

        int postMoney = member2.getGold();
        System.out.println(postMoney);

        assertEquals(intialMoney-40, postMoney);
    }


}