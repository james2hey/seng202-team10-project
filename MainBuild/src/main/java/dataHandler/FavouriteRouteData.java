package dataHandler;

import dataHandler.SQLiteDB;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by jto59 on 20/09/17.
 */
public class FavouriteRouteData {

    static SQLiteDB db;

    String[] fields =
            {"name         VARCHAR(12)",
                    "start_year   VARCHAR(4)",
                    "start_month  VARCHAR(2)",
                    "start_day    VARCHAR(2)",
                    "start_time   VARCHAR(19)",
                    "bikeid       VARCHAR(20)"};


    String primaryKey = "name, start_year, start_month, start_day, start_time, bikeid";
    String tableName = "favourite_routes";

    static PreparedStatement addRoute;
    static String addRouteStatement = "insert or fail into favourite_routes values(?,?,?,?,?,?)";



    public FavouriteRouteData(SQLiteDB db) {
        this.db = db;
        db.addTable(tableName, fields, primaryKey);
        addRoute = db.getPreparedStatement(addRouteStatement);
    }



    public static void addFavouriteRoute(String name, String start_year, String start_month, String start_day,
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
