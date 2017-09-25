package dataHandler;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testing that the import times for for each CSV type are meeting the requirement
 * of a rate 0.5s per 10 000 data points.
 */
public class CSVImportTimeTests {
    private SQLiteDB db;


    @Before
    public void setUp() throws Exception {
        String home = System.getProperty("user.home");
        java.nio.file.Path path = java.nio.file.Paths.get(home, "testdatabase.db");
        db = new SQLiteDB(path.toString());
    }

    @Test
    public void routeImportTime() {
        long startTime = System.nanoTime();
        int numberOfPoints = 50;

        //Call route RouteImport method for 100 points;

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        long rate = duration / numberOfPoints;
        assertTrue(rate < 0.5/10000);
    }

    public void wifiImportTime() {

    }

    public void retailImportTime() {

    }

}
