package dataHandler;

import java.sql.*;

/**
 * Created by jes143 on 17/09/17.
 */
public class SQLiteDB {

    private Connection conn;
    private Statement stmt;

    private String addTable = "CREATE TABLE %s(%s, PRIMARY KEY(%s))";


    /**
     * Creates a database object in the users default directory called "database.db"
     */
    public SQLiteDB() {

        String home = System.getProperty("user.home");
        java.nio.file.Path path = java.nio.file.Paths.get(home, "database.db");
        String url = "jdbc:sqlite:" + path;
        init(url);
    }

    /**
     * Create a database object with a specified file location.
     * @param location A url string for the file location.
     */
    public SQLiteDB(String location) {
        init("jdbc:sqlite:" + location);
    }

    /**
     * Initializes a database connection from a specified url. Also initializes a default statement
     * @param url A string specifying the file location
     */
    private void init(String url) {
        try {
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds a new table if it does not already exist. Vulnerable to SQL injection so do not expose the fields to users without sanitization.
     * @param tableName Name of the table
     * @param fields A string list of columns and their properties
     * @param primaryKey A value or values for the primary key, can be comma separated
     */
    public boolean addTable(String tableName, String[] fields, String primaryKey) {
        try {
            String fieldsText = "";
            fieldsText += fields[0];
            for (int i = 1; i < fields.length; i++) {
                fieldsText += ", " + fields[i];
            }
            String f = String.format(addTable, tableName, fieldsText, primaryKey);
            stmt.execute(f);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Executes SQL update on the database. Vulnerable to SQL injection so do not expose the fields to users without sanitization.
     * @param sql The statement to be executed
     * @return int The sql executeUpdate outcome or -1 if failed
     */
    public int executeUpdateSQL(String sql) {
        try {
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    /**
     * Executes SQL query on the database, returns ResultSet. Vulnerable to SQL injection so do not expose the fields to users without sanitization.
     * @param sql The statement to be executed
     * @return The ResultSet object returned, or null if query failed.
     */
    public ResultSet executeQuerySQL(String sql) {
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Generate a PreparedStatement object to use. Returns null on invalid data.
     * @param statement A valid SQL prepared statement.
     * @return Returns a PreparedStatement object, or null of statement failed. The calling class is now responsible for SQLExecptions, although the code will compile even if they are not handled.
     */

    public PreparedStatement getPreparedStatement(String statement) {
        try {
            return conn.prepareStatement(statement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Change the database auto commit state, useful for batching commits.
     * @param state Sets the auto commit state.
     */
    public void setAutoCommit(Boolean state) {
        try {
            conn.setAutoCommit(state);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Simple commit execution on the database to aid with batching
     */
    public void commit() {
        try {
            conn.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
