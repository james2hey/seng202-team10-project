package main;

import javax.swing.plaf.nimbus.State;
import java.io.File;
import java.sql.*;


/**
 * Created by jes143 on 28/08/17.
 */
public class DatabaseManager {

    static String sql_trips = "CREATE TABLE IF NOT EXISTS route_information(" +
            "   tripduration            INTEGER" +
            "  ,starttime               VARCHAR(19)" +
            "  ,stoptime                VARCHAR(19)" +
            "  ,start_station_id        INTEGER" +
            "  ,start_station_name      VARCHAR(21)" +
            "  ,start_latitude          NUMERIC(9,6) NOT NULL" +
            "  ,start_longitude         NUMERIC(9,6) NOT NULL" +
            "  ,end_station_id          INTEGER" +
            "  ,end_station_name        VARCHAR(21)" +
            "  ,end_latitude            NUMERIC(9,6) NOT NULL" +
            "  ,end_longitude           NUMERIC(9,6) NOT NULL" +
            "  ,bikeid                  INTEGER" +
            "  ,usertype                VARCHAR(10)" +
            "  ,birth_year              INTEGER" +
            "  ,gender                  INTEGER)";

    static String sql_retailers = "CREATE TABLE IF NOT EXISTS retailer(" +
            "   RETAILER_NAME      VARCHAR(50) NOT NULL" +
            "  ,ADDRESS            VARCHAR(50)" +
            "  ,LAT                NUMERIC(9,6) NOT NULL" +
            "  ,LONG               NUMERIC(9,6) NOT NULL" +
            "  ,CITY               VARCHAR(20)" +
            "  ,STATE              VARCHAR(2)" +
            "  ,ZIP                INTEGER" +
            "  ,Main_Type          VARCHAR(50)" +
            "  ,Secondary_Type     VARCHAR(50)" +
            "  ,PRIMARY KEY(RETAILER_NAME, ADDRESS))";

    static String sql_wifis = "CREATE TABLE IF NOT EXISTS wifi_location(" +
            "  COST       VARCHAR(12)" +
            "  ,PROVIDER   VARCHAR(20)" +
            "  ,ADDRESS    VARCHAR(50)" +
            "  ,LAT        NUMERIC(9,6) NOT NULL" +
            "  ,LON        NUMERIC(9,6) NOT NULL" +
            "  ,REMARKS    VARCHAR(50)" +
            "  ,CITY       VARCHAR(8)" +
            "  ,SSID       VARCHAR(50) NOT NULL" +
            "  ,SUBURB     VARCHAR(20)" +
            "  ,POSTCODE   INTEGER)";

    static Connection conn = null;
    static Statement stmt = null;

    public static void connect() {

        String home = System.getProperty("user.home");
        java.nio.file.Path path = java.nio.file.Paths.get(home, "database.db");
        String url = "jdbc:sqlite:" + path;

        try {
            conn = DriverManager.getConnection(url);

            stmt = conn.createStatement();
            stmt.executeUpdate(sql_trips);
            stmt.executeUpdate(sql_retailers);
            stmt.executeUpdate(sql_wifis);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void printTables() {
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM RETAILER;");

            while (rs.next()) {
                System.out.println("RETAILER = " + rs.getString("RETAILER_NAME"));
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addItem(String sql) {
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    static String sql_retailers = "CREATE TABLE IF NOT EXISTS retailer(" +
            "   RETAILER_NAME      VARCHAR(50) NOT NULL" +
            "  ,ADDRESS            VARCHAR(50)" +
            "  ,LAT                NUMERIC(9,6) NOT NULL" +
            "  ,LONG               NUMERIC(9,6) NOT NULL" +
            "  ,CITY               VARCHAR(20)" +
            "  ,STATE              VARCHAR(2)" +
            "  ,ZIP                INTEGER" +
            "  ,Main_Type          VARCHAR(50)" +
            "  ,Secondary_Type     VARCHAR(50)" +
            "  ,PRIMARY KEY(RETAILER_NAME, ADDRESS))";
    public static void addRetailer(String RETAILER_NAME, String ADDRESS , double LAT, double LONG, String CITY,
                                   String STATE, int ZIP, String MAIN_TYPE, String SECONDARY_TYPE) {


    }

}
