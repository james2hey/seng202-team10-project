package dataHandler;

import GUIControllers.Controller;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by jes143 on 17/09/17.
 */
public class WifiDataHandler {
    SQLiteDB db;

    String[] fields =
          { "WIFI_ID    VARCHAR(20)" ,
            "COST       VARCHAR(12)" ,
            "PROVIDER   VARCHAR(20)" ,
            "ADDRESS    VARCHAR(50)" ,
            "LAT        NUMERIC(9,6) NOT NULL" ,
            "LON        NUMERIC(9,6) NOT NULL" ,
            "REMARKS    VARCHAR(50)" ,
            "CITY       VARCHAR(8)" ,
            "SSID       VARCHAR(50) NOT NULL" ,
            "SUBURB     VARCHAR(20)" ,
            "ZIP        VARCHAR(8)"};

    String primaryKey = "WIFI_ID";
    String tableName = "wifi_location";

    PreparedStatement addData;
    String addDataStatement = "insert or fail into wifi_location values(?,?,?,?,?,?,?,?,?,?,?)";

    /**
     * Initializes an object, linked to the given database. Can process CSVs and add single entries
     * @param db
     */
    public WifiDataHandler (SQLiteDB db) {
        this.db = db;
        db.addTable(tableName, fields, primaryKey);
        addData = db.getPreparedStatement(addDataStatement);

    }

    /**
     * Processes a CSV line and adds to the database if valid.
     * @param record A string array of object corresponding to the CSV
     * @return A bool stating the success state of the process.
     */
    private Boolean processLine(String[] record) {
        try {
            double lat = Double.parseDouble(record[7]);
            double lon = Double.parseDouble(record[8]);


            return addSingleEntry(record[0], record[3], record[4], record[6], lat, lon, record[12], record[13], record[14], record[18], record[22]);

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Incorrect string array size");
            return false;
        }
    }

    /**
     * Takes a full list of parameters for an element in the table and adds that to the database using a PreparedStatement
     * @param ID
     * @param COST
     * @param PROVIDER
     * @param ADDRESS
     * @param LAT
     * @param LONG
     * @param REMARKS
     * @param CITY
     * @param SSID
     * @param SUBURB
     * @param ZIP
     * @return A value representing the success of the addition. Fails on such things as PrimaryKey collisions.
     */
    public Boolean addSingleEntry(
            String ID, String COST, String PROVIDER, String ADDRESS, double LAT, double LONG,
            String REMARKS,String CITY, String SSID, String SUBURB, String ZIP) {
        try {
            addData.setObject(1, ID);
            addData.setObject(2, COST);
            addData.setObject(3, PROVIDER);
            addData.setObject(4, ADDRESS);
            addData.setObject(5, LAT);
            addData.setObject(6, LONG);
            addData.setObject(7, REMARKS);
            addData.setObject(8, CITY);
            addData.setObject(9, SSID);
            addData.setObject(10, SUBURB);
            addData.setObject(11, ZIP);
            addData.executeUpdate();
            return true;
        } catch (SQLException e ) {
            addData = db.getPreparedStatement(addDataStatement);
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Takes a CSV file and repeatedly calls processLine on the records
     * @param url A string directing to a valid filepath
     * @return An integer list with the count of successful additions and failed additions.
     * @throws IOException Thrown if there are errors reading the file
     * @throws NoSuchFieldException Thrown if there are an incorrect number of fields in the CSV
     */
    public int[] processCSV(String url) throws IOException, NoSuchFieldException {
        int[] successFailCounts = {0, 0};
        db.setAutoCommit(false);
        CSVReader reader = new CSVReader(new FileReader(url), ',');

        String[] record;
        record = reader.readNext(); // Skip first line as it's the desc
        if (record.length != 29) {
            throw new NoSuchFieldException("Incorrect number of fields, expected 29 but got " + record.length);
        }
        while ((record = reader.readNext()) != null) {
            if (processLine(record)) {
                successFailCounts[0] += 1;
                System.out.println("Suc");
            } else {
                successFailCounts[1] += 1;
                System.out.println("F");
            }
        }
        db.setAutoCommit(true);
        db.commit();
        return successFailCounts;
    }
}
