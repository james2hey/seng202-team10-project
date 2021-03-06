package dataManipulation;

import dataObjects.Cyclist;
import dataObjects.Route;
import dataObjects.WifiLocation;
import dataHandler.*;
import javafx.concurrent.Task;
import main.HandleUsers;
import main.Main;
import org.junit.*;
import org.testfx.framework.junit.ApplicationTest;

import java.nio.file.Files;
import java.util.ArrayList;

import static org.junit.Assert.*;


////////////////////////ROUTE FILTERING TESTS\\\\\\\\\\\\\\\\\\\\\
public class DataFilterer_Routes_Wifi_Test implements AddRouteCallback {

    private static DataFilterer dataFilterer;
    private static ListDataHandler listDataHandler;
    private static HandleUsers hu;
    private static DatabaseUser databaseUser;
    private ArrayList<Route> routes = new ArrayList<>();
    private ArrayList<WifiLocation> wifiLocations = new ArrayList<>();
    private static SQLiteDB db;


    @AfterClass
    public static void tearDown() throws Exception {
        String home = System.getProperty("user.home");
        java.nio.file.Path path = java.nio.file.Paths.get(home, "testdatabase.db");
        Files.delete(path);
    }


    @BeforeClass
    public static void setUp() throws Exception {
        ApplicationTest.launch(Main.class);

        String home = System.getProperty("user.home");
        java.nio.file.Path path = java.nio.file.Paths.get(home, "testdatabase.db");
        db = new SQLiteDB(path.toString());

        hu = new HandleUsers();
        hu.init(db);
        hu.currentCyclist = new Cyclist("Tester");
        listDataHandler = new ListDataHandler(db, "test name");
        ListDataHandler.setListName("test list");
        dataFilterer = new DataFilterer(db);
        databaseUser = new DatabaseUser(db);
        databaseUser.addUser("Tester", 1, 1, 2017, 1);


        WifiDataHandler wifiDataHandler = new WifiDataHandler(db);
        RouteDataHandler routeDataHandler = new RouteDataHandler(db);

        ClassLoader loader = DataFilterer_Routes_Wifi_Test.class.getClassLoader();
        CSVImporter task;

        task = new CSVImporter(db, loader.getResource("CSV/NYC_Free_Public_WiFi_03292017-test.csv").getFile(), wifiDataHandler);
        task.run();

        task = new CSVImporter(db, loader.getResource("CSV/201601-citibike-tripdata-test.csv").getFile(), routeDataHandler);
        task.run();

        System.out.println(db.executeQuerySQL("select count(*) from route_information").getInt(1));

    }

    @After
    public void clean() {
        routes.clear();
        wifiLocations.clear();
    }

