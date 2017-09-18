package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static main.DatabaseManager.stmt;

/**
 * Created by jto59 on 16/09/17.
 * Deals with users on the start up screen to choose which user is to be logged in or created.
 */
public class HandleUsers {
    private static ArrayList<String> userList = new ArrayList<>();

    public static ArrayList<String> getUserList() {
        return userList;
    }

    public static void fillUserList() {
        try {
            ResultSet rs;
            rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                userList.add(rs.getString("NAME"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
//        for (int i = 0; i < userList.size(); i++) {
//            Controller.existingUsers.getItems().add(userList.get(i));
//        }
        //existingUsers.setVisibleRowCount(userList.size());
        //existingUsers.getItems().removeAll(existingUsers.getItems());
//        ObservableList<String> options = FXCollections.observableArrayList(
//                "ONE",
//                "TWO",
//                "THREE"
//        );
//
//        existingUsers = new ComboBox(options);
//        existingUsers.getItems().addAll(
//                "FOUR",
//                "FIVE"
//        );
    }
    /**
     * Logs into the user whose parameter is handed into the function.
     * @param username;
     */
    public static void logIn(String username, boolean isCyclist) {
        //Also needs to get favourites list...
        User user = createInstance(username, true);
    }

    /**
     * Logs out of the currently logged in user.
     */
    public static void logOut() {
        //Restart to main screen!!!!!!!!!
    }

    /**
     * Checks if the username already exists, if not it creates a new User and adds them to the users list.
     * @param isCyclist;
     */
    public static boolean createNewUser(String username, boolean isCyclist) {
        ResultSet rs;
        boolean created = false;
        try {
            rs = stmt.executeQuery("SELECT * FROM users WHERE NAME = '" + username + "';");
            String s = rs.getString("NAME");
            //System.out.println("Name already in use, pick another.");
            // Prompt for new name.

        }catch (SQLException e) { //What if the result set is not closed?
            e.getMessage();
            User user = createInstance(username, isCyclist);
            String name = user.getName();
            DatabaseUser.addUser(name);
            DatabaseManager.commit();
            userList.add(name);
            created = true;
            System.out.println("Created");
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
