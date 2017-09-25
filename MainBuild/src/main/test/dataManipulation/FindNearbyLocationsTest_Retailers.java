package dataManipulation;


import dataAnalysis.RetailLocation;
import dataHandler.Geocoder;
import dataHandler.RetailerDataHandler;
import dataHandler.SQLiteDB;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class FindNearbyLocationsTest_Retailers {

    private ArrayList<RetailLocation> retailers;
    private SQLiteDB db;

    @AfterClass
    public static void clearDB() throws Exception {
        String home = System.getProperty("user.home");
        java.nio.file.Path path = java.nio.file.Paths.get(home, "testdatabase.db");
        Files.delete(path);
    }

    @Before
    public void setUp() throws Exception {
        retailers = new ArrayList<>();

        String home = System.getProperty("user.home");
        java.nio.file.Path path = java.nio.file.Paths.get(home, "testdatabase.db");
        db = new SQLiteDB(path.toString());

        Geocoder.init();
        RetailerDataHandler retailerDataHandler = new RetailerDataHandler(db);
        retailerDataHandler.processCSV(getClass().getClassLoader().getResource("CSV/Lower_Manhattan_Retailers-test4.csv").getFile());
    }


    @Test
    public void FindNearbyRetailTest_40_7230097_minus_74_0078529() throws Exception {
        ArrayList<String> retailName = new ArrayList<>();
        retailName.add("Duane Reade");
        FindNearbyLocations nearbyLocations = new FindNearbyLocations(db);
        retailers = nearbyLocations.findNearbyRetail(40.7230097, -74.0078529);
        for (int i = 0; i < retailers.size(); i++){
            assertTrue(retailName.get(i).equals(retailers.get(i).getName()));
        }
    }


    @Test
    public void FindNearbyRetailTest_40_7190097_minus_74_1378529() throws Exception {
        FindNearbyLocations nearbyLocations = new FindNearbyLocations(db);
        retailers = nearbyLocations.findNearbyRetail(40.7190097, -74.0078529);
        int size = retailers.size();
        System.out.println(size);
        assertTrue(size == 20);
    }


    @Test
    public void FindNearbyRetailTest_0_0_0_0() throws Exception {
        ArrayList<String> retailName = new ArrayList<>();
        FindNearbyLocations nearbyLocations = new FindNearbyLocations(db);
        retailers = nearbyLocations.findNearbyRetail(0.0, 0.0);
        for (int i = 0; i < retailers.size(); i++){
            assertTrue(retailName.get(i).equals(retailers.get(i).getName()));
        }
    }
}
