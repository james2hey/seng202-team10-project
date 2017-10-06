package dataHandler;

import main.Main;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * ListData creates the lists table in the database and adds list details to the table.
 */
public class ListData {

    private static String listName;
    private SQLiteDB db;
    private String tableName = "lists";
    private String[] tableFields = {"list_name    VARCHAR(50)",
                                    "list_owner   VARCHAR(12)"};
    private String primaryKey = "list_name";
    private String addListCommand = "insert or fail into lists values(?,?)";


    /**
     * Constructor for ListData, creates new table in database.
     *
     * @param db the database connection.
     */
    public ListData(SQLiteDB db) {
        this.db = db;
        db.addTable(tableName, tableFields, primaryKey);
    }


    /**
     * Constructor for ListData, creates new table in database and adds a list to the table from the given listName and
     * the current user.
     *
     * @param db the database connection.
     */
    public ListData(SQLiteDB db, String listName) {
        this.db = db;
        db.addTable(tableName, tableFields, primaryKey);
        addList(listName);
    }


    /**
     * gets the listName variable.
     *
     * @return listName of type String
     */
    public static String getListName() {
        return listName;
    }


    /**
     * sets the listName variable to the given name.
     *
     * @param name type String
     */
    public static void setListName(String name) {
        listName = name;
        System.out.println(getListName());
    }


    /**
     * adds a new list to the table from the given listName and the current user.
     *
     * @param listName type String. The name of the new list to be added to the database.
     */
    public void addList(String listName) {
        try {
            String user = Main.hu.currentCyclist.getName();
            PreparedStatement pstmt = db.getPreparedStatement(addListCommand);
            pstmt.setString(1, listName);
            pstmt.setString(2, user);
            pstmt.executeUpdate();
            db.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
