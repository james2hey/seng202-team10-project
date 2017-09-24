package dataHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Handles all of the favourite wifi data for every user in the database.
 */
public class FavouriteWifiData {

    private SQLiteDB db;
    private String[] fields =
            {"name         VARCHAR(12)",
                    "WIFI_ID    DOUBLE"};
    private String primaryKey = "name, WIFI_ID";
    private String tableName = "favourite_wifi";
    private PreparedStatement addWifi;
    private String addWifiStatement = "insert or fail into favourite_wifi values(?,?)";

    /**
     * Initializes the database when creating an instance of the FavouriteWifiData.
     * @param db database the wifi data is added to
     */
    public FavouriteWifiData(SQLiteDB db) {
        this.db = db;
        db.addTable(tableName, fields, primaryKey);
        addWifi = db.getPreparedStatement(addWifiStatement);
    }


    /**
     * Adds the given name and wifi id to the table.
     * @param name name of the user
     * @param WIFI_ID identification number of the wifi service
     */
    public void addFavouriteWifi(String name, String WIFI_ID) {
        try {
            addWifi.setObject(1, name);
            addWifi.setObject(2, WIFI_ID);
            addWifi.executeUpdate();
            db.commit();
        } catch (SQLException e) {
            addWifi = db.getPreparedStatement(addWifiStatement);
            System.out.println(e.getMessage());
        }
    }
}
