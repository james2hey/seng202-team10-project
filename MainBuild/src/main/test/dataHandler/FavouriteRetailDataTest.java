package dataHandler;

import org.junit.*;

import java.nio.file.Files;
import java.sql.ResultSet;

import static org.junit.Assert.*;

/**
 * Test for the FavouriteRetailData database table when adding data. Note that error
 * handling for retail shops being added twice is found in the HandleUser getUserRetailFavourites method.
 */
public class FavouriteRetailDataTest {
    private static SQLiteDB db;
    private static FavouriteRetailData favouriteRetailData;

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
        favouriteRetailData = new FavouriteRetailData(db);
    }


    @Test
    public void addFavouriteRetail() throws Exception {
        favouriteRetailData.addFavouriteRetail("Tester", "Test shop", "1 Test Street");
        ResultSet rs;
        rs = db.executeQuerySQL("SELECT * FROM favourite_retail WHERE name = 'Tester' AND RETAILER_NAME = 'Test shop'" +
                "AND ADDRESS = '1 Test Street';");
        assertFalse(rs.isClosed());
    }

}