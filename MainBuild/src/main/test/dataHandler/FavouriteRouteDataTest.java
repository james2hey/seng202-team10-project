package dataHandler;

import main.HandleUsers;
import org.junit.*;

import java.nio.file.Files;
import java.sql.ResultSet;

import static org.junit.Assert.*;

/**
 * Test for the FavouriteRouteData database table when adding data. Note that error
 * handling for retail shops being added twice is found in the HandleUser getUserRouteFavourites method.
 */
public class FavouriteRouteDataTest {
    private static SQLiteDB db;
    private static FavouriteRouteData favouriteRouteData;
    private static HandleUsers hu;

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
        favouriteRouteData = new FavouriteRouteData(db);
    }
    @Test
    public void addFavouriteRoute() throws Exception {
        favouriteRouteData.addFavouriteRoute("Tester", "2016", "01", "01",
                "00:00:00", "10000", 1, hu);
        ResultSet rs;
        rs = db.executeQuerySQL("SELECT * FROM favourite_routes WHERE name = 'Tester' AND start_year = '2016' AND " +
                        "start_month = '01' AND start_day = '01' AND start_time = '00:00:00' AND bike_id = '10000' " +
                        "AND rank = '1';");
        assertFalse(rs.isClosed());
    }
}