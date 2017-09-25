package dataManipulation;

import dataHandler.SQLiteDB;
import main.Main;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by mki58 on 23/09/17.
 */
public class UpdateData {


    /**
     * updateRouteField updates a single route entry in the database. The route entry to be updated is identified using
     * the bikeID, year, month, day and time parameters as these are the primary keys for the route_information table
     * in the database.
     *
     * @param field
     * @param value
     * @param bikeID
     * @param year
     * @param month
     * @param day
     * @param time
     */
    public static void updateRouteField(String field, String value, String bikeID, String year, String month, String day, String time) {
        SQLiteDB db = Main.getDB();
        String sqlCommand = "UPDATE route_information SET " + field + " = ? WHERE " +
                "bikeid = ? AND start_year = ? AND start_month = ? AND start_day = ? AND start_time = ?;";

        try {
            PreparedStatement pstmt = db.getPreparedStatement(sqlCommand);
            System.out.println(sqlCommand);
            pstmt.setString(1, value);
            pstmt.setString(2, bikeID);
            pstmt.setString(3, year);
            pstmt.setString(4, month);
            pstmt.setString(5, day);
            pstmt.setString(6, time);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     *
     *
     * @param field
     * @param value
     * @param wifiID
     */
    public static void updateWifiField(String field, String value, String wifiID) {
        SQLiteDB db = Main.getDB();
        String sqlCommand = "UPDATE wifi_location SET " + field + " = ? WHERE wifi_id = ?;";

        try {
            PreparedStatement pstmt = db.getPreparedStatement(sqlCommand);
            System.out.println(sqlCommand);
            System.out.println(wifiID);
            pstmt.setString(1, value);
            pstmt.setString(2, wifiID);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     *
     *
     * @param field
     * @param value
     * @param name
     * @param address
     */
    public static void updateRetailerField(String field, String value, String name, String address) {
        SQLiteDB db = Main.getDB();
        String sqlCommand = "UPDATE retailer SET " + field + " = ? WHERE retailer_name = ? AND address = ?;";

        try {
            PreparedStatement pstmt = db.getPreparedStatement(sqlCommand);
            System.out.println(sqlCommand);
            pstmt.setString(1, value);
            pstmt.setString(2, name);
            pstmt.setString(3, address);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
