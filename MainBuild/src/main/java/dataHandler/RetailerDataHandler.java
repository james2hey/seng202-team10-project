package dataHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by jes143 on 18/09/17.
 */

public class RetailerDataHandler implements DataHandler, GeoCallback {

    private SQLiteDB db;

    private String[] fields =
            {"RETAILER_NAME      VARCHAR(50) NOT NULL",
                    "ADDRESS            VARCHAR(50) NOT NULL",
                    "LAT                NUMERIC(9,6) NOT NULL",
                    "LONG               NUMERIC(9,6) NOT NULL",
                    "CITY               VARCHAR(20)",
                    "STATE              VARCHAR(2)",
                    "ZIP                VARCHAR(8)",
                    "Main_Type          VARCHAR(50)",
                    "Secondary_Type     VARCHAR(50)",
                    "list_name          VARCHAR(25)"};

    private String primaryKey = "RETAILER_NAME, ADDRESS";
    private String tableName = "retailer";

    private PreparedStatement addData;
    private String addDataStatement = "insert or fail into retailer values(?,?,?,?,?,?,?,?,?,?)";
    private int[] fieldCounts = {9, 18};

    /**
     * Initializes an object, linked to the given database. Can process CSVs and add single entries
     *
     * @param db
     */
    public RetailerDataHandler(SQLiteDB db) {
        this.db = db;
        db.addTable(tableName, fields, primaryKey);
        addData = db.getPreparedStatement(addDataStatement);

    }

    /**
     * Processes a CSV line and adds to the database if valid.
     * Has the ability to handle the new and old format of CSV from data.gov
     * To add support for a new format, just add some extra checks, and if there is a different number of fields, add that number to the fieldCounts list
     *
     * @param record A string array of object corresponding to the CSV
     * @return A bool stating the success state of the process.
     */
    public void processLine(String[] record, Callback callback) {
        if (record.length == 18 && !record[10].equals("")) {
            double lat;
            double lon;
            try {
                lat = Double.parseDouble(record[10]);
                lon = Double.parseDouble(record[11]);
            } catch (NumberFormatException e) {
                callback.result(false);
                return;
            }
            callback.result(addSingleEntry(record[0], record[1], lat, lon, record[3], record[4], record[5], record[7], record[8]));
        } else if (record.length == 9 || record.length == 18){
            GeocodeOutcome outcome = new GeocodeOutcome(record, callback, this);
            Geocoder.addressToLatLonAsync(record[1] + ", " + record[3] + ", " + record[4] + ", " + record[5] + ", ", outcome);
        } else {
            callback.result(false);
        }
    }

    /**
     * Takes a full list of parameters for an element in the table and adds that to the database using a PreparedStatement
     *
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
    public Boolean addSingleEntry(
            String RETAILER_NAME, String ADDRESS, double LAT, double LONG, String CITY,
            String STATE, String ZIP, String MAIN_TYPE, String SECONDARY_TYPE) {
        String listName = dataHandler.ListDataHandler.getListName();
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
            addData.setObject(10, listName);
            addData.executeUpdate();
            return true;
        } catch (SQLException e) {
            addData = db.getPreparedStatement(addDataStatement);
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public void result(String[] record, double[] latlon, Callback callback) {
        System.out.println("5");
        System.out.println(latlon[0]);
        System.out.println(latlon[1]);
        callback.result(addSingleEntry(record[0], record[1], latlon[0], latlon[1], record[3], record[4], record[5], record[7], record[8]));
    }

    @Override
    public boolean canProcess(int columnCount) {
        for (int count: fieldCounts) {
            if (columnCount == count) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getFieldCounts() {
        String out = "";
        for (int count: fieldCounts) {
            out += Integer.toString(count) + " or ";
        }
        return out.substring(0, out.length() - 4);
    }
}
