package dataHandler;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.net.ConnectException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import GUIControllers.Controller;

/**
 * Created by jes143 on 18/09/17.
 */

public class RetailerDataHandler {

    SQLiteDB db;

    String[] fields =
          { "RETAILER_NAME      VARCHAR(50) NOT NULL",
            "ADDRESS            VARCHAR(50) NOT NULL",
            "LAT                NUMERIC(9,6) NOT NULL",
            "LONG               NUMERIC(9,6) NOT NULL",
            "CITY               VARCHAR(20)",
            "STATE              VARCHAR(2)",
            "ZIP                VARCHAR(8)",
            "Main_Type          VARCHAR(50)",
            "Secondary_Type     VARCHAR(50)"};

    String primaryKey = "RETAILER_NAME, ADDRESS";
    String tableName = "retailer";

    PreparedStatement addData;
    String addDataStatement = "insert or fail into retailer values(?,?,?,?,?,?,?,?,?)";

    /**
     * Initializes an object, linked to the given database. Can process CSVs and add single entries
     * @param db
     */
    public RetailerDataHandler(SQLiteDB db) {
        this.db = db;
        db.addTable(tableName, fields, primaryKey);
        addData = db.getPreparedStatement(addDataStatement);

    }

    /**
     * Processes a CSV line and adds to the database if valid.
     *
     * @param record A string array of object corresponding to the CSV
     * @return A bool stating the success state of the process.
     */
    private Boolean processLine(String[] record) {
        try {
            System.out.println(record[1]);
            double[] latlon = Geocoder.addressToLatLon(record[1] + ", " + record[3] + ", " + record[4] + ", " + record[5] + ", ");
            if (latlon == null) {
                return false;
            }

            System.out.println(latlon[0]);
            System.out.println(latlon[1]);

            return addSingleEntry(record[0], record[1], latlon[0], latlon[1], record[3], record[4], record[5], record[7], record[8]);

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Incorrect string array size");
            return false;
        }
    }

    /**
     * Takes a full list of parameters for an element in the table and adds that to the database using a PreparedStatement
     * @param RETAILER_NAME
     * @param ADDRESS
     * @param LAT
     * @param LONG
     * @param CITY
     * @param STATE
     * @param ZIP
     * @param MAIN_TYPE
     * @param SECONDARY_TYPE
     * @return A value representing the success of the addition. Fails on such things as PrimaryKey collisions.
     */
    public Boolean addSingleEntry (
            String RETAILER_NAME, String ADDRESS, double LAT, double LONG, String CITY,
            String STATE, String ZIP, String MAIN_TYPE, String SECONDARY_TYPE) {
        try {
            addData.setObject(1, RETAILER_NAME);
            addData.setObject(2, ADDRESS);
            addData.setObject(3, LAT);
            addData.setObject(4, LONG);
            addData.setObject(5, CITY);
            addData.setObject(6, STATE);
            addData.setObject(7, ZIP);
            addData.setObject(8, MAIN_TYPE);
            addData.setObject(9, SECONDARY_TYPE);
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
     * @throws ConnectException Thrown if the geocoder could not establish a connection
     * @throws IOException Thrown if there are errors reading the file
     * @throws NoSuchFieldException Thrown if there are an incorrect number of fields in the CSV
     */
    public int[] processCSV(String url) throws IOException, NoSuchFieldException {
        if (!Geocoder.testConnection())
            throw new ConnectException("Geocoder could not connect");
        int[] successFailCounts = {0, 0};
        db.setAutoCommit(false);
        CSVReader reader = new CSVReader(new FileReader(url), ',');

        String[] record;
        record = reader.readNext(); // Skip first line as it's the desc
        if (record.length != 9) {
            throw new NoSuchFieldException("Incorrect number of fields, expected 9 but got " + record.length);
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
