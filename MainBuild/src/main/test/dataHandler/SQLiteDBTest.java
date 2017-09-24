package dataHandler;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;

/**
 * Created by jes143 on 19/09/17.
 */
public class SQLiteDBTest {

    private SQLiteDB db;

    @AfterClass
    public static void clearDB() throws Exception {
        String home = System.getProperty("user.home");
        java.nio.file.Path path = java.nio.file.Paths.get(home, "testdatabase.db");
        Files.delete(path);
    }

    @Before
    public void setUp() throws Exception {
        String home = System.getProperty("user.home");
        java.nio.file.Path path = java.nio.file.Paths.get(home, "testdatabase.db");
        db = new SQLiteDB(path.toString());


        WifiDataHandler wdh = new WifiDataHandler(db);
        System.out.println(wdh);
        wdh.processCSV(getClass().getClassLoader().getResource("CSV/NYC_Free_Public_WiFi_03292017-test.csv").getFile());

        RouteDataHandler rdh = new RouteDataHandler(db);
        rdh.processCSV(getClass().getClassLoader().getResource("CSV/201601-citibike-tripdata-test.csv").getFile());

        RetailerDataHandler retailerDataHandler = new RetailerDataHandler(db);
        System.out.println("Made");
        retailerDataHandler.processCSV(getClass().getClassLoader().getResource("CSV/Lower_Manhattan_Retailers-test.csv").getFile());
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void addTable() throws Exception {

    }

    @Test
    public void executeUpdateSQL() throws Exception {

    }

    @Test
    public void executeQuerySQL() throws Exception {

    }

    @Test
    public void getPreparedStatement() throws Exception {

    }

    @Test
    public void setAutoCommit() throws Exception {

    }

    @Test
    public void commit() throws Exception {

    }

    @Test
    public void rollback() throws Exception {

    }



}