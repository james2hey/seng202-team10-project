package dataHandler;

import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.PendingResult;
import com.google.maps.errors.ApiException;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.net.ConnectException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import GUIControllers.Controller;

/**
 * Created by jes143 on 18/09/17.
 */

/**
 * A simple callback interface used to receive a Lat and Lon from the asynchronous request
 */
interface Callback {
    void response(String[] record, double[] latLon);
}

public class RetailerDataHandler implements Callback {

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
    ArrayList<GeocodeOutcome> outcomes;
    int[] successFailCounts = {0, 0};
    int awaiting = 0;

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
     * Processes a line and creates a GeocodeOutcome object from the address, with a callback to itself
     * @param record A string list of values in expected order as defined in the CSV
     * @return GeocodeOutcome object or null if error
     */
    private GeocodeOutcome processLine(String[] record) {
        try {
            System.out.println(record[1]);
            GeocodeOutcome outcome = new GeocodeOutcome(record, this);
            Geocoder.addressToLatLonAsync(record[1] + ", " + record[3] + ", " + record[4] + ", " + record[5] + ", ", outcome);
            System.out.println(outcome);
            return outcome;

        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            awaiting -= 1;
            successFailCounts[1] += 1;
            return null;
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
     * @throws NoSuchFieldException Thrown if there are an incorrect number of fields in the CSV
     * @throws ConnectException Thrown if the geocoder could not establish a connection
     * @throws IOException Thrown if there are errors reading the file
     */
    public int[] processCSV(String url) throws IOException, NoSuchFieldException {


        db.setAutoCommit(false);
        CSVReader reader = new CSVReader(new FileReader(url), ',');

        String[] record;
        record = reader.readNext(); // Skip first line as it's the desc
        if (record.length != 9) {
            throw new NoSuchFieldException("Incorrect number of fields, expected 9 but got " + record.length);
        }
        if (!Geocoder.testConnection())
            throw new ConnectException("Geocoder could not connect");
        while ((record = reader.readNext()) != null) {
            awaiting += 1;
            processLine(record);
        }

        while (awaiting > 0) {
            try {
                Thread.sleep(100);
                System.out.println(awaiting);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        db.setAutoCommit(true);
        db.commit();
        return successFailCounts;
    }

    /**
     * Takes a callback of a string list of the standard CSV values and a converted latLon from the Geocoder
     * @param record A string list of values in expected order as defined in the CSV
     * @param latLon A double list in order lat, lon as calculated by the calling function
     */
    @Override
    public void response(String[] record, double[] latLon) {
        awaiting -= 1;
        if (record == null) {
            successFailCounts[1] += 1;
            return;
        }
        if(addSingleEntry(record[0], record[1], latLon[0], latLon[1], record[3], record[4], record[5], record[7], record[8])) {
            successFailCounts[0] += 1;
        } else {
            successFailCounts[1] += 1;
        }
    }
}
