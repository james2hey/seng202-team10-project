package dataHandler;

import dataHandler.SQLiteDB;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by jto59 on 21/09/17.
 */
public class FavouriteWifiData {

    static SQLiteDB db;

    private String[] fields =
            {"name         VARCHAR(12)",
                    "WIFI_ID    DOUBLE"};


    String primaryKey = "name, WIFI_ID";
    String tableName = "favourite_wifi";

    static PreparedStatement addWifi;
    static String addWifiStatement = "insert or fail into favourite_wifi values(?,?)";


    public FavouriteWifiData(SQLiteDB db) {
        this.db = db;
        db.addTable(tableName, fields, primaryKey);
        addWifi = db.getPreparedStatement(addWifiStatement);
    }


    public static void addFavouriteWifi(String name, double WIFI_ID) {
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
