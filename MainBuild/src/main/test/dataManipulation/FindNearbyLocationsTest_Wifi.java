package dataManipulation;

import dataAnalysis.RetailLocation;
import dataAnalysis.WifiLocation;
import dataHandler.Geocoder;
import dataHandler.RetailerDataHandler;
import dataHandler.SQLiteDB;
import dataHandler.WifiDataHandler;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;




public class FindNearbyLocationsTest_Wifi {

    private ArrayList<WifiLocation> wifi;
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
        wifi = new ArrayList<>();

        String home = System.getProperty("user.home");
        java.nio.file.Path path = java.nio.file.Paths.get(home, "testdatabase.db");
        db = new SQLiteDB(path.toString());

        System.out.println("before");
        WifiDataHandler wdh = new WifiDataHandler(db);
        System.out.println("adter");
        System.out.println(wdh);
        wdh.processCSV(getClass().getClassLoader().getResource("CSV/NYC_Free_Public_WiFi_03292017-test.csv").getFile());

    }


    @Test
    public void findNearbyWifiTest() throws Exception {
        FindNearbyLocations nearbyLocations = new FindNearbyLocations(db);
        wifi = nearbyLocations.findNearbyWifi(40.717828, -74.00096200000002);
        System.out.println(wifi.size());
        for (int j = 0; j < wifi.size(); j++){
            System.out.println(wifi.get(j).getSSID());
        }

        assertTrue(1 == 1);
    }

//    @Test
//    public void findNearbyWifiTest2() throws Exception {
//        FindNearbyLocations nearbyLocations = new FindNearbyLocations(db);
//        wifi = nearbyLocations.findNearbyWifi(route2);
//        System.out.println(wifi.size());
//        for (int j = 0; j < wifi.size(); j++){
//            System.out.println(wifi.get(j).getSSID());
//        }
//        assertTrue(1 == 1);
//    }

//    @Test
//    public void findNearbyWifiToPointTest() throws Exception {
//        FindNearbyLocations nearbyLocations = new FindNearbyLocations(db);
//        wifi = nearbyLocations.findNearbyWifi(retailer);
//        System.out.println(wifi.size());
//        for (int j = 0; j < wifi.size(); j++){
//            System.out.println(wifi.get(j).getSSID());
//        }
//        assertTrue(1 == 1);
//
//    }


    @Test
    public void findNearByRetailerAlongRoute() throws Exception {
        FindNearbyLocations nearbyLocations = new FindNearbyLocations(db);
        retailers = nearbyLocations.findNearbyRetail(40.717828, -74.00096200000002);
        System.out.println(retailers.size());
        for (int j = 0; j < retailers.size(); j++){
            System.out.println(retailers.get(j).getName());
        }
        assertTrue(1 == 1);
    }

}