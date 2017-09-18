package main;

import dataHandler.SQLiteDB;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by jto59 on 17/09/17.
 */
public class DatabaseUser {

    static String tableName = "users";
    static String[] fields = {"NAME VARCHAR(12)"};
    static String primaryKey = "NAME";


    static String addUserString = "insert or fail into users values(?)";
    static PreparedStatement addUser = null;
    static int user_count;
    static SQLiteDB db;

    /**
     * Adds Name to the database.
     * @param NAME
     */
    public static void addUser(String NAME) {
        try {
            addUser.setString(1, NAME);
            addUser.executeUpdate();
            db.commit();

        } catch (SQLException e) {
                addUser = db.getPreparedStatement(addUserString);
                System.out.println(e.getMessage());
        }
    }

    public static void init() {
        db = Main.getDB();
        db.addTable(tableName, fields, primaryKey);
        addUser = db.getPreparedStatement(addUserString);
    }

}
