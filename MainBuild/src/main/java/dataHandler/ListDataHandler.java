package dataHandler;

import main.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * ListDataHandler creates the lists table in the database and adds list details to the table.
 */
public class ListDataHandler {

    private static String listName;
    private SQLiteDB db;
    private String tableName = "lists";
    private String[] tableFields = {"list_name    VARCHAR(50)",
                                    "list_owner   VARCHAR(12)"};
    private String primaryKey = "list_name";
    private String addListCommand = "insert or fail into lists values(?,?)";


    /**
     * Constructor for ListDataHandler, creates new table in database.
     *
     * @param db the database connection.
     */
    public ListDataHandler(SQLiteDB db) {
        this.db = db;
        db.addTable(tableName, tableFields, primaryKey);
    }


    /**
     * Constructor for ListDataHandler, creates new table in database and adds a list to the table from the given listName and
     * the current user.
     *
     * @param db the database connection.
     */
    public ListDataHandler(SQLiteDB db, String listName) {
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
     * Creates a ArrayList of all the lists that the user had created.
     *
     * @return lists of type ArrayList
     */
    public ArrayList getLists() {
        ArrayList<String> lists = new ArrayList<>();
        String userName = Main.hu.currentCyclist.getName();
        try {
            ResultSet rs = db.executeQuerySQL("SELECT list_name FROM lists WHERE list_owner = '" + userName + "';");
            while (rs.next()) {
                lists.add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lists;
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
