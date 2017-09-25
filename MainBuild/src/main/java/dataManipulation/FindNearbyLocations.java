package dataManipulation;

import dataAnalysis.RetailLocation;
import dataAnalysis.WifiLocation;
import dataHandler.SQLiteDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class FindNearbyLocations {

    private ArrayList<WifiLocation> nearbyWifi = new ArrayList<>();
    private ArrayList<RetailLocation> nearbyRetail = new ArrayList<>();
    private SQLiteDB db;


    /**
     * Constructor initializes the database for use within the FindNearByLocations class.
     *
     * @param database SQLite database connection
     */
    public FindNearbyLocations(SQLiteDB database) {
        db = database;
    }


    /**
     * generateWifiArray takes a result set, rs of wifi data entries from the database, it converts each one into a
     * WifiLocation object and adds them to and ArrayList.
     *
     * @param rs result set of data entries from the wifi_location table of the database
     */
    private void generateWifiArray(ResultSet rs) {
        try {
            nearbyWifi.clear();
            while (rs.next()) {
                nearbyWifi.add(new WifiLocation(rs.getString("wifi_id"), rs.getDouble("lat"),
                        rs.getDouble("lon"), rs.getString("address"),
                        rs.getString("ssid"), rs.getString("cost"),
                        rs.getString("provider"), rs.getString("remarks"),
                        rs.getString("city"), rs.getString("suburb"),
                        rs.getInt("zip")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Creates a Result Set of wifi data entries from the table wifi_location in the database. Uses the four parameters,
     * an upper and lower latitude and upper and lower longitude to query the database and find all wifi data entries
     * that fall between these values. Returns the result set.
     *
     * @param lowerLat of type Double. A number describing a latitude value
     * @param upperLat of type Double. A number describing a latitude value
     * @param lowerLong of type Double. A number describing a latitude value
     * @param upperLong of type Double. A number describing a latitude value
     * @return result set of wifi data entries from the query to the database
     */
    private ResultSet generateWifiResultSet(double lowerLat, double upperLat, double lowerLong, double upperLong) {
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            String queryString = "SELECT * FROM wifi_location WHERE LAT BETWEEN ? AND ? AND LON BETWEEN ? AND ?;";
            pstmt = db.getPreparedStatement(queryString);
            pstmt.setDouble(1, lowerLat);
            pstmt.setDouble(2, upperLat);
            pstmt.setDouble(3, lowerLong);
            pstmt.setDouble(4, upperLong);
            System.out.println("SELECT * FROM wifi_location WHERE LAT BETWEEN " + lowerLat + " AND " + upperLat +
                    " AND LON BETWEEN " + lowerLong + " AND " + upperLong);
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return rs;
    }


    /**
     * generateRetailArray takes a result set, rs of retail data entries from the database, it converts each one into a
     * RetailLocation object and adds them to and ArrayList.
     *
     * @param rs result set of data entries from the retailer table of the database
     */
    private void generateRetailerArray(ResultSet rs) {
        try {
            nearbyRetail.clear();
            while (rs.next()) {
                nearbyRetail.add(new RetailLocation(rs.getString("retailer_name"),
                        rs.getString("address"), rs.getString("city"),
                        rs.getString("main_type"), rs.getString("secondary_type"),
                        rs.getString("state"), rs.getInt("zip"),
                        rs.getDouble("lat"), rs.getDouble("long")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Creates a Result Set of retail data entries from the table retailer in the database. Uses the four parameters,
     * an upper and lower latitude and upper and lower longitude to query the database and find all retail data entries
     * that fall between these values. Returns the result set.
     *
     * @param lowerLat of type Double. A number describing a latitude value
     * @param upperLat of type Double. A number describing a latitude value
     * @param lowerLong of type Double. A number describing a latitude value
     * @param upperLong of type Double. A number describing a latitude value
     * @return result set of retail data entries from the query to the database
     */
    private ResultSet generateRetailerResultSet(double lowerLat, double upperLat, double lowerLong, double upperLong) {
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            String queryString = "SELECT * FROM retailer WHERE lat BETWEEN ? AND ? AND long BETWEEN ? AND ?;";
            pstmt = db.getPreparedStatement(queryString);
            pstmt.setDouble(1, lowerLat);
            pstmt.setDouble(2, upperLat);
            pstmt.setDouble(3, lowerLong);
            pstmt.setDouble(4, upperLong);
            System.out.println("SELECT * FROM retailer WHERE LAT BETWEEN " + lowerLat + " AND " + upperLat +
                    " AND LON BETWEEN " + lowerLong + " AND " + upperLong);
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return rs;
    }


    /**
     * findNearbyWifi finds wifi data entries that are nearby the coordinates given as the parameters, lat and lon.
     * Returns all found wifi entries as WifiLocation objects in an ArrayList.
     *
     * @param lat of type Double. A number describing a latitude value
     * @param lon of type Double. A number describing a longitude value
     * @return ArrayList<WifiLocation>, contains all results from the query.
     */
    public ArrayList<WifiLocation> findNearbyWifi(double lat, double lon) {
        ResultSet rs = generateWifiResultSet(lat - 0.01, lat + 0.01, lon - 0.01,
                lon + 0.01);
        generateWifiArray(rs);
        return nearbyWifi;
    }


    /**
     * findNearbyRetail finds retail data entries that are nearby the coordinates given as the parameters, lat and lon.
     * Returns all found retail entries as RetailLocation objects in an ArrayList.
     *
     * @param lat of type Double. A number describing a latitude value
     * @param lon of type Double. A number describing a longitude value
     * @return ArrayList<RetailLocation>, contains all results from the query.
     */
    public ArrayList<RetailLocation> findNearbyRetail(double lat, double lon) {
        ResultSet rs = generateRetailerResultSet(lat - 0.01, lat + 0.01, lon - 0.01,
                lon + 0.01);
        generateRetailerArray(rs);
        return nearbyRetail;
    }
}

