package main;

import dataHandler.SQLiteDB;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by jto59 on 20/09/17.
 */
public class FavouriteRouteData {

    String tableName = "favourite_routes";
    String[] fields = {"name        = VARCHAR(12)" +
                              "start_year  = INTEGER" +
                              "start_month = INTEGER" +
                              "start_day   = INTEGER" +
                              "start_time  = VARCHAR(19)" +
                              "bikeid      = VARCHAR(20)"};

    String primaryKey = "name";

    PreparedStatement addRoute;
    String addRouteStatement = "insert or fail into favourite_routes values(?,?,?,?,?,?)";
    private int route_count;
    SQLiteDB db;


    public FavouriteRouteData(SQLiteDB db) {
        this.db = db;
        db.addTable(tableName, fields, primaryKey);
        addRoute = db.getPreparedStatement(addRouteStatement);
    }



    public void addFavouriteRoute(String name, int start_year, int start_month, int start_day,
                         String start_time, String bike_id) {
        try {
            addRoute.setObject(1, name);
            addRoute.setObject(2, start_year);
            addRoute.setObject(3, start_month);
            addRoute.setObject(4, start_day);
            addRoute.setObject(5, start_time);
            addRoute.setObject(6, bike_id);
            addRoute.executeUpdate();
            db.commit();

        } catch (SQLException e) {
            addRoute = db.getPreparedStatement(addRouteStatement);
            System.out.println(e.getMessage());
        }
    }
}
