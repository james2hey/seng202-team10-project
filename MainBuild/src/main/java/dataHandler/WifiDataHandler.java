package dataHandler;

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
