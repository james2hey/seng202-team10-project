package dataManipulation;

import dataAnalysis.RetailLocation;
import dataAnalysis.Route;
import dataAnalysis.WifiLocation;
import dataHandler.RouteDataHandler;
import dataHandler.SQLiteDB;
import dataHandler.WifiDataHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

//----------------------------------------------------------------
//--------ALL TESTS MUST BE RUN WITH THE TEST DATA FILES----------
//----------------------------------------------------------------


////////////////////////ROUTE FILTERING TESTS\\\\\\\\\\\\\\\\\\\\\

public class DataFilterer_Routes_Wifi_Test {

    private DataFilterer dataFilterer;
    private ArrayList<Route> routes = new ArrayList<>();
    private ArrayList<WifiLocation> wifiLocations = new ArrayList<>();
    private ArrayList<RetailLocation> retailLocations = new ArrayList<>();
    private SQLiteDB db;

    @After
    public void tearDown() throws Exception {
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
    public void filterRoutesTestGenderNotSpecified() throws Exception {
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
    public void filterRoutesTestGenderFemale() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("23130");
        bikeID.add("15427");
        bikeID.add("22319");
        bikeID.add("20759");
        bikeID.add("23013");
        bikeID.add("19675");
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
    public void filterRoutesTestGenderMale() throws Exception {
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
    public void filterRoutesTestGenderOther() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("24256");
        bikeID.add("19651");

        routes = dataFilterer.filterRoutes(3, null, null, null, null,
                null, null);
        int size = routes.size();
        for (int i = 0; i < size; i++){
            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestDate20160110_201601020() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("15747");
        bikeID.add("16278");
        bikeID.add("19240");
        bikeID.add("23114");
        bikeID.add("18503");
        bikeID.add("22270");
        bikeID.add("23160");
        bikeID.add("23095");
        bikeID.add("23453");
        routes = dataFilterer.filterRoutes(-1, "10/01/2016", "20/01/2016", null, null, null, null);
        int size = routes.size();
        for (int i = 0; i < size; i++){
            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestDate20160131_20160131() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("19818");
        bikeID.add("15517");
        bikeID.add("22211");
        routes = dataFilterer.filterRoutes(-1, "30/01/2016", "31/01/2016", null, null, null, null);
        int size = routes.size();
        for (int i = 0; i < size; i++){
            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestDate20160101_20160101() throws Exception {
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
    public void filterRoutesTestTime153610_161739() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("23483");
        bikeID.add("19605");
        bikeID.add("23507");
        bikeID.add("20759");
        routes = dataFilterer.filterRoutes(-1, null, null, "15:36:10", "16:17:39", null, null);
        int size = routes.size();
        for (int i = 0; i < size; i++) {
            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestTime000000_000100() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("22285");
        routes = dataFilterer.filterRoutes(-1, null, null, "00:00:00", "00:01:00", null, null);
        int size = routes.size();
        for (int i = 0; i < size; i++) {
            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestTime230000_245959() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("22211");
        routes = dataFilterer.filterRoutes(-1, null, null, "23:00:00", "24:59:59", null, null);
        int size = routes.size();
        for (int i = 0; i < size; i++) {
            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestTime000000_000000() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("21624");
        routes = dataFilterer.filterRoutes(-1, null, null, "00:00:00", "00:00:00", null, null);
        int size = routes.size();
        for (int i = 0; i < size; i++) {
            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestStartAddress_broad_() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("24202");
        bikeID.add("24042");
        bikeID.add("23409");
        bikeID.add("16278");
        bikeID.add("19240");
        bikeID.add("23114");
        bikeID.add("23160");
        bikeID.add("23095");
        routes = dataFilterer.filterRoutes(-1, null, null, null, null,
                "broad", null);
        int size = routes.size();
        for (int i = 0; i < size; i++){
            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestStartAddress_pershing_square_() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("23130");
        routes = dataFilterer.filterRoutes(-1, null, null, null, null,
                "pershing square", null);
        int size = routes.size();
        for (int i = 0; i < size; i++){
            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestStartAddress_q_() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("23130");
        routes = dataFilterer.filterRoutes(-1, null, null, null, null,
                "q", null);
        int size = routes.size();
        for (int i = 0; i < size; i++){
            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestStartAddress_a_() throws Exception {
        routes = dataFilterer.filterRoutes(-1, null, null, null, null,
                "a", null);
        int size = routes.size();
        assertTrue(46 == size);
    }


    @Test
    public void filterRoutesTestStartAddress_test_() throws Exception {
        routes = dataFilterer.filterRoutes(-1, null, null, null, null,
                "test", null);
        int size = routes.size();
        assertTrue(0 == size);
    }


    @Test
    public void filterRoutesTestEndAddress_broad_() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("18702");
        bikeID.add("23099");
        bikeID.add("19240");
        bikeID.add("21639");
        bikeID.add("21488");
        bikeID.add("23557");
        routes = dataFilterer.filterRoutes(-1, null, null, null, null,
                null, "broad");
        int size = routes.size();
        for (int i = 0; i < size; i++){
            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestEndAddress_pershing_square_() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("24256");
        bikeID.add("23114");
        routes = dataFilterer.filterRoutes(-1, null, null, null, null,
                null, "pershing square");
        int size = routes.size();
        for (int i = 0; i < size; i++){
            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestEndAddress_q_() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("24256");
        bikeID.add("17199");
        bikeID.add("23114");
        routes = dataFilterer.filterRoutes(-1, null, null, null, null,
                null, "q");
        int size = routes.size();
        for (int i = 0; i < size; i++){
            assertTrue(bikeID.get(i).equals(routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestEndAddress_a_() throws Exception {
        routes = dataFilterer.filterRoutes(-1, null, null, null, null,
                null, "a");
        int size = routes.size();
        assertTrue(43 == size);
    }


    @Test
    public void filterRoutesTestEndAddress_test_() throws Exception {
        routes = dataFilterer.filterRoutes(-1, null, null, null, null,
                null, "test");
        int size = routes.size();
        assertTrue(0 == size);
    }




////////////////////////WIFI FILTERING TESTS\\\\\\\\\\\\\\\\\\\\\

    @Test
    public void filterWifiTestName() throws Exception {
        ArrayList<String> wifiID = new ArrayList<>();
        wifiID.add("3");
        wifiID.add("27");
        wifiID.add("49");
        wifiID.add("458");
        wifiID.add("578");
        wifiID.add("805");
        wifiID.add("858");
        wifiID.add("894");
        wifiLocations = dataFilterer.filterWifi("guest", null, null, null);
        for (int i = 0; i < wifiLocations.size(); i++) {
            assertTrue(wifiID.get(i).equals(wifiLocations.get(i).getWifiID()));
        }
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