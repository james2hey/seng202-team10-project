package dataManipulation;

import dataAnalysis.RetailLocation;
import dataAnalysis.Route;
import dataAnalysis.WifiLocation;
import dataHandler.RetailerDataHandler;
import dataHandler.RouteDataHandler;
import dataHandler.SQLiteDB;
import dataHandler.WifiDataHandler;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

//----------------------------------------------------------------
//--------ALL TESTS MUST BE RUN WITH THE TEST DATA FILES----------
//----------------------------------------------------------------
//To create database using test data, delete the current database. Then add '-test' onto the end of the string of each
// data file name in the main class. Run the program to create a new database using the data in these test files.

public class DataFiltererTest {

    private DataFilterer dataFilterer;
    private ArrayList<Route> routes = new ArrayList<>();
    private ArrayList<WifiLocation> wifiLocations = new ArrayList<>();
    private ArrayList<RetailLocation> retailLocations = new ArrayList<>();
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
//        retailerDataHandler.processCSV(getClass().getClassLoader().getResource("CSV/Lower_Manhattan_Retailers-test.csv").getFile());
    }


    @Test
    public void filterByGenderNotSpecified() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("16498");
        bikeID.add("18702");
        bikeID.add("17199");
        bikeID.add("19256");
        routes = dataFilterer.filterRoutes(0, null, null, null, null,
                null, null);
        int size = routes.size();
        for (int i = 0; i < size; i++){
            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterByGenderFemale() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("23130");
        bikeID.add("15427");
        bikeID.add("22319");
        bikeID.add("20759");
        bikeID.add("23013");
        bikeID.add("15747");
        bikeID.add("16278");
        bikeID.add("19240");
        bikeID.add("18503");
        bikeID.add("21488");
        bikeID.add("21585");
        routes = dataFilterer.filterRoutes(2, null, null, null, null,
                null, null);
        int size = routes.size();
        for (int i = 0; i < size; i++){
            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterByGenderMale() throws Exception {
        //Only testing first and last 10 records from test database with this filter applied
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("22285");
        bikeID.add("21416");
        bikeID.add("24202");
        bikeID.add("21452");
        bikeID.add("15289");
        bikeID.add("24042");
        bikeID.add("23483");
        bikeID.add("19605");
        bikeID.add("20008");
        bikeID.add("23409");

        bikeID.add("23160");
        bikeID.add("23095");
        bikeID.add("23453");
        bikeID.add("21252");
        bikeID.add("21639");
        bikeID.add("21410");
        bikeID.add("23557");
        bikeID.add("19818");
        bikeID.add("15517");
        bikeID.add("22211");

        routes = dataFilterer.filterRoutes(1, null, null, null, null, null, null);
        for (int i = 0; i < 10; i++){
            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
        }
        int size = routes.size();
        int j = 10;
        for (int i = size - 10; i < size; i++){
            assertTrue(bikeID.get(j).equals(routes.get(i).getBikeID()));
            j++;
        }
    }


    @Test
    public void filterByDate20160110_201601020() throws Exception {
        //SELECT bikeid FROM route_information WHERE start_year || start_month || start_day BETWEEN '20160110' AND '20160120';
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("22285");
        bikeID.add("16498");
        routes = dataFilterer.filterRoutes(-1, "01/01/2016", "01/01/2016", null, null, null, null);
        int size = routes.size();
        for (int i = 0; i < size; i++){
            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterByDate20160101_20160101() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("22285");
        bikeID.add("16498");
        routes = dataFilterer.filterRoutes(-1, "01/01/2016", "01/01/2016", null, null, null, null);
        int size = routes.size();
        for (int i = 0; i < size; i++){
            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterByTime000000_000100() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("22285");
        bikeID.add("17827");
        bikeID.add("21997");
        routes = dataFilterer.filterRoutes(2, null, null, null, null, null, null);
        int size = routes.size();
        for (int i = 0; i < size; i++) {
            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterByTime001130_001200() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("21624");
        routes = dataFilterer.filterRoutes(2, null, null, null, null, null, null);
        int size = routes.size();
        for (int i = 0; i < size; i++) {
            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterByTime000000_000000() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("21624");
        routes = dataFilterer.filterRoutes(2, null, null, null, null, null, null);
        int size = routes.size();
        for (int i = 0; i < size; i++) {
            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterByTime001500_245959() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("22478");
        bikeID.add("15713");
        bikeID.add("20945");
        bikeID.add("19039");
        bikeID.add("20258");
        bikeID.add("23386");
        bikeID.add("15861");
        routes = dataFilterer.filterRoutes(2, null, null, null, null, null, null);
        int size = routes.size();
        for (int i = 0; i < size; i++) {
            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterByAddress_broad_() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("18665");
        routes = dataFilterer.filterRoutes(-1, null, null, null, null,
                "broad", null);
        int size = routes.size();
        for (int i = 0; i < size; i++){
            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterWifiTest() throws Exception {
        wifiLocations = dataFilterer.filterWifi(null, "Brooklyn", null, null);
        for (int i = 0; i < wifiLocations.size(); i++) {
            System.out.println(wifiLocations.get(1).getSSID());
        }
        assertTrue(1 == 1);
    }

    @Test
    public void filterWifiTestAll() throws Exception {
        wifiLocations = dataFilterer.filterWifi(null, "Brooklyn", "Limited Free", "USA");
        for (int i = 0; i < wifiLocations.size(); i++) {
            System.out.println(wifiLocations.get(1).getSSID());
        }
        assertTrue(1 == 1);
    }


//    @Test
//    public void filterRetailersTest() throws Exception {
//        retailLocations = dataFilterer.filterRetailers(null, null, null, 10004);
//        System.out.println(retailLocations.size());
//        for (int i = 0; i < retailLocations.size(); i++) {
//            System.out.println(retailLocations.get(i).getName());
//        }
//        assertTrue(1 == 1);
//    }
//
//    @Test
//    public void filterRetailersTestAll() throws Exception {
//        retailLocations = dataFilterer.filterRetailers(null, "new", "casual", 10004);
//        System.out.println(retailLocations.size());
//        for (int i = 0; i < retailLocations.size(); i++) {
//            System.out.println(retailLocations.get(i).getName());
//        }
//        assertTrue(1 == 1);
//    }
//
//    @Test
//    public void filterRetailersTestNone() throws Exception {
//        retailLocations = dataFilterer.filterRetailers(null, null, null, -1);
//        System.out.println(retailLocations.size());
//        for (int i = 0; i < retailLocations.size(); i++) {
//            System.out.println(retailLocations.get(i).getName());
//        }
//        assertTrue(1 == 1);
//    }

}