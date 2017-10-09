//package dataHandler;
//
//import de.saxsys.javafx.test.JfxRunner;
//import de.saxsys.javafx.test.TestInJfxThread;
//import javafx.concurrent.Task;
//import org.junit.*;
//import org.junit.rules.ExpectedException;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.invocation.InvocationOnMock;
//import org.mockito.stubbing.Answer;
//
//import java.nio.file.Files;
//import java.sql.ResultSet;
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;
//
///**
// * Using fake retailerdatahandler as mocks were really complicated as Geocoder is static
// */
//@RunWith(JfxRunner.class)
//public class CSVImporterRetailerTest {
//
//    @Rule
//    public final ExpectedException exception = ExpectedException.none();
//
//    private static SQLiteDB db;
//    private RetailerDataHandlerFake handler;
//
//    @AfterClass
//    public static void clearDB() throws Exception {
//        String home = System.getProperty("user.home");
//        java.nio.file.Path path = java.nio.file.Paths.get(home, "testdatabase.db");
//        Files.delete(path);
//    }
//
//    @BeforeClass
//    public static void setUp() throws Exception {
//        String home = System.getProperty("user.home");
//        java.nio.file.Path path = java.nio.file.Paths.get(home, "testdatabase.db");
//        db = new SQLiteDB(path.toString());
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        db.executeUpdateSQL("DROP TABLE retailer");
//    }
//
//    @Before
//    public void init() throws Exception {
//        handler = new RetailerDataHandlerFake(db);
//    }
//
//    @Test
//    @TestInJfxThread
//    public void processCSVIncorrectFormat() throws Exception {
//        Task<Void> task = new CSVImporter(db, getClass().getClassLoader().getResource("CSV/NYC_Free_Public_WiFi_03292017-test.csv").getFile(), handler);
//        Thread thread = new Thread(task);
//        thread.run();
//        assertEquals("Incorrect number of fields, expected 9 or 18 but got 29\n" +
//                "Did you select the correct CSV file?", task.messageProperty().getValue());
//    }
//
//    @Test
//    @TestInJfxThread
//    public void processCSVInvalidFile() throws Exception {
//        Task<Void> task = new CSVImporter(db, "NotARealFile.csv", handler);
//        Thread thread = new Thread(task);
//        thread.run();
//        assertEquals("That file doesn't exist.\nPlease select a valid file", task.messageProperty().getValue());
//    }
//
//    @Test
//    @TestInJfxThread
//    public void processCSVValidOld() throws Exception {
//        Task<Void> task = new CSVImporter(db, getClass().getClassLoader().getResource("CSV/Lower_Manhattan_Retailers-new-test.csv").getFile(), handler);
//        Thread thread = new Thread(task);
//        thread.run();
//        ResultSet rs = db.executeQuerySQL("SELECT COUNT(*) FROM retailer");
//        assertEquals(50, rs.getInt(1));
//    }
//
//    @Test
//    @TestInJfxThread
//    public void processCSVValidNew() throws Exception {
//        Task<Void> task = new CSVImporter(db, getClass().getClassLoader().getResource("CSV/Lower_Manhattan_Retailers-test.csv").getFile(), handler);
//        Thread thread = new Thread(task);
//        thread.run();
//        ResultSet rs = db.executeQuerySQL("SELECT COUNT(*) FROM retailer");
//        assertEquals(50, rs.getInt(1));
//    }
//
//    @Test
//    @TestInJfxThread
//    public void processCSVValidTwiceOld() throws Exception {
//        Task<Void> task;
//        Thread thread;
//
//        task = new CSVImporter(db, getClass().getClassLoader().getResource("CSV/Lower_Manhattan_Retailers-test.csv").getFile(), handler);
//        thread = new Thread(task);
//        thread.run();
//
//        task = new CSVImporter(db, getClass().getClassLoader().getResource("CSV/Lower_Manhattan_Retailers-test.csv").getFile(), handler);
//        thread = new Thread(task);
//        thread.run();
//
//        ResultSet rs = db.executeQuerySQL("SELECT COUNT(*) FROM retailer");
//        assertEquals(50, rs.getInt(1));
//    }
//
//    @Test
//    @TestInJfxThread
//    public void processCSVValidTwiceNew() throws Exception {
//        Task<Void> task;
//        Thread thread;
//
//        task = new CSVImporter(db, getClass().getClassLoader().getResource("CSV/Lower_Manhattan_Retailers-new-test.csv").getFile(), handler);
//        thread = new Thread(task);
//        thread.run();
//
//        task = new CSVImporter(db, getClass().getClassLoader().getResource("CSV/Lower_Manhattan_Retailers-new-test.csv").getFile(), handler);
//        thread = new Thread(task);
//        thread.run();
//
//        ResultSet rs = db.executeQuerySQL("SELECT COUNT(*) FROM retailer");
//        assertEquals(50, rs.getInt(1));
//    }
//
//    @Ignore
//    @Test
//    @TestInJfxThread
//    public void testImportSpeed() throws Exception {
//        Task task = new CSVImporter(db, getClass().getClassLoader().getResource("CSV/Lower_Manhattan_Retailers.csv").getFile(), handler);
//        Thread thread = new Thread(task);
//        long startTime = System.currentTimeMillis();
//        thread.run();
//
//        long endTime = System.currentTimeMillis();
//        long timeTaken = endTime - startTime;
//        long average = 772/timeTaken;
//        long expectedAverage = 10000/500;
//        System.out.println(timeTaken);
//        System.out.println(average);
//        System.out.println(expectedAverage);
//        ResultSet rs = db.executeQuerySQL("SELECT COUNT(*) FROM retailer");
//        System.out.println(rs.getInt(1));
//        assertTrue(average > expectedAverage);
//    }
//}