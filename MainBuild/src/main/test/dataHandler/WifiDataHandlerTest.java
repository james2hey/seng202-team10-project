package dataHandler;

import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;
import java.io.IOException;
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
public class WifiDataHandlerTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private static SQLiteDB db;
    private static WifiDataHandler wifiDataHandler;

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
        db.executeUpdateSQL("DROP TABLE wifi_location");
    }

    @Before
    public void init() throws Exception {
        wifiDataHandler = new WifiDataHandler(db);
    }

    @Test
    public void validTable() throws Exception {
        ResultSet resultSet = db.executeQuerySQL("SELECT name FROM sqlite_master WHERE type='table' AND name='wifi_location'");
        assertTrue(resultSet.next());
    }

    @Test
    public void addSingleEntry() throws Exception {
        Boolean success = wifiDataHandler.addSingleEntry("ID", "cost", "provider", "address", 0.0, 0.0, "remarks", "city", "ssid", "suburb", "zip");
        assertTrue(success);
    }

    @Test
    public void addSingleEntryAlreadyExists() throws Exception {
        wifiDataHandler.addSingleEntry("ID", "cost", "provider", "address", 0.0, 0.0, "remarks", "city", "ssid", "suburb", "zip");
        Boolean success = wifiDataHandler.addSingleEntry("ID", "cost", "provider", "address", 0.0, 0.0, "remarks", "city", "ssid", "suburb", "zip");

        assertFalse(success);
    }

    @Test
    public void addSingleEntryNullPrimaryKeys() throws Exception {
        Boolean success = wifiDataHandler.addSingleEntry(null, "cost", "provider", "address", 0.0, 0.0, "remarks", "city", "ssid", "suburb", "zip");
        assertFalse(success);
    }

    @Test
    public void processCSVIncorrectFormat() throws Exception {
        exception.expect(NoSuchFieldException.class);
        wifiDataHandler.processCSV(getClass().getClassLoader().getResource("CSV/Lower_Manhattan_Retailers-test.csv").getFile());
    }

    @Test
    public void processCSVInvalidFile() throws Exception {
        exception.expect(FileNotFoundException.class);
        wifiDataHandler.processCSV("NotARealFile.csv");
    }

    @Test
    public void processCSVValid() throws Exception {
        Geocoder.init();
        //exception.expect(ConnectException.class);
        wifiDataHandler.processCSV(getClass().getClassLoader().getResource("CSV/NYC_Free_Public_WiFi_03292017-test.csv").getFile());
        ResultSet rs = db.executeQuerySQL("SELECT COUNT(*) FROM wifi_location");
        assertEquals(50, rs.getInt(1));
        //System.out.println(rs.getInt(1));
    }

    @Test
    public void processCSVValidTwice() throws Exception {
        Geocoder.init();
        wifiDataHandler.processCSV(getClass().getClassLoader().getResource("CSV/NYC_Free_Public_WiFi_03292017-test.csv").getFile());
        wifiDataHandler.processCSV(getClass().getClassLoader().getResource("CSV/NYC_Free_Public_WiFi_03292017-test.csv").getFile());
        ResultSet rs = db.executeQuerySQL("SELECT COUNT(*) FROM wifi_location");
        assertEquals(50, rs.getInt(1));
    }
}