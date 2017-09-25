package dataHandler;

import main.HandleUsers;
import main.Main;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Handles all of the favourite route data for every user in the database.
 */
public class FavouriteRouteData {

    private SQLiteDB db;
    private String[] fields =
            {"name         VARCHAR(12)",
                    "start_year   VARCHAR(4)",
                    "start_month  VARCHAR(2)",
                    "start_day    VARCHAR(2)",
                    "start_time   VARCHAR(19)",
                    "bikeid       VARCHAR(20)",
                    "rank         INTEGER"};
    private String primaryKey = "name, start_year, start_month, start_day, start_time, bikeid";
    private String tableName = "favourite_routes";
    private  PreparedStatement addRoute;
    private  String addRouteStatement = "insert or fail into favourite_routes values(?,?,?,?,?,?,?)";


    /**
     * Initializes the database when creating an instance of the FavouriteRouteData.
     * @param db database the retail data is added to
     */
    public FavouriteRouteData(SQLiteDB db) {
        this.db = db;
        System.out.println(db.addTable(tableName, fields, primaryKey));
        addRoute = db.getPreparedStatement(addRouteStatement);
    }


    /**
     * Adds the given name, start year, start month, start day, start time, bike ID and rank to the table.
     * @param name name of the user
     * @param start_year year the route started
     * @param start_month month the route started
     * @param start_day day the route started
     * @param start_time time the route started
     * @param bike_id identification number of the bike
     * @param rank rating of the route that the user has chosen
     */
    public void addFavouriteRoute(String name, String start_year, String start_month, String start_day,
                                  String start_time, String bike_id, int rank, HandleUsers hu) {
        try {
            addRoute.setObject(1, name);
            addRoute.setObject(2, start_year);
            addRoute.setObject(3, start_month);
            addRoute.setObject(4, start_day);
            addRoute.setObject(5, start_time);
            addRoute.setObject(6, bike_id);
            addRoute.setObject(7, rank);
            addRoute.executeUpdate();
            db.commit();
            hu.currentCyclist.updateUserRouteFavourites(hu);

        } catch (SQLException e) {
            addRoute = db.getPreparedStatement(addRouteStatement);
            System.out.println(e.getMessage());
        }
    }
}
