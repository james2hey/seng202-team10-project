package dataHandler;

import GUIControllers.ProgressPopupController;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;
import org.hamcrest.Matchers;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.testfx.framework.junit.ApplicationTest;

import java.nio.file.Files;
import java.sql.ResultSet;
import java.util.regex.Matcher;

import static org.junit.Assert.*;
import static org.testfx.api.FxAssert.verifyThat;

/**
 * Using fake retailerdatahandler as mocks were really complicated as Geocoder is static
 */
public class CSVImporterRetailerTest {


    private static SQLiteDB db;
    private RetailerDataHandlerFake handler;

    @AfterClass
    public static void clearDB() throws Exception {
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
    }

    @After
    public void tearDown() throws Exception {
        db.executeUpdateSQL("DROP TABLE retailer");
    }

    @Before
    public void init() throws Exception {
        handler = new RetailerDataHandlerFake(db);
    }

    @Test
    public void processCSVIncorrectFormat() throws Exception {
        Task<Void> task = new CSVImporter(db, getClass().getClassLoader().getResource("CSV/NYC_Free_Public_WiFi_03292017-test.csv").getFile(), handler);
        task.run();
        ResultSet rs = db.executeQuerySQL("SELECT COUNT(*) FROM retailer");
        assertEquals(0, rs.getInt(1));
    }

    @Test
    public void processCSVInvalidFile() throws Exception {
        Task<Void> task = new CSVImporter(db, "NotARealFile.csv", handler);
        task.run();
        ResultSet rs = db.executeQuerySQL("SELECT COUNT(*) FROM retailer");
        assertEquals(0, rs.getInt(1));
    }

    @Test
    public void processCSVValidOld() throws Exception {
        Task<Void> task = new CSVImporter(db, getClass().getClassLoader().getResource("CSV/Lower_Manhattan_Retailers-new-test.csv").getFile(), handler);
        task.run();
        ResultSet rs = db.executeQuerySQL("SELECT COUNT(*) FROM retailer");
        assertEquals(50, rs.getInt(1));
    }

    @Test
    public void processCSVValidNew() throws Exception {
        Task<Void> task = new CSVImporter(db, getClass().getClassLoader().getResource("CSV/Lower_Manhattan_Retailers-test.csv").getFile(), handler);
        task.run();
        ResultSet rs = db.executeQuerySQL("SELECT COUNT(*) FROM retailer");
        assertEquals(50, rs.getInt(1));
    }

    @Test
    public void processCSVValidTwiceOld() throws Exception {
        Task<Void> task;

        task = new CSVImporter(db, getClass().getClassLoader().getResource("CSV/Lower_Manhattan_Retailers-test.csv").getFile(), handler);
        task.run();

        task = new CSVImporter(db, getClass().getClassLoader().getResource("CSV/Lower_Manhattan_Retailers-test.csv").getFile(), handler);
        task.run();

        ResultSet rs = db.executeQuerySQL("SELECT COUNT(*) FROM retailer");
        assertEquals(50, rs.getInt(1));
    }

    @Test
    public void processCSVValidTwiceNew() throws Exception {
        Task<Void> task;

        task = new CSVImporter(db, getClass().getClassLoader().getResource("CSV/Lower_Manhattan_Retailers-new-test.csv").getFile(), handler);
        task.run();

        task = new CSVImporter(db, getClass().getClassLoader().getResource("CSV/Lower_Manhattan_Retailers-new-test.csv").getFile(), handler);
        task.run();

        ResultSet rs = db.executeQuerySQL("SELECT COUNT(*) FROM retailer");
        assertEquals(50, rs.getInt(1));
    }

    @Ignore
    @Test
    public void testImportSpeed() throws Exception {
        Task task = new CSVImporter(db, getClass().getClassLoader().getResource("CSV/Lower_Manhattan_Retailers.csv").getFile(), handler);
        long startTime = System.currentTimeMillis();
        task.run();

        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;
        long average = 772/timeTaken;
        long expectedAverage = 10000/500;
        System.out.println(timeTaken);
        System.out.println(average);
        System.out.println(expectedAverage);
        ResultSet rs = db.executeQuerySQL("SELECT COUNT(*) FROM retailer");
        System.out.println(rs.getInt(1));
        assertTrue(average > expectedAverage);
    }
}