package test.java;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import main.java.game.*;
import main.java.dao.*;;

public class CropTest {
    @Test
    public void testGetCropName(){
        CropDAO cropDAO = new CropDAO();
        Crop crop = new Crop("Papaya", 20, 30, 8, 75, 100, 15);
        Crop testcrop = cropDAO.getCropDetails("Papaya");

        assertEquals(crop.getName(),testcrop.getName());
    }
    @Test
    public void testGetCropCost(){
        CropDAO cropDAO = new CropDAO();
        Crop crop = new Crop("Papaya", 20, 30, 8, 75, 100, 15);
        Crop testcrop = cropDAO.getCropDetails("Papaya");
        
        assertEquals(crop.getCost(),testcrop.getCost());
    }
    @Test
    public void testGetCropRipeTime(){
        CropDAO cropDAO = new CropDAO();
        Crop crop = new Crop("Papaya", 20, 30, 8, 75, 100, 15);
        Crop testcrop = cropDAO.getCropDetails("Papaya");
        
        assertEquals(crop.getRipeTime(),testcrop.getRipeTime());
    }
    @Test
    public void testGetCropXp(){
        CropDAO cropDAO = new CropDAO();
        Crop crop = new Crop("Papaya", 20, 30, 8, 75, 100, 15);
        Crop testcrop = cropDAO.getCropDetails("Papaya");

        assertEquals(crop.getXp(), testcrop.getXp());
    }
    @Test
    public void testGetCropSalePrice(){
        CropDAO cropDAO = new CropDAO();
        Crop crop = new Crop("Papaya", 20, 30, 8, 75, 100, 15);
        Crop testcrop = cropDAO.getCropDetails("Papaya");

        assertEquals(crop.getSalePrice(), testcrop.getSalePrice());
    }
    @Test
    public void testCropGrowthHalfway(){
        CropDAO cropDAO = new CropDAO();
        Crop crop = new Crop("Papaya", 20, 30, 8, 75, 100, 15);
        String growth = crop.cropGrowth(1, 10);

        assertEquals("1. Papaya     [###-------] 33%", growth);
    }
    @Test
    public void testCropGrowthFully(){
        CropDAO cropDAO = new CropDAO();
        Crop crop = new Crop("Papaya", 20, 30, 8, 75, 100, 15);
        String growth = crop.cropGrowth(1, 30);

        assertEquals("1. Papaya     [##########] 100%", growth);
    }
    @Test
    public void testCropWilted(){
        CropDAO cropDAO = new CropDAO();
        Crop crop = new Crop("Papaya", 20, 30, 8, 75, 100, 15);
        String growth = crop.cropGrowth(1, 61);

        assertEquals("1. Papaya     [  wilted  ]", growth);
    }

    
    

    
    

}