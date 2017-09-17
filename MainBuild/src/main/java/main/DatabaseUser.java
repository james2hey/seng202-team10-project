package main;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by jto59 on 17/09/17.
 */
public class DatabaseUser {

    static String sql_users = "CREATE TABLE IF NOT EXISTS users(" +
            "   NAME       VARCHAR(12)" +
            "  ,PRIMARY KEY(NAME))";


    static String addUserString = "insert into users values(?)";
    static PreparedStatement addUser = null;
    static int user_count;

    /**
     * Adds Name to the database.
     * @param NAME
     */
    public static void addUser(String NAME) {
        try {
            DatabaseManager.conn.setAutoCommit(false);
            addUser.setString(1, NAME);
            addUser.executeUpdate();
            DatabaseManager.edits --;

            if (DatabaseManager.edits == 0) {
                DatabaseManager.conn.commit();
            }
            user_count += 1;

        } catch (SQLException e) {
            try {
                DatabaseManager.conn.rollback();
                addUser = DatabaseManager.conn.prepareStatement(addUserString);
                System.out.println(e.getMessage());
            } catch (SQLException e2) {
                System.out.println(e2.getMessage());
            }
        }
    }

}
