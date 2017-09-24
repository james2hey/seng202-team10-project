package dataManipulation;

import dataAnalysis.Route;
import dataAnalysis.WifiLocation;
import dataAnalysis.RetailLocation;
import dataHandler.SQLiteDB;

import java.sql.*;
import java.util.ArrayList;


public class FindNearbyLocations {
    private ArrayList<WifiLocation> nearbyWifi = new ArrayList<>();
    private ArrayList<RetailLocation> nearbyRetail = new ArrayList<>();
    private SQLiteDB db;

    public FindNearbyLocations(SQLiteDB database) {
        db = database;
    }

    private void generateWifiArray(ResultSet rs) {
        try {
            nearbyWifi.clear();
            while (rs.next()) {
                nearbyWifi.add(new WifiLocation(rs.getDouble("wifi_id"), rs.getDouble("lat"),
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
            System.out.println("SELECT * FROM wifi_location WHERE LAT BETWEEN " + lowerLat + " AND " + upperLat + " AND LON BETWEEN " + lowerLong + " AND " + upperLong);
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return rs;
    }


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
            System.out.println("SELECT * FROM retailer WHERE LAT BETWEEN " + lowerLat + " AND " + upperLat + " AND LON BETWEEN " + lowerLong + " AND " + upperLong);
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return rs;
    }

    public ArrayList<WifiLocation> findNearbyWifi(double lat, double lon) {
        ResultSet rs = generateWifiResultSet(lat - 0.01, lat + 0.01, lon - 0.01, lon + 0.01);
        generateWifiArray(rs);
        return nearbyWifi;
    }

    public ArrayList<WifiLocation> findNearbyWifi(double lat1, double lon1, double lat2, double lon2) {
        double routeUpperLat = Double.max(lat1, lat2);
        double routeLowerLat = Double.min(lat1, lat2);
        double routeUpperLong = Double.max(lon1, lon2);
        double routeLowerLong = Double.min(lon1, lon2);
        double upperLat = routeUpperLat + 0.01;
        double lowerLat = routeLowerLat - 0.01;
        double upperLong = routeUpperLong + 0.01;
        double lowerLong = routeLowerLong - 0.01;

        ResultSet rs = generateWifiResultSet(lowerLat, upperLat, lowerLong, upperLong);
        generateWifiArray(rs);
        return nearbyWifi;
    }

    public ArrayList<RetailLocation> findNearbyRetail(double lat, double lon) {
        ResultSet rs = generateRetailerResultSet(lat - 0.01, lat + 0.01, lon - 0.01, lon + 0.01);
        generateRetailerArray(rs);
        return nearbyRetail;
    }

    public ArrayList<RetailLocation> findNearbyRetail(double lat1, double lon1, double lat2, double lon2) {
        double routeUpperLat = Double.max(lat1, lat2);
        double routeLowerLat = Double.min(lat1, lat2);
        double routeUpperLong = Double.max(lon1, lon2);
        double routeLowerLong = Double.min(lon1, lon2);
        double upperLat = routeUpperLat + 0.01;
        double lowerLat = routeLowerLat - 0.01;
        double upperLong = routeUpperLong + 0.01;
        double lowerLong = routeLowerLong - 0.01;

        ResultSet rs = generateRetailerResultSet(lowerLat, upperLat, lowerLong, upperLong);
        generateRetailerArray(rs);
        return nearbyRetail;
    }
}

