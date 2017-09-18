package dataManipulation;

import dataAnalysis.Route;
import dataAnalysis.WifiLocation;
import dataAnalysis.RetailLocation;
import dataHandler.SQLiteDB;
import main.Main;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.DoubleSummaryStatistics;


public class FindNearbyLocations {
    private static ArrayList<WifiLocation> nearbyWifi = new ArrayList<>();
    private static ArrayList<RetailLocation> nearbyRetail = new ArrayList<>();
    private static SQLiteDB db;

    public static void init(SQLiteDB database) {
        db = database;
    }

    private static void generateWifiArray(ResultSet rs) {
        clearWifiArray();
        try {
            while (rs.next()) {
                nearbyWifi.add(new WifiLocation(rs.getDouble("WIFI_ID"), rs.getDouble("LAT"),
                        rs.getDouble("LON"), rs.getString("SSID"),
                        rs.getString("ADDRESS"), rs.getString("SSID")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    private static void clearWifiArray() {
        nearbyWifi.clear();
    }



    public static ArrayList<WifiLocation> findNearbyWifi(Route route) {
        double routeUpperLat;
        double routeLowerLat;
        double routeUpperLong;
        double routeLowerLong;
        double upperLat;
        double lowerLat;
        double upperLong;
        double lowerLong;
        double startLat = route.getStartLocation().getLatitude();
        double startLong = route.getStartLocation().getLongitude();
        double endLat = route.getEndLocation().getLatitude();
        double endLong = route.getEndLocation().getLongitude();

        routeUpperLat = Double.max(startLat, endLat);
        routeLowerLat = Double.min(startLat, endLat);
        routeUpperLong = Double.max(startLong, endLong);
        routeLowerLong = Double.min(startLong, endLong);

        upperLat = routeUpperLat + 0.01;
        lowerLat = routeLowerLat - 0.01;
        upperLong = routeUpperLong + 0.01;
        lowerLong = routeLowerLong - 0.01;
        try {
            String queryString = "SELECT * FROM wifi_location WHERE LAT BETWEEN ? AND ? AND LON BETWEEN ? AND ?;";
            PreparedStatement pstmt = db.getPreparedStatement(queryString);
            pstmt.setDouble(1, lowerLat);
            pstmt.setDouble(2, upperLat);
            pstmt.setDouble(3, lowerLong);
            pstmt.setDouble(4, upperLong);
            System.out.println("SELECT * FROM wifi_location WHERE LAT BETWEEN " + lowerLat + " AND " + upperLat + " AND LON BETWEEN " + lowerLong + " AND " + upperLong);
            ResultSet rs = pstmt.executeQuery();
            generateWifiArray(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return nearbyWifi;
    }
}
