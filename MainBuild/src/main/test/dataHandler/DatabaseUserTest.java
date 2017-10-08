package dataHandler;

import dataAnalysis.Cyclist;
import main.HandleUsers;
import main.Main;
import org.junit.*;

import java.nio.file.Files;
import java.sql.ResultSet;

import static org.junit.Assert.*;

/**
 * Testing class for the DatabaseUser.
 */
public class DatabaseUserTest {
    private static SQLiteDB db;
    private static DatabaseUser databaseUser;
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
        db.executeUpdateSQL("DROP TABLE users;");
        db.executeQuerySQL("DROP TABLE taken_routes;");
        db.executeQuerySQL("DROP TABLE favourite_routes;");
        db.executeQuerySQL("DROP TABLE favourite_retail;");
        db.executeQuerySQL("DROP TABLE favourite_wifi;");
    }

    @Before
    public void init() throws Exception {
        databaseUser = new DatabaseUser(db);
    }

    @Test
    public void addUser() throws Exception {
        databaseUser.addUser("Tester", 1, 1, 2017, 1);
        ResultSet rs = db.executeQuerySQL("SELECT count(*) FROM users WHERE name = 'Tester' AND birth_day = 1 " +
                "AND birth_month = 1 AND birth_year = 2017 AND gender = 1;");
        int result = rs.getInt("count(*)");
        assertEquals(1, result);
    }

    @Test
    public void updateDetailsTakenRoutes() throws Exception {
        TakenRoutes t = new TakenRoutes(db);
        RouteDataHandler r = new RouteDataHandler(db);
        t.addTakenRoute("Tester", "0", "1", "1", "00:00:00", "1", 1, hu);
        databaseUser.addUser("Tester", 1, 1, 2017, 1);
        databaseUser.updateDetails("Different Name", "Tester", 1, 1, 2017, 1);

        ResultSet rs = db.executeQuerySQL("SELECT name FROM taken_routes WHERE name = 'Different Name';");
        String result = rs.getString("name");
        assertEquals("Different Name", result);

        db.executeQuerySQL("DROP TABLE favourite_routes;");
    }

    @Test
    public void updateDetailsFavouriteRoutes() throws Exception {
        FavouriteRouteData f = new FavouriteRouteData(db);
        RouteDataHandler r = new RouteDataHandler(db);
        f.addFavouriteRoute("Tester", "0", "1", "1", "00:00:00", "1", 1, hu);
        databaseUser.addUser("Tester", 1, 1, 2017, 1);
        databaseUser.updateDetails("Different Name", "Tester", 1, 1, 2017, 1);

        ResultSet rs = db.executeQuerySQL("SELECT name FROM favourite_routes WHERE name = 'Different Name';");
        String result = rs.getString("name");
        assertEquals("Different Name", result);

        db.executeQuerySQL("DROP TABLE favourite_routes;");
    }

    @Test
    public void updateDetailsFavouriteRetail() throws Exception {
        FavouriteRetailData f = new FavouriteRetailData(db);
        f.addFavouriteRetail("Tester", "Shop", "Address");
        databaseUser.addUser("Tester", 1, 1, 2017, 1);
        databaseUser.updateDetails("Different Name", "Tester", 1, 1, 2017, 1);

        ResultSet rs = db.executeQuerySQL("SELECT name FROM favourite_retail WHERE name = 'Different Name';");
        String result = rs.getString("name");
        assertEquals("Different Name", result);

        db.executeQuerySQL("DROP TABLE favourite_retail;");
    }

    @Test
    public void updateDetailsFavouriteWifi() throws Exception {
        FavouriteWifiData f = new FavouriteWifiData(db);
        f.addFavouriteWifi("Tester", "Wifi");
        databaseUser.addUser("Tester", 1, 1, 2017, 1);
        databaseUser.updateDetails("Different Name", "Tester", 1, 1, 2017, 1);

        ResultSet rs = db.executeQuerySQL("SELECT name FROM favourite_wifi WHERE name = 'Different Name';");
        String result = rs.getString("name");
        assertEquals("Different Name", result);

        db.executeQuerySQL("DROP TABLE favourite_wifi;");
    }

    @Test
    public void removeUserFromDatabase1() throws Exception {
        // Checking the name is removed from the users table.
        ListDataHandler l = new ListDataHandler(db, hu);
        databaseUser.addUser("Tester", 1, 1, 2017, 1);
        databaseUser.removeUserFromDatabase("Tester", hu);

        ResultSet rs = db.executeQuerySQL("SELECT count(*) FROM users WHERE name = 'Tester';");
        String result = rs.getString("count(*)");
        assertEquals("0", result);
    }

    @Test
    public void removeUserFromDatabase2() throws Exception {
        // Checking that the name is removed from the favourite_wifi table as an example. The same delete clause is made for the
        // for the other types of tables so it is unnecessary to test all of these.
        FavouriteWifiData f = new FavouriteWifiData(db);
        ListDataHandler l = new ListDataHandler(db, hu);
        f.addFavouriteWifi("Tester", "Wifi");
        databaseUser.addUser("Tester", 1, 1, 2017, 1);
        databaseUser.removeUserFromDatabase("Tester", hu);

        ResultSet rs = db.executeQuerySQL("SELECT count(*) FROM favourite_wifi WHERE name = 'Tester';");
        String result = rs.getString("count(*)");
        assertEquals("0", result);
    }

    @Test
    public void removeUserFromDatabase3() throws Exception {
        ListDataHandler l = new ListDataHandler(db, hu);

        databaseUser.addUser("Tester", 1, 1, 2017, 1);
        databaseUser.removeUserFromDatabase("Tester", hu);



        // Insert test stuff here @MATT.




        ResultSet rs = db.executeQuerySQL("SELECT count(*) FROM lists WHERE list_owner = 'Tester';");
        String result = rs.getString("count(*)");
        assertEquals("0", result);
    }


}