package dataManipulation;

import dataAnalysis.Route;

import dataAnalysis.WifiLocation;
import dataHandler.SQLiteDB;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import static org.junit.Assert.*;




public class FindNearbyLocationsTest {

    private Route route;
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
        route = new Route(268, 40.757666, -73.985878, 3002,
                40.74854862, -73.98808416, "blah st", "foo st",
                "00:00:41", "01", "01", "2016");

        String home = System.getProperty("user.home");
        java.nio.file.Path path = java.nio.file.Paths.get(home, "testdatabase.db");
        db = new SQLiteDB(path.toString());
        FindNearbyLocations.init(db);
    }


    @Test
    public void findNearbyWifiTest() throws Exception {
//        wifi = FindNearbyLocations.findNearbyWifi(route);
//        System.out.println(wifi.size());
//        for (int j = 0; j < wifi.size(); j++){
//            System.out.println(wifi.get(j).getSSIF());
//        }
//
//        assertTrue(1 == 1);
    }

}