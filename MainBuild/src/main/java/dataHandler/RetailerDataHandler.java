package dataHandler;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by jes143 on 18/09/17.
 */

public class RetailerDataHandler {

    SQLiteDB db;

    String[] fields =
          { "RETAILER_NAME      VARCHAR(50) NOT NULL",
            "ADDRESS            VARCHAR(50)",
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

            double[] latlon = Geocoder.addressToLatLon(record[1] + ", " + record[3] + ", " + record[4] + ", " + record[5] + ", ");
            System.out.println(latlon[0]);
            System.out.println(latlon[1]);

            return addSingleEntry(record[0], record[1], latlon[0], latlon[1], record[3], record[4], record[5], record[7], record[8]);

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Incorrect string array size");
            return false;
        } catch (Exception e) {
            System.out.println("Unspecified error");
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
        }
        return true;
    }

    public Boolean addSingleEntry(
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

    public void processCSV(String url) {
        try {
            db.setAutoCommit(false);
            CSVReader reader = new CSVReader(new FileReader(url), ',');

            String[] record;
            reader.readNext(); // Skip first line as it's the desc
            while ((record = reader.readNext()) != null) {
                System.out.println(processLine(record));
            }
            db.setAutoCommit(true);
            db.commit();
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }
}
