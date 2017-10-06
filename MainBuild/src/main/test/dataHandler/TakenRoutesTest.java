package dataHandler;

import dataAnalysis.Cyclist;
import main.HandleUsers;
import org.junit.*;

import java.nio.file.Files;
import java.sql.ResultSet;

import static org.junit.Assert.*;

/**
 * Created by jto59 on 6/10/17.
 */
public class TakenRoutesTest {
    private static SQLiteDB db;
    private static TakenRoutes takenRoutes;
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
        hu = new HandleUsers();
        hu.init(db);
        hu.currentCyclist = new Cyclist("Tester");
    }

    @After
    public void tearDown() throws Exception {
        db.executeUpdateSQL("DROP TABLE favourite_routes");
    }

    @Before
    public void init() throws Exception {
        takenRoutes = new TakenRoutes(db);
    }

    @Test
    public void addFavouriteRoute() throws Exception {
        RouteDataHandler rdh = new RouteDataHandler(db);
        takenRoutes.addTakenRoute("Tester", "2016", "01", "01",
                "00:00:00", "10000", 1, hu);
        ResultSet rs;
        rs = db.executeQuerySQL("SELECT * FROM taken_routes WHERE name = 'Tester' AND start_year = '2016' AND " +
                "start_month = '01' AND start_day = '01' AND start_time = '00:00:00' AND bikeid = '10000' " +
                "AND distance = '1';");
        assertFalse(rs.isClosed());
    }

    @Test
    public void deleteTakenRoute() throws Exception {

    }

    @Test
    public void findFiveRecentRoutesMoreThanFive() throws Exception {

    }

    @Test
    public void findFiveRecentRoutesLessThanFive() throws Exception {

    }

    @Test
    public void findFiveRecentRoutesEmpty() throws Exception {

    }
}