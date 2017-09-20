package main;

import dataHandler.SQLiteDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import GUIControllers.LoginController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Created by jto59 on 16/09/17.
 * Deals with users on the start up screen to choose which user is to be logged in or created.
 */
public class HandleUsers {
    private static ArrayList<String> userList = new ArrayList<>();
    public static String currentUserName;
    public static Cyclist currentCyclist;
    public static Analyst currentAnalyst;

    public static ArrayList<String> getUserList() {
        return userList;
    }
    private static SQLiteDB db;

    public static void init() {
        db = Main.getDB();
    }

    public static void fillUserList() {
        try {
            ResultSet rs;
            rs = db.executeQuerySQL("SELECT * FROM users");
            while (rs.next()) {
                userList.add(rs.getString("NAME"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Logs into the user whose parameter is handed into the function.
     * @param username;
     */
    public static void logIn(String username, boolean isCyclist) {
        //Also needs to get favourites list...
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
            currentUserName = username;

        } catch (SQLException e) { //What if the result set is not closed?
            e.getMessage();

            userList.add(username);
        }


        if (isCyclist) {
            currentCyclist = new Cyclist(username);
        } else{
            currentAnalyst = new Analyst(username);
        }
        System.out.println("Logged into " + username + "'s account.");

    }

    /**
     * Logs out of the currently logged in user.
     */
    public static void logOutOfUser() {
        // Will need to change this.
        currentUserName = "";
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
        if (created) {
            currentUserName = username;
        }
        return created;
    }

    /**
     * Creates an instance of the User subclass adding its name.
     * @param username;
     * @param isCyclist;
     * @return user
     */
    public static User createInstance(String username, boolean isCyclist) {
        User user;
        if(isCyclist) {
            user = new Cyclist(username);
        } else {
            user = new Analyst(username);
        }
        return user;
    }


}
