package main;

import dataAnalysis.RetailLocation;
import dataAnalysis.Route;
import dataAnalysis.WifiLocation;
import dataHandler.SQLiteDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Created by jto59 on 16/09/17.
 * Deals with users on the start up screen to choose which user is to be logged in or created.
 */
public class HandleUsers {
    private static ArrayList<String> userList = new ArrayList<>();
    //public static String currentUserName;
    public static Cyclist currentCyclist;

    public static ArrayList<String> getUserList() {
        return userList;
    }

    private static SQLiteDB db;


    /**
     * Initializes the database.
     */
    public static void init() {
        db = Main.getDB();
    }


    /**
     * Fills the database with existing users from an external csv.
     */
    public static void fillUserList() {
        try {
            ResultSet rs;
            rs = db.executeQuerySQL("SELECT * FROM users;");
            while (rs.next()) {
                userList.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Logs into the user whose parameter is handed into the function.
     * @param username user to be logged in
     */
    public static void logIn(String username) {
        currentCyclist = new Cyclist(username);
        getUserRouteFavourites();
        getUserWifiFavourites();
        getUserRetailFavourites();

    }


    /**
     * Gets all of the Cyclists favourite Route's and adds them to the instances list.
     */
    public static void getUserRouteFavourites() {
        ResultSet rsFavourites, rsRoute;
        String name = currentCyclist.getName();
        Route tempRoute;
        try {
            rsFavourites = db.executeQuerySQL("SELECT * FROM favourite_routes WHERE name = '" + name + "';");
            while (rsFavourites.next()) {
                PreparedStatement ps = db.getPreparedStatement("SELECT * FROM route_information where start_year = ? AND start_month = ? AND start_day = ? AND start_time = ? AND bikeid = ?");
                ps.setString(1, rsFavourites.getString(2));
                ps.setString(2, rsFavourites.getString(3));
                ps.setString(3, rsFavourites.getString(4));
                ps.setString(4, rsFavourites.getString(5));
                ps.setString(5, rsFavourites.getString(6));
                rsRoute = ps.executeQuery();
                tempRoute = new Route(rsRoute.getInt("tripduration"), rsRoute.getString("start_time"),
                        rsRoute.getString("end_time"), rsRoute.getString("start_day"),
                        rsRoute.getString("start_month"), rsRoute.getString("start_year"),
                        rsRoute.getString("end_day"), rsRoute.getString("end_month"),
                        rsRoute.getString("end_year"), rsRoute.getDouble("start_latitude"),
                        rsRoute.getDouble("start_longitude"), rsRoute.getDouble("end_latitude"),
                        rsRoute.getDouble("end_longitude"), rsRoute.getInt("start_station_id"),
                        rsRoute.getInt("end_station_id"), rsRoute.getString("start_station_name"),
                        rsRoute.getString("end_station_name"), rsRoute.getString("bikeid"));
                currentCyclist.addRouteInstance(tempRoute);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Gets all of the Cyclists favourite WifiLocation's and adds them to the instances list.
     */
    public static void getUserWifiFavourites() {
        ResultSet rsFavourites, rsWifi;
        String name = currentCyclist.getName();
        WifiLocation tempWifi;
        try {
            rsFavourites = db.executeQuerySQL("SELECT * FROM favourite_wifi WHERE name = '" + name + "';");
            while (rsFavourites.next()) {
                PreparedStatement ps = db.getPreparedStatement("SELECT * FROM wifi_location where WIFI_ID = ?");

                ps.setInt(1, rsFavourites.getInt(2));
                rsWifi = ps.executeQuery();
                tempWifi = new WifiLocation(rsWifi.getDouble("wifi_id"), rsWifi.getDouble("lat"),
                        rsWifi.getDouble("lon"), rsWifi.getString("address"),
                        rsWifi.getString("ssid"), rsWifi.getString("cost"),
                        rsWifi.getString("provider"), rsWifi.getString("remarks"),
                        rsWifi.getString("city"), rsWifi.getString("suburb"),
                        rsWifi.getInt("zip"));
                currentCyclist.addWifiInstance(tempWifi);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Gets all of the Cyclists favourite RetailLocation's and adds them to the instances list.
     */
    public static void getUserRetailFavourites() {
        ResultSet rsFavourites, rsRetail;
        String name = currentCyclist.getName();
        RetailLocation tempRetail;
        try {
            rsFavourites = db.executeQuerySQL("SELECT * FROM favourite_retail WHERE name = '" + name + "';");
            while (rsFavourites.next()) {
                PreparedStatement ps = db.getPreparedStatement("SELECT * FROM retailer where RETAILER_NAME = ? AND ADDRESS = ?");

                ps.setString(1, rsFavourites.getString(2));
                ps.setString(2, rsFavourites.getString(3));
                rsRetail = ps.executeQuery();
                tempRetail = new RetailLocation(rsRetail.getString("retailer_name"),
                        rsRetail.getString("address"), rsRetail.getString("city"),
                        rsRetail.getString("main_type"), rsRetail.getString("secondary_type"),
                        rsRetail.getInt("zip"), rsRetail.getDouble("lat"),
                        rsRetail.getDouble("long"));
                currentCyclist.addRetailInstance(tempRetail);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    /**
     * Logs out of the currently logged in user by terminating the instance of the currentCyclist.
     */
    public static void logOutOfUser() {
        currentCyclist = null;
    }


    /**
     * Checks if the username already exists, if not it creates a new user and adds them to the users list.
     * @param username the user who is getting an instance created for them
     */
    public static boolean createNewUser(String username) {
        ResultSet rs;
        boolean created = false;
        try {
            rs = db.executeQuerySQL("SELECT * FROM users WHERE NAME = '" + username + "';");
            String s = rs.getString("NAME");

        } catch (SQLException e) { //What if the result set is not closed?
            e.getMessage();
            currentCyclist = new Cyclist(username);
            DatabaseUser.addUser(username);
        }
        userList.add(username);
        created = true;
        return created;
    }
}
