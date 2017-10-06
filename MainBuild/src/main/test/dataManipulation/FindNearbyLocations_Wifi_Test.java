package dataManipulation;

import dataAnalysis.RetailLocation;
import dataAnalysis.WifiLocation;
import dataHandler.*;
import de.saxsys.javafx.test.JfxRunner;
import de.saxsys.javafx.test.TestInJfxThread;
import javafx.concurrent.Task;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.nio.file.Files;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;



@RunWith(JfxRunner.class)
public class FindNearbyLocations_Wifi_Test {

    private static ArrayList<WifiLocation> wifi;
    private static SQLiteDB db;

    @AfterClass
    public static void clearDB() throws Exception {
        String home = System.getProperty("user.home");
        java.nio.file.Path path = java.nio.file.Paths.get(home, "testdatabase.db");
        Files.delete(path);
    }

    @BeforeClass
    @TestInJfxThread
    public static void setUp() throws Exception {
        wifi = new ArrayList<>();

        String home = System.getProperty("user.home");
        java.nio.file.Path path = java.nio.file.Paths.get(home, "testdatabase.db");
        db = new SQLiteDB(path.toString());

        WifiDataHandler handler = new WifiDataHandler(db);

        Task<Void> task = new CSVImporter(db, FindNearbyLocations_Wifi_Test.class.getClassLoader().getResource("CSV/NYC_Free_Public_WiFi_03292017-test.csv").getFile(), handler);
        task.run();
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