package main;

import dataHandler.SQLiteDB;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by jto59 on 20/09/17.
 */
public class FavouriteRouteData {

    static String tableName = "favourite_routes";
    static String[] fields = {"name        = VARCHAR(12)" +
                              "start_year  = INTEGER" +
                              "start_month = INTEGER" +
                              "start_day   = INTEGER" +
                              "start_time  = VARCHAR(19)" +
                              "bikeid      = VARCHAR(20)"};

    static String primaryKey = "name";

    static String addRouteString = "insert or fail into users values(?,?,?,?,?,?)";
    static PreparedStatement addRouteStatment = null;
    private static int route_count;
    static SQLiteDB db;

    public static void init() {
        db = Main.getDB();
        db.addTable(tableName, fields, primaryKey);
        addRouteStatment = db.getPreparedStatement(addRouteString);
    }


    public boolean addEntry(String name, int start_year, int start_month, int start_day,
                         String start_time, String bike_id) {
        try {
            addRouteStatment.setObject(1, name);
            addRouteStatment.setObject(2, start_year);
            addRouteStatment.setObject(3, start_month);
            addRouteStatment.setObject(4, start_day);
            addRouteStatment.setObject(5, start_time);
            addRouteStatment.setObject(6, bike_id);
            addRouteStatment.executeUpdate();
            return true;

        } catch (SQLException e) {
            addRouteStatment = db.getPreparedStatement(addRouteString);
            System.out.println(e.getMessage());
            return false;
        }
    }
}
