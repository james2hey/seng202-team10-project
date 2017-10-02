package dataHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Handles all of the user data, ensuring that each username is unique when adding names.
 */
public class DatabaseUser {

    private String tableName = "users";
    private String[] fields = {"name VARCHAR(12)",
            "birth_day               INTEGER",
            "birth_month             INTEGER",
            "birth_year              INTEGER",
            "gender                  INTEGER",
            "distance_cycled         INTEGER"};

    private String primaryKey = "name";
    private String addUserString = "insert or fail into users values(?,?,?,?,?,?)";
    private PreparedStatement addUser = null;
    private SQLiteDB db;


    /**
     * Initializes the database when creating an instance of the DatabaseUser.
     *
     * @param db database the user data is added to
     */
    public DatabaseUser(SQLiteDB db) {
        this.db = db;
        db.addTable(tableName, fields, primaryKey);
        addUser = db.getPreparedStatement(addUserString);
    }


    /**
     * Adds Name to the database.
     *
     * @param name name of the user to add
     */
    public void addUser(String name, int day, int month, int year, int gender, int distance) {
        try {
            addUser.setString(1, name);
            addUser.setInt(2, day);
            addUser.setInt(3, month);
            addUser.setInt(4, year);
            addUser.setInt(5, gender);
            addUser.setInt(6, distance);
            addUser.executeUpdate();
            db.commit();

        } catch (SQLException e) {
            addUser = db.getPreparedStatement(addUserString);
            System.out.println(e.getMessage());
        }
    }
}