    @Test
    public void filterRoutesTestGenderNotSpecified() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("16498");
        bikeID.add("18702");
        bikeID.add("17199");
        bikeID.add("19256");
        Task task = new RouteFiltererTask(db, 0, null, null, null, null,
                null, null, null, null, this);
        task.run();
        int size = bikeID.size();
        for (int i = 0; i < size; i++){
            assertEquals(bikeID.get(i), routes.get(i).getBikeID());
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
        Task task = new RouteFiltererTask(db,2, null, null, null, null,
                null, null, null, null, this);
        task.run();
        int size = bikeID.size();
        for (int i = 0; i < size; i++){
            assertEquals(bikeID.get(i), routes.get(i).getBikeID());
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

        Task task = new RouteFiltererTask(db,1, null, null, null, null,
                null, null, null, null, this);
        task.run();
        for (int i = 0; i < 10; i++){
            assertEquals(bikeID.get(i), (routes.get(i).getBikeID()));
        }
        int size = routes.size();
        int j = 10;
        for (int i = size - 10; i < size; i++){
            assertEquals(bikeID.get(j), (routes.get(i).getBikeID()));
            j++;
        }
    }


    @Test
    public void filterRoutesTestGenderOther() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("24256");
        bikeID.add("19651");

        Task task = new RouteFiltererTask(db,3, null, null, null, null,
                null, null, null, null, this);
        task.run();
        int size = bikeID.size();
        for (int i = 0; i < size; i++){
            assertEquals(bikeID.get(i), (routes.get(i).getBikeID()));
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
        Task task = new RouteFiltererTask(db,-1, "10/01/2016", "20/01/2016", null,
                null, null, null, null, null, this);
        task.run();
        int size = bikeID.size();
        for (int i = 0; i < size; i++){
            assertEquals(bikeID.get(i), (routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestDate20160131_20160131() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("19818");
        bikeID.add("15517");
        bikeID.add("22211");
        Task task = new RouteFiltererTask(db,-1, "30/01/2016", "31/01/2016", null,
                null, null, null, null, null, this);
        task.run();
        int size = bikeID.size();
        for (int i = 0; i < size; i++){
            assertEquals(bikeID.get(i), (routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestDate20160101_20160101() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("22285");
        bikeID.add("16498");
        Task task = new RouteFiltererTask(db,-1, "01/01/2016", "01/01/2016", null,
                null, null, null, null, null, this);
        task.run();
        int size = bikeID.size();
        for (int i = 0; i < size; i++){
            assertEquals(bikeID.get(i), (routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestTime153610_161739() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("23483");
        bikeID.add("19605");
        bikeID.add("23507");
        bikeID.add("20759");
        Task task = new RouteFiltererTask(db,-1, null, null, "15:36:10",
                "16:17:39", null, null, null, null, this);
        task.run();
        int size = bikeID.size();
        for (int i = 0; i < size; i++) {
            assertEquals(bikeID.get(i), (routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestTime000000_000100() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("22285");
        Task task = new RouteFiltererTask(db,-1, null, null, "00:00:00",
                "00:01:00", null, null, null, null, this);
        task.run();
        int size = bikeID.size();
        for (int i = 0; i < size; i++) {
            assertEquals(bikeID.get(i), (routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestTime230000_245959() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("22211");
        Task task = new RouteFiltererTask(db,-1, null, null, "23:00:00",
                "24:59:59", null, null, null, null, this);
        task.run();
        int size = bikeID.size();
        for (int i = 0; i < size; i++) {
            assertEquals(bikeID.get(i), (routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestTime000000_000000() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        Task task = new RouteFiltererTask(db,-1, null, null, "00:00:00",
                "00:00:00", null, null, null, null, this);
        task.run();
        int size = bikeID.size();
        for (int i = 0; i < size; i++) {
            assertEquals(bikeID.get(i), (routes.get(i).getBikeID()));
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
        Task task = new RouteFiltererTask(db,-1, null, null, null, null,
                "broad", null, null, null, this);
        task.run();
        int size = bikeID.size();
        for (int i = 0; i < size; i++){
            assertEquals(bikeID.get(i), (routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestStartAddress_pershing_square_() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("23130");
        Task task = new RouteFiltererTask(db,-1, null, null, null, null,
                "pershing square", null, null, null, this);
        task.run();
        int size = bikeID.size();
        for (int i = 0; i < size; i++){
            assertEquals(bikeID.get(i), (routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestStartAddress_q_() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("23130");
        Task task = new RouteFiltererTask(db,-1, null, null, null, null,
                "q", null, null, null, this);
        task.run();
        int size = bikeID.size();
        for (int i = 0; i < size; i++){
            assertEquals(bikeID.get(i), (routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestStartAddress_a_() throws Exception {
        Task task = new RouteFiltererTask(db,-1, null, null, null, null,
                "a", null, null, null, this);
        task.run();
        int size = routes.size();
        assertEquals(46 ,  size);
    }


    @Test
    public void filterRoutesTestStartAddress__() throws Exception {
        Task task = new RouteFiltererTask(db,-1, null, null, null, null,
                "", null, null, null, this);
        task.run();
        int size = routes.size();
        assertEquals(50 ,  size);
    }


    @Test
    public void filterRoutesTestStartAddress_test_() throws Exception {
        Task task = new RouteFiltererTask(db,-1, null, null, null, null,
                "test", null, null, null, this);
        task.run();
        int size = routes.size();
        assertEquals(0 ,  size);
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
        Task task = new RouteFiltererTask(db,-1, null, null, null, null,
                null, "broad", null, null, this);
        task.run();
        int size = bikeID.size();
        for (int i = 0; i < size; i++){
            assertEquals(bikeID.get(i), (routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestEndAddress_pershing_square_() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("24256");
        bikeID.add("23114");
        Task task = new RouteFiltererTask(db,-1, null, null, null, null,
                null, "pershing square", null, null, this);
        task.run();
        int size = bikeID.size();
        for (int i = 0; i < size; i++){
            assertEquals(bikeID.get(i), (routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestEndAddress_q_() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("24256");
        bikeID.add("17199");
        bikeID.add("23114");
        Task task = new RouteFiltererTask(db,-1, null, null, null, null,
                null, "q", null, null, this);
        task.run();
        int size = bikeID.size();
        for (int i = 0; i < size; i++){
            assertEquals(bikeID.get(i), (routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestEndAddress_a_() throws Exception {
        Task task = new RouteFiltererTask(db,-1, null, null, null, null,
                null, "a", null, null, this);
        task.run();
        int size = routes.size();
        assertEquals(43 ,  size);
    }


    @Test
    public void filterRoutesTestEndAddress_test_() throws Exception {
        Task task = new RouteFiltererTask(db,-1, null, null, null, null,
                null, "test", null, null, this);
        task.run();
        int size = routes.size();
        assertEquals(0 ,  size);
    }


    @Test
    public void filterRoutesTestEndAddress__() throws Exception {
        Task task = new RouteFiltererTask(db,-1, null, null, null, null,
                null, "", null, null, this);
        task.run();
        int size = routes.size();
        assertEquals(50 ,  size);
    }


    @Test
    public void filterRoutesTestBikeID_24042_() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("24042");
        Task task = new RouteFiltererTask(db,-1, null, null, null, null,
                null, null, "24042", null, this);
        task.run();
        int size = bikeID.size();
        for (int i = 0; i < size; i++){
            assertEquals(bikeID.get(i), (routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestBikeID_0_() throws Exception {
        Task task = new RouteFiltererTask(db,-1, null, null, null, null,
                null, null, "", null, this);
        task.run();
        int size = routes.size();
        assertEquals(size ,  0);

    }


    @Test
    public void filterRoutesTestList_foo() throws Exception {
        Task task = new RouteFiltererTask(db,-1, null, null, null, null,
                null, null, null, "foo", this);
        task.run();
        int size = routes.size();
        assertEquals(size ,  0);

    }


    @Test
    public void filterRoutesTestList_test_list() throws Exception {
        Task task = new RouteFiltererTask(db,-1, null, null, null, null,
                null, null, null, "test list", this);
        task.run();
        int size = routes.size();
        assertEquals(size ,  50);

    }


    @Test
    public void filterRoutesTestList__() throws Exception {
        Task task = new RouteFiltererTask(db,-1, null, null, null, null,
                null, null, null, "", this);
        task.run();
        int size = routes.size();
        assertEquals(size ,  0);

    }


    @Test
    public void filterRoutesTestGender_1_Date_20160101_20160120_() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("23114");
        bikeID.add("22270");
        bikeID.add("23160");
        bikeID.add("23095");
        bikeID.add("23453");
        Task task = new RouteFiltererTask(db,1, "10/01/2016", "20/01/2016", null,
                null, null, null, null, null, this);
        task.run();
        int size = bikeID.size();
        for (int i = 0; i < size; i++){
            assertEquals(bikeID.get(i), (routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestGender_2_Time_100000_150000_() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("21585");
        Task task = new RouteFiltererTask(db,2, null, null, "10:00:00",
                "15:00:00", null, null, null, null, this);
        task.run();
        int size = bikeID.size();
        for (int i = 0; i < size; i++){
            assertEquals(bikeID.get(i), (routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestGender_2_StartName_broad_() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("16278");
        bikeID.add("19240");
        Task task = new RouteFiltererTask(db,2, null, null, null,
                null, "broad", null, null, null, this);
        task.run();
        int size = bikeID.size();
        for (int i = 0; i < size; i++){
            assertEquals(bikeID.get(i), (routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestGender_3_EndName_pershing_square_south_() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("24256");
        Task task = new RouteFiltererTask(db,3, null, null, null,
                null, null, "Pershing Square South", null, null, this);
        task.run();
        int size = bikeID.size();
        for (int i = 0; i < size; i++){
            assertEquals(bikeID.get(i), (routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestDate_20160101_20160120_Time_100000_150000_() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("23453");
        Task task = new RouteFiltererTask(db,-1, "10/01/2016", "20/01/2016",
                "10:00:00", "15:00:00", null, null, null, null, this);
        task.run();
        int size = bikeID.size();
        for (int i = 0; i < size; i++){
            assertEquals(bikeID.get(i), (routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestDate_20160101_20160120_StartAddress_broad_() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("16278");
        bikeID.add("19240");
        bikeID.add("23114");
        bikeID.add("23160");
        bikeID.add("23095");
        Task task = new RouteFiltererTask(db,-1, "10/01/2016", "20/01/2016",
                null, null, "broad", null, null, null, this);
        task.run();
        int size = bikeID.size();
        for (int i = 0; i < size; i++){
            assertEquals(bikeID.get(i), (routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestDate_20160101_20160120_EndAddress_broad_() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("19240");
        Task task = new RouteFiltererTask(db,-1, "10/01/2016", "20/01/2016",
                null, null, null, "broad", null, null, this);
        task.run();
        int size = bikeID.size();
        for (int i = 0; i < size; i++){
            assertEquals(bikeID.get(i), (routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestDate_20160101_201601200_BikeID_18503_() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("18503");
        Task task = new RouteFiltererTask(db,-1, "10/01/2016", "20/01/2016",
                null, null, null, null, "18503", null, this);
        task.run();
        int size = bikeID.size();
        for (int i = 0; i < size; i++){
            assertEquals(bikeID.get(i), (routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestTime_100000_150000_StartAddress_st_() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("15971");
        bikeID.add("23453");
        bikeID.add("21410");
        Task task = new RouteFiltererTask(db,-1, null, null,
                "10:00:00", "15:00:00", "st", null, null, null, this);
        task.run();
        int size = bikeID.size();
        for (int i = 0; i < size; i++){
            assertEquals(bikeID.get(i), (routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestTime_100000_150000_EndAddress_ave_() throws Exception {
        ArrayList<String> bikeID = new ArrayList<>();
        bikeID.add("15971");
        bikeID.add("23453");
        bikeID.add("21585");
        Task task = new RouteFiltererTask(db,-1, null, null,
                "10:00:00", "15:00:00", null, "ave", null, null, this);
        task.run();
        int size = bikeID.size();
        for (int i = 0; i < size; i++){
            assertEquals(bikeID.get(i), (routes.get(i).getBikeID()));
        }
    }


    @Test
    public void filterRoutesTestStartAddress_st_EndAddress_ave_() throws Exception {
        Task task = new RouteFiltererTask(db,-1, null, null,
                null, null, "st", "ave", null, null, this);
        task.run();
        int size = routes.size();
        assertEquals(size ,  27);
    }



////////////////////////WIFI FILTERING TESTS\\\\\\\\\\\\\\\\\\\\\

    @Test
    public void filterWifiTestName_guest_() throws Exception {
        ArrayList<String> wifiID = new ArrayList<>();
        wifiID.add("3");
        wifiID.add("27");
        wifiID.add("49");
        wifiID.add("458");
        wifiID.add("578");
        wifiID.add("805");
        wifiID.add("858");
        wifiID.add("894");
        wifiLocations = dataFilterer.filterWifi("guest", null, null, null, null);
        for (int i = 0; i < wifiID.size(); i++) {
            assertEquals(wifiID.get(i), (wifiLocations.get(i).getWifiID()));
        }
    }


    @Test
    public void filterWifiTestName_foo_() throws Exception {
        ArrayList<String> wifiID = new ArrayList<>();
        wifiLocations = dataFilterer.filterWifi("foo", null, null, null, null);
        for (int i = 0; i < wifiID.size(); i++) {
            assertEquals(wifiID.get(i), (wifiLocations.get(i).getWifiID()));
        }
    }


    @Test
    public void filterWifiTestName_l_() throws Exception {
        wifiLocations = dataFilterer.filterWifi("l", null, null, null, null);
        int size = wifiLocations.size();
        assertEquals(size ,  42);
    }


    @Test
    public void filterWifiTestName__() throws Exception {
        wifiLocations = dataFilterer.filterWifi("", null, null, null, null);
        int size = wifiLocations.size();
        assertEquals(size ,  50);
    }


    @Test
    public void filterWifiTestSuburb_Staten_Island_() throws Exception {
        ArrayList<String> wifiID = new ArrayList<>();
        wifiID.add("150");
        wifiID.add("172");
        wifiID.add("243");
        wifiID.add("458");
        wifiLocations = dataFilterer.filterWifi(null, "Staten Island", null, null, null);
        for (int i = 0; i < wifiID.size(); i++) {
            assertEquals(wifiID.get(i), (wifiLocations.get(i).getWifiID()));
        }
    }


    @Test
    public void filterWifiTestSuburb__() throws Exception {
        ArrayList<String> wifiID = new ArrayList<>();
        wifiLocations = dataFilterer.filterWifi(null, "", null, null, null);
        for (int i = 0; i < wifiID.size(); i++) {
            assertEquals(wifiID.get(i), (wifiLocations.get(i).getWifiID()));
        }
    }


    @Test
    public void filterWifiTestType_Free_() throws Exception {
        wifiLocations = dataFilterer.filterWifi(null, null, "free", null, null);
        int size = wifiLocations.size();
        assertEquals(size, 42);
    }


    @Test
    public void filterWifiTestType__() throws Exception {
        ArrayList<String> wifiID = new ArrayList<>();
        wifiLocations = dataFilterer.filterWifi(null, null, "", null, null);
        for (int i = 0; i < wifiID.size(); i++) {
            assertEquals(wifiID.get(i), (wifiLocations.get(i).getWifiID()));
        }
    }


    @Test
    public void filterWifiTestProvider_nycha_() throws Exception {
        ArrayList<String> wifiID = new ArrayList<>();
        wifiID.add("75");
        wifiID.add("551");
        wifiLocations = dataFilterer.filterWifi(null, null, null, "nycha", null);
        for (int i = 0; i < wifiID.size(); i++) {
            assertEquals(wifiID.get(i), (wifiLocations.get(i).getWifiID()));
        }
    }


    @Test
    public void filterWifiTestProvider_foo_() throws Exception {
        ArrayList<String> wifiID = new ArrayList<>();
        wifiLocations = dataFilterer.filterWifi(null, null, null, "foo", null);
        for (int i = 0; i < wifiID.size(); i++) {
            assertEquals(wifiID.get(i), (wifiLocations.get(i).getWifiID()));
        }
    }


    @Test
    public void filterWifiTestProvider_l_() throws Exception {
        wifiLocations = dataFilterer.filterWifi(null, null, null, "l", null);
        int size = wifiLocations.size();
        assertEquals(size, 44);
    }


    @Test
    public void filterWifiTestList_foo() throws Exception {
        wifiLocations = dataFilterer.filterWifi(null, null, null, null, "foo");
        int size = wifiLocations.size();
        assertEquals(size, 0);
    }


    @Test
    public void filterWifiTestList_test_list() throws Exception {
        wifiLocations = dataFilterer.filterWifi(null, null, null, null, "test list");
        int size = wifiLocations.size();
        assertEquals(size, 50);
    }


    @Test
    public void filterWifiTestList__() throws Exception {
        wifiLocations = dataFilterer.filterWifi(null, null, null, null, "");
        int size = wifiLocations.size();
        assertEquals(size, 0);
    }


    @Test
    public void filterWifiTestName_nypl_Suburb_manhattan() throws Exception {
        ArrayList<String> wifiID = new ArrayList<>();
        wifiID.add("247");
        wifiID.add("331");
        wifiID.add("359");
        wifiLocations = dataFilterer.filterWifi("nypl", "Manhattan", null, null, null);
        for (int i = 0; i < wifiID.size(); i++) {
            assertEquals(wifiID.get(i), (wifiLocations.get(i).getWifiID()));
        }
    }


    @Test
    public void filterWifiTestName_nypl_Type_free_() throws Exception {
        ArrayList<String> wifiID = new ArrayList<>();
        wifiID.add("247");
        wifiID.add("295");
        wifiID.add("331");
        wifiID.add("359");
        wifiLocations = dataFilterer.filterWifi("nypl", null, "Free", null, null);
        for (int i = 0; i < wifiID.size(); i++) {
            assertEquals(wifiID.get(i), (wifiLocations.get(i).getWifiID()));
        }
    }


    @Test
    public void filterWifiTestName_guest_Provider_spec_() throws Exception {
        ArrayList<String> wifiID = new ArrayList<>();
        wifiID.add("458");
        wifiID.add("578");
        wifiID.add("805");
        wifiID.add("894");
        wifiLocations = dataFilterer.filterWifi("guest", null, null, "spec", null);
        for (int i = 0; i < wifiID.size(); i++) {
            assertEquals(wifiID.get(i), (wifiLocations.get(i).getWifiID()));
        }
    }



    @Test
    public void filterWifiTestSuburb_brooklyn_Type_limited_free_() throws Exception {
        ArrayList<String> wifiID = new ArrayList<>();
        wifiID.add("3");
        wifiID.add("27");
        wifiID.add("49");
        wifiID.add("805");
        wifiID.add("894");
        wifiLocations = dataFilterer.filterWifi(null, "Brooklyn", "Limited Free", null, null);
        for (int i = 0; i < wifiID.size(); i++) {
            assertEquals(wifiID.get(i), (wifiLocations.get(i).getWifiID()));
        }
    }


    @Test
    public void filterWifiTestSuburb_brooklyn_Provider_alticeusa_() throws Exception {
        ArrayList<String> wifiID = new ArrayList<>();
        ArrayList<String> wifiIDff = new ArrayList<>();
        wifiID.add("3");
        wifiID.add("27");
        wifiID.add("49");
        wifiLocations = dataFilterer.filterWifi(null, "Brooklyn", null, "alticeusa", null);
        for (WifiLocation w : wifiLocations) {
            wifiIDff.add(w.getWifiID());
        }
        assertArrayEquals(wifiID.toArray(), wifiIDff.toArray());
    }

    @Test
    public void filterWifiTestType_limited_free_provider_alticeusa() throws Exception {
        ArrayList<String> wifiID = new ArrayList<>();
        ArrayList<String> wifiIDff = new ArrayList<>();
        wifiID.add("3");
        wifiID.add("27");
        wifiID.add("49");
        wifiID.add("858");
        wifiLocations = dataFilterer.filterWifi(null, null, "Limited Free", "alticeusa", null);
        for (WifiLocation w : wifiLocations) {
            wifiIDff.add(w.getWifiID());
        }
        assertArrayEquals(wifiID.toArray(), wifiIDff.toArray());
    }


    @Override
    public void addRoute(Route route) {
        this.routes.add(route);
    }

    @Override
    public void addRoutes(ArrayList<Route> routes) {
        this.routes.addAll(routes);
    }
}