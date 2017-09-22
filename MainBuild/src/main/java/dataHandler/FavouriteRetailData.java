package dataHandler;

import dataHandler.SQLiteDB;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by jto59 on 21/09/17.
 */
public class FavouriteRetailData {

    static SQLiteDB db;

    String[] fields =
            {"name         VARCHAR(12)",
                    "RETAILER_NAME    VARCHAR(50) NOT NULL",
                    "ADDRESS            VARCHAR(50)"};


    String primaryKey = "name, RETAILER_NAME, ADDRESS";
    String tableName = "favourite_retail";

    static PreparedStatement addRetail;
    static String addRetailStatement = "insert or fail into favourite_retail values(?,?,?)";


    public FavouriteRetailData(SQLiteDB db) {
        this.db = db;
        db.addTable(tableName, fields, primaryKey);
        addRetail = db.getPreparedStatement(addRetailStatement);
    }


    public static void addFavouriteRetail(String name, String retail_name, String address) {
        try {
            addRetail.setObject(1, name);
            addRetail.setObject(2, retail_name);
            addRetail.setObject(3, address);
            addRetail.executeUpdate();
            db.commit();

        } catch (SQLException e) {
            addRetail = db.getPreparedStatement(addRetailStatement);
            System.out.println(e.getMessage());
        }
    }
}
