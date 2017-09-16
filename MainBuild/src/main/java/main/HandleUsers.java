package main;

import java.util.Scanner;
import java.util.ArrayList;

/**
 * Created by jto59 on 16/09/17.
 * Deals with users on the start up screen to choose which user is to be logged in or created.
 */
public class HandleUsers {

    /**
     * Logs into the user whose parameter is handed into the function.
     * @param user
     */
    public static void logIn(User user) {
        //
    }

    /**
     * Logs out of the currently logged in user.
     */
    public static void logOut() {
        //Restart to main screen!!!!!!!!!
    }

    /**
     * Creates a new User and adds them to the users list.
     * @param isCyclist;
     */
    public static void createNewUser(boolean isCyclist) {
        System.out.print("Enter Username: ");
        String username = "Hello";
        User user;
        if(isCyclist) {
            user = new Cyclist(username);
        } else {
            user = new Analyst(username);
        }

        user.getName();
        DatabaseManager.addUserString = "insert into users values(username)";

    }


//        Scanner s = new Scanner(System.in);
//        String name = s.next();
//        User user;
//        if(isCyclist) {
//            user = new Cyclist(name);
//        } else {
//            user = new Analyst(name);
//        }
//        userList.add(user);
//    }

}
