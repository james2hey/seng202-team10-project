package dataHandler;

import dataAnalysis.WifiLocation;
import org.junit.*;

import java.nio.file.Files;
import java.sql.ResultSet;

import static org.junit.Assert.*;

/**
 * Test for the FavouriteWifiData database table when adding data. Note that error
 * handling for retail shops being added twice is found in the HandleUser getUserWifiFavourites method.
 */
public class FavouriteWifiDataTest {
    private static SQLiteDB db;
    private static FavouriteWifiData favouriteWifiData;

    @AfterClass
    public static void clearDB() throws Exception {
        String home = System.getProperty("user.home");
        java.nio.file.Path path = java.nio.file.Paths.get(home, "testdatabase.db");
        Files.delete(path);
    }

    @BeforeClass
    public static void setUp() throws Exception {
        String home = System.getProperty("user.home");
        java.nio.file.Path path = java.nio.file.Paths.get(home, "testdatabase.db");
        db = new SQLiteDB(path.toString());
    }

    @After
    public void tearDown() throws Exception {
        db.executeUpdateSQL("DROP TABLE favourite_retail");
    }

    @Before
    public void init() throws Exception {
        favouriteWifiData = new FavouriteWifiData(db);
    }
    @Test
    public void addFavouriteWifi() throws Exception {
        favouriteWifiData.addFavouriteWifi("Tester", "1");
        ResultSet rs;
        rs = db.executeQuerySQL("SELECT * FROM favourite_wifi WHERE name = 'Tester' AND WIFI_ID = '1';");
        assertFalse(rs.isClosed());
    }

//    @Test
//    public void deleteFavouriteWifi() throws Exception {
//        favouriteWifiData.addFavouriteWifi("Tester", "1");
//        WifiLocation w = new WifiLocation("1", 0.0, 0.0, "", "", "","",
//                "", "", "", 1);
//        favouriteWifiData.deleteFavouriteWifi(w);
//    }

}