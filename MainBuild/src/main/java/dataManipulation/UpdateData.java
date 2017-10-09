package dataManipulation;

import dataHandler.SQLiteDB;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * UpdateData class contains methods that are used to update fields of data entries in the database.
 */
public class UpdateData {

    private static SQLiteDB db;

    public static void init(SQLiteDB sqLiteDB) {
        db = sqLiteDB;
    }


    /**
     * updateRouteField updates a single route entry in the database. The route entry to be updated is identified using
     * the bikeID, year, month, day and time parameters as these are the primary keys for the route_information table
     * in the database.
     *
     * @param field  of type String, this is the field in that database to be updated
     * @param value  of type String, this is the new value that will replace the current value in the stated field
     * @param bikeID of type String, this is the bikeID value for the route to be updated
     * @param year   of type String, this is the startYear value for the route to be updated
     * @param month  of type String, this is the startMonth value for the route to be updated
     * @param day    of type String, this is the startDay value for the route to be updated
     * @param time   of type String, this is the startTime value for the route to be updated
     */
    public static void updateRouteField(Object field, Object value, Object bikeID, Object year, Object month, Object day, Object time) {
        String sqlCommand = "UPDATE route_information SET " + field + " = ? WHERE " +
                "bikeid = ? AND start_year = ? AND start_month = ? AND start_day = ? AND start_time = ?;";

        try {
            System.out.println(sqlCommand);
            PreparedStatement pstmt = db.getPreparedStatement(sqlCommand);
            pstmt.setObject(1, value);
            pstmt.setObject(2, bikeID);
            pstmt.setObject(3, year);
            pstmt.setObject(4, month);
            pstmt.setObject(5, day);
            pstmt.setObject(6, time);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * updateWifiField updates a single wifi entry in the database. The wifi entry to be updated is identified using
     * the wifiID parameter as this is the primary key for the wifi_location table
     * in the database.
     *
     * @param field  of type String, this is the field in that database to be updated
     * @param value  of type String, this is the new value that will replace the current value in the stated field
     * @param wifiID of type String, this is the wifiID field for the wifi location to be updated
     */
    public static void updateWifiField(Object field, Object value, Object wifiID) {
        String sqlCommand = "UPDATE wifi_location SET " + field + " = ? WHERE wifi_id = ?;";

        try {
            PreparedStatement pstmt = db.getPreparedStatement(sqlCommand);
            System.out.println(sqlCommand);
            pstmt.setObject(1, value);
            pstmt.setObject(2, wifiID);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * updateRetailerField updates a single retailer entry in the database. The retailer entry to be updated is
     * identified using the name and address parameters as these are the primary keys for the retailer table in the
     * database.
     *
     * @param field   of type String, this is the field in that database to be updated
     * @param value   of type String, this is the new value that will replace the current value in the stated field
     * @param name    of type String, this is the name value of the retail location to be updated
     * @param address of type String, this is the address value of the retail location to be updated
     */
    public static void updateRetailerField(Object field, Object value, Object name, Object address) {
        String sqlCommand = "UPDATE retailer SET " + field + " = ? WHERE retailer_name = ? AND address = ?;";

        try {
            PreparedStatement pstmt = db.getPreparedStatement(sqlCommand);
            System.out.println(sqlCommand);
            pstmt.setObject(1, value);
            pstmt.setObject(2, name);
            pstmt.setObject(3, address);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
