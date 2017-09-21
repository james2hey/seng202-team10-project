package main;

import dataAnalysis.Route;
import dataHandler.SQLiteDB;
import javafx.beans.value.ObservableListValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import GUIControllers.LoginController;

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
    public static Analyst currentAnalyst;
    public static ObservableList<String> allUsers = FXCollections.observableArrayList("1");

    public static ArrayList<String> getUserList() {
        return userList;
    }

    private static SQLiteDB db;

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
        allUsers = FXCollections.observableArrayList("1","2");
        //allUsers.getItems().addAll("1","2");

    }

    /**
     * Logs into the user whose parameter is handed into the function.
     * @param username;
     */
    public static void logIn(String username, boolean isCyclist) {
        ResultSet rs;

        try {
            rs = db.executeQuerySQL("SELECT * FROM users WHERE name = '" + username + "';");
            String type = rs.getString(2);
            //type.charAt(0)
            System.out.println(type.length());
            System.out.println("cyclist".length());

            if (type.charAt(0) == 'c') { // Cant figure out why type != "cyclist".
                currentCyclist = new Cyclist(username);
                System.out.println("Created cyclist instance for " + username);
            } else {
                currentAnalyst = new Analyst(username);
                System.out.println("Created analyst instance for " + username);
            }

        } catch (SQLException e) { //What if the result set is not closed?
            e.getMessage();

            userList.add(username);
        }
        if (isCyclist) {
            currentCyclist = new Cyclist(username);
            getUserFavourites();
        } else{
            currentAnalyst = new Analyst(username);
        }
        System.out.println("Logged into " + username + "'s account.");

    }

    public static void getUserFavourites() {
        ResultSet rsFavourites, rsRoute;
        String name = currentCyclist.getName();
        Route tempRoute;
        try {
            rsFavourites = db.executeQuerySQL("SELECT * FROM favourite_routes WHERE name = '" + name + "';");
            while (rsFavourites.next()) {
                PreparedStatement ps = db.getPreparedStatement("SELECT * FROM route_information where start_year = ? AND start_month = ? AND start_day = ? AND start_time = ? AND bikeid = ?");
                ps.setInt(1, rsFavourites.getInt(2));
                ps.setInt(2, rsFavourites.getInt(3));
                ps.setInt(3, rsFavourites.getInt(4));
                ps.setString(4, rsFavourites.getString(5));
                ps.setString(5, rsFavourites.getString(6));
                rsRoute = ps.executeQuery();
                tempRoute = new Route(rsRoute.getInt("tripduration"), rsRoute.getString("start_time"),
                        rsRoute.getString("end_time"), rsRoute.getInt("start_day"),
                        rsRoute.getInt("start_month"), rsRoute.getInt("start_year"),
                        rsRoute.getInt("end_day"), rsRoute.getInt("end_month"),
                        rsRoute.getInt("end_year"), rsRoute.getDouble("start_latitude"),
                        rsRoute.getDouble("start_longitude"), rsRoute.getDouble("end_latitude"),
                        rsRoute.getDouble("end_longitude"), rsRoute.getInt("start_station_id"),
                        rsRoute.getInt("end_station_id"), rsRoute.getString("start_station_name"),
                        rsRoute.getString("end_station_name"), rsRoute.getString("bikeid"));
                currentCyclist.addRoute(tempRoute, name);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(currentCyclist.getName() + "'s favourite routes:");
        System.out.println("-----------------");
        for (int i = 0; i < currentCyclist.getFavouriteRouteList().size(); i++) {
            System.out.println(currentCyclist.getFavouriteRouteList().get(i).getStartTime());
        }
        System.out.println("-----------------");
        System.out.println();

    }

    /**
     * Logs out of the currently logged in user by terminating both instances of currentAnalyst and currentCyclist.
     */
    public static void logOutOfUser() {
        currentAnalyst = null;
        currentCyclist = null;
    }

    /**
     * Checks if the username already exists, if not it creates a new User and adds them to the users list.
     * @param isCyclist;
     */
    public static boolean createNewUser(String username, boolean isCyclist) {
        ResultSet rs;
        boolean created = false;
        try {
            rs = db.executeQuerySQL("SELECT * FROM users WHERE NAME = '" + username + "';");
            String s = rs.getString("NAME");

        } catch (SQLException e) { //What if the result set is not closed?
            e.getMessage();
            if (isCyclist) {
                currentCyclist = new Cyclist(username);
                DatabaseUser.addUser(username, "cyclist");
            } else {
                currentAnalyst = new Analyst(username);
                DatabaseUser.addUser(username, "analyst");
            }
            userList.add(username);
            created = true;
        }
        return created;
    }
//
//    /**
//     * Creates an instance of the User subclass adding its name.
//     * @param username;
//     * @param isCyclist;
//     * @return user
//     */
//    public static User createInstance(String username, boolean isCyclist) {
//        User user;
//        if(isCyclist) {
//            user = new Cyclist(username);
//        } else {
//            user = new Analyst(username);
//        }
//        return user;
//    }


}
