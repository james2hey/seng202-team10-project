package main;

import dataAnalysis.RetailLocation;
import dataAnalysis.Route;
import dataAnalysis.WifiLocation;
import dataHandler.*;
import org.junit.*;

import java.nio.file.Files;
import java.sql.ResultSet;

import static org.junit.Assert.*;

/**
 * Tests for the Cyclist class.
 */
public class CyclistTest {
    private static SQLiteDB db;
    private Cyclist testCyclist;
    private HandleUsers hu;

    @Before
    public void setUp() throws Exception {
        testCyclist = new Cyclist("Tester");
        hu = new HandleUsers();
        hu.init(db);
        hu.currentCyclist = testCyclist;
    }

    @BeforeClass
    public static void initDB() {
        String home = System.getProperty("user.home");
        java.nio.file.Path path = java.nio.file.Paths.get(home, "testdatabase.db");
        db = new SQLiteDB(path.toString());
    }

    @After
    public void tearDown() throws Exception {
        db.executeUpdateSQL("DROP TABLE route_information");
        db.executeUpdateSQL("DROP TABLE wifi_location");
        db.executeUpdateSQL("DROP TABLE retailer");
        db.executeUpdateSQL("DROP TABLE favourite_route");
        db.executeUpdateSQL("DROP TABLE favourite_wifi");
        db.executeUpdateSQL("DROP TABLE favourite_retail");
        db.executeUpdateSQL("DROP TABLE users");
    }

    @AfterClass
    public static void clearDB() throws Exception {
        String home = System.getProperty("user.home");
        java.nio.file.Path path = java.nio.file.Paths.get(home, "testdatabase.db");
        Files.delete(path);
    }


    @Test
    public void addRoute() throws Exception {
        RouteDataHandler rdh = new RouteDataHandler(db);
        FavouriteRouteData frd = new FavouriteRouteData(db);
        Route testRoute = new Route(10, "00:00:00", "00:00:00", "01", "01", "2016",
                "01", "01", "2016", 0.0, 0.0,
                0.0, 0.0, 1, 2, "Test Street",
                "Test2 Street", "10000", 1, "Subscriber", 20);
        //hu.currentCyclist = testCyclist;
        testCyclist.addRoute(testRoute, testCyclist.getName(), 1, db, hu);
        ResultSet rs;

        rs = db.executeQuerySQL("SELECT * FROM favourite_routes WHERE name = '" + testCyclist.getName() + "' AND  start_year = '2016'" +
                " AND start_month = '01' AND start_day = '01' AND start_time = '00:00:00' AND bikeid = '10000' AND rank = '1'");
        assertFalse(rs.isClosed());
    }

    @Test
    public void routeAlreadyInListFalse() throws Exception {
        Route testRoute = new Route(10, "00:00:00", "00:00:00", "01", "01", "2016",
                "01", "01", "2016", 0.0, 0.0,
                0.0, 0.0, 1, 2, "Test Street",
                "Test2 Street", "10000", 1, "Subscriber", 20);
        boolean result = testCyclist.routeAlreadyInList(testRoute);
        assertFalse(result);
    }


    @Test
    public void routeAlreadyInListTrue() throws Exception {
        Route testRoute = new Route(10, "00:00:00", "00:00:00", "01", "01", "2016",
                "01", "01", "2016", 0.0, 0.0,
                0.0, 0.0, 1, 2, "Test Street",
                "Test2 Street", "10000", 1, "Subscriber", 20);
        testCyclist.addRouteInstance(testRoute);
        boolean result = testCyclist.routeAlreadyInList(testRoute);
        assertTrue(result);
    }

    @Test
    public void addFavouriteRetail() throws Exception {
        FavouriteRetailData frd = new FavouriteRetailData(db);
        RetailLocation testRetail = new RetailLocation("Test Shop", "1 Test Street", "NY",
                "Casual Eating", "F-Pizza", "NY", 10000, 0.0, 0.0);
        testCyclist.addFavouriteRetail(testRetail, testCyclist.getName(), db);
        ResultSet rs;

        rs = db.executeQuerySQL("SELECT * FROM favourite_retail WHERE name = '" + testCyclist.getName() + "' AND " +
                "RETAILER_NAME = 'Test Shop' AND ADDRESS = '1 Test Street';");
        assertFalse(rs.isClosed());
    }

    @Test
    public void addFavouriteWifi() throws Exception {
        FavouriteWifiData fwd = new FavouriteWifiData(db);
        WifiLocation testWifi = new WifiLocation("1", 0.0, 0.0, "1 Test Street", "Guest",
                "Free", "BPL", "free", "NY", "Manhattan",
                10000);
        testCyclist.addFavouriteWifi(testWifi, testCyclist.getName(), db);
        ResultSet rs;

        rs = db.executeQuerySQL("SELECT * FROM favourite_wifi WHERE name = '" + testCyclist.getName() + "' AND " +
                "WIFI_ID = '1'");
        assertFalse(rs.isClosed());
    }

}