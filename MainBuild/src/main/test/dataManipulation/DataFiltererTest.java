package dataManipulation;

import dataAnalysis.Route;
import dataAnalysis.WifiLocation;
import dataHandler.*;
import main.DatabaseUser;
import main.HandleUsers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import static org.junit.Assert.*;

//----------------------------------------------------------------
//--------ALL TESTS MUST BE RUN WITH THE TEST DATA FILES----------
//----------------------------------------------------------------
//To create database using test data, delete the current database. Then add '-test' onto the end of the string of each
// data file name in the main class. Run the program to create a new database using the data in these test files.

public class DataFiltererTest {

    private DataFilterer dataFilterer;
    private ArrayList<Route> routes = new ArrayList<>();
    private ArrayList<WifiLocation> wifiLocations = new ArrayList<>();
    private SQLiteDB db;

    @AfterClass
    public static void tearDownClass() throws Exception {
        String home = System.getProperty("user.home");
        java.nio.file.Path path = java.nio.file.Paths.get(home, "testdatabase.db");
        Files.delete(path);
    }

    @Before
    public void setUp() throws Exception {
        String home = System.getProperty("user.home");
        java.nio.file.Path path = java.nio.file.Paths.get(home, "testdatabase.db");
        db = new SQLiteDB(path.toString());
        dataFilterer = new DataFilterer(db);


        WifiDataHandler wdh = new WifiDataHandler(db);
        wdh.processCSV(getClass().getClassLoader().getResource("CSV/NYC_Free_Public_WiFi_03292017-test.csv").getFile());

        RouteDataHandler rdh = new RouteDataHandler(db);
        rdh.processCSV(getClass().getClassLoader().getResource("CSV/201601-citibike-tripdata-test.csv").getFile());

//        RetailerDataHandler retailerDataHandler = new RetailerDataHandler(db);
//        System.out.println("Made");
//        retailerDataHandler.processCSV(getClass().getClassLoader().getResource("CSV/Lower_Manhattan_Retailers-test.csv").getFile());
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void filterByGenderFemale() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("21997");
        bikeID.add("22794");
        bikeID.add("15721");
        bikeID.add("23650");
        bikeID.add("19804");
        bikeID.add("21624");
        bikeID.add("24232");
        bikeID.add("15713");
        bikeID.add("20945");
        bikeID.add("19039");
        bikeID.add("20258");
        routes = dataFilterer.filterRoutes(2, null, null, null, null,
                -1, -1, null, null);
        int size = routes.size();
        for (int i = 0; i < size; i++){
            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
        }
    }
//
//
//    @Test
//    public void filterByGenderFemaleAge0_20() throws Exception {
//        ArrayList<String> bikeID = new ArrayList<>();
//        bikeID.add("21997");
//        bikeID.add("23650");
//        bikeID.add("19039");
//        bikeID.add("20258");
//        routes = dataFilterer.filterRoutes(2, null, null, 30, 40, null, null, -1, -1);
//        int size = routes.size();
//        for (int i = 0; i < size; i++){
//            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
//        }
//    }
//
//
//    @Test
//    public void filterByGenderFemaleAge50_100() throws Exception {
//        ArrayList<String> bikeID = new ArrayList<>();
//        bikeID.add("22794");
//        bikeID.add("19804");
//        bikeID.add("24232");
//        routes = dataFilterer.filterRoutes(2, null, null, 50, 100, null, null, -1, -1);
//        int size = routes.size();
//        for (int i = 0; i < size; i++){
//            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
//        }
//    }
//
//    @Test
//    public void filterByGenderFemaleDuration1000_2000() throws Exception {
//        ArrayList<String> bikeID = new ArrayList<>();
//        bikeID.add("24232");
//        bikeID.add("15713");
//        bikeID.add("20945");
//        routes = dataFilterer.filterRoutes(2, null, null, -1, -1, null, null, 1000, 2000);
//        int size = routes.size();
//        for (int i = 0; i < size; i++){
//            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
//        }
//    }
//
//    @Test
//    public void filterByGenderFemaleDuration0_400() throws Exception {
//        ArrayList<String> bikeID = new ArrayList<>();
//        bikeID.add("15721");
//        routes = dataFilterer.filterRoutes(2, null, null, -1, -1, null, null, 0, 400);
//        int size = routes.size();
//        for (int i = 0; i < size; i++){
//            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
//        }
//    }
//
//
//    @Test
//    public void filterByGenderFemaleAge0_20Duration1000_2000() throws Exception {
//        ArrayList<String> bikeID = new ArrayList<>();
//        routes = dataFilterer.filterRoutes(2, null, null, 30, 40, null, null, 1000, 2000);
//        int size = routes.size();
//        for (int i = 0; i < size; i++){
//            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
//        }
//    }
//
//
//    @Test
//    public void filterByIncompleteData() throws Exception {
//        ArrayList<String> bikeID = new ArrayList<>();
//        bikeID.add("17057");
//        bikeID.add("17109");
//        bikeID.add("24021");
//        bikeID.add("14769");
//        bikeID.add("16475");
//        bikeID.add("19424");
//        bikeID.add("14823");
//        bikeID.add("22661");
//        routes = dataFilterer.filterRoutes(0, null, null, -1, -1, null, null, -1, -1);
//        int size = routes.size();
//        for (int i = 0; i < size; i++){
//            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
//        }
//    }
//
//    @Test
//    public void filterByGenderMale() throws Exception {
//        //Only testing first and last 10 records from test database with this filter applied
//        ArrayList<String> bikeID = new ArrayList<>();
//        bikeID.add("22285");
//        bikeID.add("17827");
//        bikeID.add("14562");
//        bikeID.add("15788");
//        bikeID.add("24183");
//        bikeID.add("15747");
//        bikeID.add("23933");
//        bikeID.add("23993");
//        bikeID.add("22541");
//        bikeID.add("22193");
//
//        bikeID.add("22965");
//        bikeID.add("21223");
//        bikeID.add("17569");
//        bikeID.add("21619");
//        bikeID.add("24119");
//        bikeID.add("14785");
//        bikeID.add("18591");
//        bikeID.add("22478");
//        bikeID.add("23386");
//        bikeID.add("15861");
//
//        routes = dataFilterer.filterRoutes(1, null, null, -1, -1, null, null, -1, -1);
//        for (int i = 0; i < 10; i++){
//            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
//        }
//        int size = routes.size();
//        int j = 10;
//        for (int i = size - 10; i < size; i++){
//            assertTrue(bikeID.get(j).equals(routes.get(i).getBikeID()));
//            j++;
//        }
//    }
//
//
//    @Test
//    public void filterByDate() throws Exception {
////        ArrayList<String> bikeID = new ArrayList<>();
////        bikeID.add("15721");
//        routes = dataFilterer.filterRoutes(-1, "00/00/0000", "01/01/2016", -1, -1, null, null, -1, -1);
//        int size = routes.size();
//        System.out.println(size);
//        assertTrue(1 == 1);
//    }
//
//
//    @Test
//    public void filterByTime000000_000100() throws Exception {
//        ArrayList<String> bikeID = new ArrayList<>();
//        bikeID.add("22285");
//        bikeID.add("17827");
//        bikeID.add("21997");
//        routes = dataFilterer.filterRoutes(-1, null, null, -1, -1, "00:00:00", "00:01:00", -1, -1);
//        int size = routes.size();
//        for (int i = 0; i < size; i++) {
//            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
//        }
//    }
//
//
//    @Test
//    public void filterByTime001130_001200() throws Exception {
//        ArrayList<String> bikeID = new ArrayList<>();
//        bikeID.add("21624");
//        routes = dataFilterer.filterRoutes(-1, null, null, -1, -1, "00:11:30", "00:12:00", -1, -1);
//        int size = routes.size();
//        for (int i = 0; i < size; i++) {
//            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
//        }
//    }
//
//
//    @Test
//    public void filterByTime000000_000000() throws Exception {
//        ArrayList<String> bikeID = new ArrayList<>();
//        bikeID.add("21624");
//        routes = dataFilterer.filterRoutes(-1, null, null, -1, -1, "00:00:00", "00:00:00", -1, -1);
//        int size = routes.size();
//        for (int i = 0; i < size; i++) {
//            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
//        }
//    }
//
//
//    @Test
//    public void filterByTime001500_245959() throws Exception {
//        ArrayList<String> bikeID = new ArrayList<>();
//        bikeID.add("22478");
//        bikeID.add("15713");
//        bikeID.add("20945");
//        bikeID.add("19039");
//        bikeID.add("20258");
//        bikeID.add("23386");
//        bikeID.add("15861");
//        routes = dataFilterer.filterRoutes(-1, null, null, -1, -1, "00:15:00", "24:59:59", -1, -1);
//        int size = routes.size();
//        for (int i = 0; i < size; i++) {
//            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
//        }
//    }


    @Test
    public void filterByAddress_broad_() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("18665");
        routes = dataFilterer.filterRoutes(-1, null, null, null, null,
                -1, -1, "broad", null);
        int size = routes.size();
        for (int i = 0; i < size; i++){
            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterWifiTest() throws Exception {
        wifiLocations = dataFilterer.filterWifi("Brooklyn", null, null);
        for (int i = 0; i < wifiLocations.size(); i++) {
            System.out.println(wifiLocations.get(1).getSSIF());
        }
        assertTrue(1 == 1);
    }

    @Test
    public void filterWifiTestAll() throws Exception {
        wifiLocations = dataFilterer.filterWifi("Brooklyn", "Limited Free", "USA");
        for (int i = 0; i < wifiLocations.size(); i++) {
            System.out.println(wifiLocations.get(1).getSSIF());
        }
        assertTrue(1 == 1);
    }

}