package dataHandler;

import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.SyncFailedException;
import java.net.ConnectException;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * Created by jes143 on 25/09/17.
 */
public class RetailerDataHandlerTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private static SQLiteDB db;
    private static RetailerDataHandler retailerDataHandler;

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
        db.executeUpdateSQL("DROP TABLE retailer");
    }

    @Before
    public void init() throws Exception {
        retailerDataHandler = new RetailerDataHandler(db);
    }

    @Test
    public void validTable() throws Exception {
        ResultSet resultSet = db.executeQuerySQL("SELECT name FROM sqlite_master WHERE type='table' AND name='retailer'");
        assertTrue(resultSet.next());
    }

    @Test
    public void addSingleEntry() throws Exception {
        Boolean success = retailerDataHandler.addSingleEntry("name", "address", 0.0, 0.0, "city", "state", "zip", "type1", "type2");
        assertTrue(success);
    }

    @Test
    public void addSingleEntryAlreadyExists() throws Exception {
        retailerDataHandler.addSingleEntry("name", "address", 0.0, 0.0, "city", "state", "zip", "type1", "type2");
        Boolean success = retailerDataHandler.addSingleEntry("name", "address", 0.0, 0.0, "city", "state", "zip", "type1", "type2");

        assertFalse(success);
    }

    @Test
    public void addSingleEntryNullPrimaryKeys() throws Exception {
        Boolean success = retailerDataHandler.addSingleEntry(null, "address", 0.0, 0.0, "city", "state", "zip", "type1", "type2");
        assertFalse(success);
    }

    @Test
    public void processCSVIncorrectFormat() throws Exception {
        exception.expect(NoSuchFieldException.class);
        retailerDataHandler.processCSV(getClass().getClassLoader().getResource("CSV/NYC_Free_Public_WiFi_03292017-test.csv").getFile());
    }

    @Test
    public void processCSVInvalidFile() throws Exception {
        exception.expect(FileNotFoundException.class);
        retailerDataHandler.processCSV("NotARealFile.csv");
    }

    @Test
    public void processCSVValid() throws Exception {
        Geocoder.init();
        //exception.expect(ConnectException.class);
        retailerDataHandler.processCSV(getClass().getClassLoader().getResource("CSV/Lower_Manhattan_Retailers-test-2.csv").getFile());
        ResultSet rs = db.executeQuerySQL("SELECT COUNT(*) FROM retailer");
        assertEquals(2, rs.getInt(1));
        //System.out.println(rs.getInt(1));
    }

    @Test
    public void processCSVValidTwice() throws Exception {
        Geocoder.init();
        retailerDataHandler.processCSV(getClass().getClassLoader().getResource("CSV/Lower_Manhattan_Retailers-test-2.csv").getFile());
        retailerDataHandler.processCSV(getClass().getClassLoader().getResource("CSV/Lower_Manhattan_Retailers-test-2.csv").getFile());
        ResultSet rs = db.executeQuerySQL("SELECT COUNT(*) FROM retailer");
        assertEquals(2, rs.getInt(1));
    }

    @Ignore
    @Test
    public void testImportSpeed() throws Exception {
        Geocoder.init();
        long startTime = System.currentTimeMillis();
        int[] results = retailerDataHandler.processCSV(getClass().getClassLoader().getResource("CSV/Lower_Manhattan_Retailers-test.csv").getFile());

        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;
        double average = 50/timeTaken;
        long expectedAverage = 10000/500;
        System.out.println(timeTaken);
        System.out.println(average);
        System.out.println(expectedAverage);
        System.out.println(results[0]);
        System.out.println(results[1]);
        assertTrue(average > expectedAverage);
    }
}