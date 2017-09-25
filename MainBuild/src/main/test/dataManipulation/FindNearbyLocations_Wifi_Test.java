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




public class FindNearbyLocations_Wifi_Test {

    private ArrayList<WifiLocation> wifi;
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

        WifiDataHandler wdh = new WifiDataHandler(db);
        wdh.processCSV(getClass().getClassLoader().getResource("CSV/NYC_Free_Public_WiFi_03292017-test.csv").getFile());

    }


    @Test
    public void findNearbyWifiTest_40_71728_minus_74_000962() throws Exception {
        ArrayList<String> wifiID = new ArrayList<>();
        wifiID.add("705");
        wifiID.add("2266");
        FindNearbyLocations nearbyLocations = new FindNearbyLocations(db);
        wifi = nearbyLocations.findNearbyWifi(40.717828, -74.000962);
        for (int i = 0; i < wifi.size(); i++){
            assertTrue(wifiID.get(i).equals(wifi.get(i).getWifiID()));
        }
    }


    @Test
    public void findNearbyWifiTest_40_689319_minus_73_987162() throws Exception {
        ArrayList<String> wifiID = new ArrayList<>();
        wifiID.add("105");
        wifiID.add("192");
        wifiID.add("215");
        wifiID.add("238");
        wifiID.add("551");
        wifiID.add("951");
        wifiID.add("1664");
        wifiID.add("2080");
        FindNearbyLocations nearbyLocations = new FindNearbyLocations(db);
        wifi = nearbyLocations.findNearbyWifi(40.689319, -73.987162);
        for (int i = 0; i < wifi.size(); i++){
            assertTrue(wifiID.get(i).equals(wifi.get(i).getWifiID()));
        }
    }


    @Test
    public void findNearbyWifiTest_0_0_0_0() throws Exception {
        ArrayList<String> wifiID = new ArrayList<>();
        FindNearbyLocations nearbyLocations = new FindNearbyLocations(db);
        wifi = nearbyLocations.findNearbyWifi(0.0, 0.0);
        for (int i = 0; i < wifi.size(); i++){
            assertTrue(wifiID.get(i).equals(wifi.get(i).getWifiID()));
        }
    }
}