//package dataManipulation;
//
//import dataAnalysis.RetailLocation;
//import dataAnalysis.Route;
//
//import dataAnalysis.WifiLocation;
//import dataHandler.*;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.util.ArrayList;
//
//import static org.junit.Assert.*;
//
//
//
//
//public class FindNearbyLocationsTest {
//
//    private Route route, route2;
//    private RetailLocation retailer;
//    private ArrayList<WifiLocation> wifi;
//    private ArrayList<RetailLocation> retailers;
//    private SQLiteDB db;
//
//    @AfterClass
//    public static void clearDB() throws Exception {
//        String home = System.getProperty("user.home");
//        java.nio.file.Path path = java.nio.file.Paths.get(home, "testdatabase.db");
//        Files.delete(path);
//    }
//
//    @Before
//    public void setUp() throws Exception {
//        wifi = new ArrayList<>();
//        route = new Route(268, 40.757666, -73.985878, 3002,
//                40.74854862, -73.98808416, "blah st", "foo st",
//                "00:00:41", "01", "01", "2016");
//        route2 = new Route(268, 50.757666, -73.985878, 3002,
//                42.6210613, -73.7268233, "blah st", "foo st",
//                "00:00:41", "01", "01", "2016");
//        retailer = new RetailLocation("foods", "food st", "NYC", "Shopping",
//                "p-spa", 10004, 40.757666, -73.985878);
//
//        String home = System.getProperty("user.home");
//        java.nio.file.Path path = java.nio.file.Paths.get(home, "testdatabase.db");
//        db = new SQLiteDB(path.toString());
//
//        System.out.println("before");
//        WifiDataHandler wdh = new WifiDataHandler(db);
//        System.out.println("adter");
//        System.out.println(wdh);
//        wdh.processCSV(getClass().getClassLoader().getResource("CSV/NYC_Free_Public_WiFi_03292017-test.csv").getFile());
//
//        Geocoder.init();
//        RetailerDataHandler retailerDataHandler = new RetailerDataHandler(db);
//        System.out.println("Made");
//        retailerDataHandler.processCSV(getClass().getClassLoader().getResource("CSV/Lower_Manhattan_Retailers-test.csv").getFile());
//    }
//
//
//    @Test
//    public void findNearbyWifiTest() throws Exception {
//        FindNearbyLocations nearbyLocations = new FindNearbyLocations(db);
//        wifi = nearbyLocations.findNearbyWifiAlongRoute(route);
//        System.out.println(wifi.size());
//        for (int j = 0; j < wifi.size(); j++){
//            System.out.println(wifi.get(j).getSSID());
//        }
//
//        assertTrue(1 == 1);
//    }
//
//    @Test
//    public void findNearbyWifiTest2() throws Exception {
//        FindNearbyLocations nearbyLocations = new FindNearbyLocations(db);
//        wifi = nearbyLocations.findNearbyWifiAlongRoute(route2);
//        System.out.println(wifi.size());
//        for (int j = 0; j < wifi.size(); j++){
//            System.out.println(wifi.get(j).getSSID());
//        }
//        assertTrue(1 == 1);
//    }
//
//    @Test
//    public void findNearbyWifiToPointTest() throws Exception {
//        FindNearbyLocations nearbyLocations = new FindNearbyLocations(db);
//        wifi = nearbyLocations.findNearbyWifiToPoint(retailer);
//        System.out.println(wifi.size());
//        for (int j = 0; j < wifi.size(); j++){
//            System.out.println(wifi.get(j).getSSID());
//        }
//        assertTrue(1 == 1);
//
//    }
//
//
//    @Test
//    public void findNearByRetailerAlongRoute() throws Exception {
//        FindNearbyLocations nearbyLocations = new FindNearbyLocations(db);
//        retailers = nearbyLocations.findNearByRetailerAlongRoute(route2);
//        System.out.println(retailers.size());
//        for (int j = 0; j < retailers.size(); j++){
//            System.out.println(retailers.get(j).getName());
//        }
//        assertTrue(1 == 1);
//    }
//
//}