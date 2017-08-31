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
            "  ,ZIP                VARCHAR(8)" +
            "  ,Main_Type          VARCHAR(50)" +
            "  ,Secondary_Type     VARCHAR(50)" +
            "  ,PRIMARY KEY(RETAILER_NAME, ADDRESS))";

    static String sql_wifis = "CREATE TABLE IF NOT EXISTS wifi_location(" +
            "   COST       VARCHAR(12)" +
            "  ,PROVIDER   VARCHAR(20)" +
            "  ,ADDRESS    VARCHAR(50)" +
            "  ,LAT        NUMERIC(9,6) NOT NULL" +
            "  ,LON        NUMERIC(9,6) NOT NULL" +
            "  ,REMARKS    VARCHAR(50)" +
            "  ,CITY       VARCHAR(8)" +
            "  ,SSID       VARCHAR(50) NOT NULL" +
            "  ,SUBURB     VARCHAR(20)" +
            "  ,ZIP        VARCHAR(8))";

    static String addRetailerString = "insert into retailer values(?,?,?,?,?,?,?,?,?)";
    static String addWifiString = "insert into wifi_location values(?,?,?,?,?,?,?,?,?,?)";
    static String addTripString = "insert into route_information values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    static Connection conn = null;
    static Statement stmt = null;
    static PreparedStatement addRetailer = null;
    static PreparedStatement addWifi = null;
    static PreparedStatement addTrip = null;
    static int edits = 1000;

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
            addRetailer = conn.prepareStatement(addRetailerString);
            addWifi = conn.prepareStatement(addWifiString);
            addTrip = conn.prepareStatement(addTripString);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void printTables() {
        try {
            int RE_C = 0;
            int WI_C = 0;
            int TR_C = 0;
            ResultSet rs;
            rs = stmt.executeQuery("SELECT * FROM RETAILER;");

            while (rs.next()) {
                System.out.println("RETAILER = " + rs.getString("RETAILER_NAME"));
                RE_C ++;
            }

            rs = stmt.executeQuery("SELECT * FROM wifi_location;");

            while (rs.next()) {
                System.out.println("WIFI = " + rs.getString("PROVIDER") + rs.getString("ADDRESS"));
                WI_C ++;
            }

            rs = stmt.executeQuery("SELECT * FROM route_information;");

            while (rs.next()) {
                System.out.println("TRIP = " + rs.getString("STARTTIME") + rs.getString("start_station_id"));
                TR_C ++;
            }
            System.out.println(RE_C);
            System.out.println(WI_C);
            System.out.println(TR_C);
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

    public static void addRetailer(String RETAILER_NAME, String ADDRESS, double LAT, double LONG, String CITY,
                                   String STATE, String ZIP, String MAIN_TYPE, String SECONDARY_TYPE) {
        try {
            conn.setAutoCommit(false);
            addRetailer.setString(1, RETAILER_NAME);
            addRetailer.setString(2, ADDRESS);
            addRetailer.setDouble(3, LAT);
            addRetailer.setDouble(4, LONG);
            addRetailer.setString(5, CITY);
            addRetailer.setString(6, STATE);
            addRetailer.setString(7, ZIP);
            addRetailer.setString(8, MAIN_TYPE);
            addRetailer.setString(9, SECONDARY_TYPE);
            addRetailer.executeUpdate();
            edits --;

            if (edits == 0) {
                conn.commit();
            }


        } catch (SQLException e) {
            try {
                conn.rollback();
                addRetailer = conn.prepareStatement(addRetailerString);
                System.out.println(e.getMessage());
            } catch (SQLException e2) {
                System.out.println(e2.getMessage());
            }
        }
    }

    public static void addWifi(String COST, String PROVIDER, String ADDRESS, double LAT, double LONG, String REMARKS, String CITY,
                               String SSID, String SUBURB, String ZIP) {
        try {
            conn.setAutoCommit(false);
            addWifi.setString(1, COST);
            addWifi.setString(2, PROVIDER);
            addWifi.setString(3, ADDRESS);
            addWifi.setDouble(4, LAT);
            addWifi.setDouble(5, LONG);
            addWifi.setString(6, REMARKS);
            addWifi.setString(7, CITY);
            addWifi.setString(8, SSID);
            addWifi.setString(9, SUBURB);
            addWifi.setString(10, ZIP);
            addWifi.executeUpdate();
            edits --;

            if (edits == 0) {
                conn.commit();
            }

        } catch (SQLException e) {
            try {
                conn.rollback();
                addWifi = conn.prepareStatement(addWifiString);
                System.out.println(e.getMessage());
            } catch (SQLException e2) {
                System.out.println(e2.getMessage());
            }
        }
    }

    public static void addTrip(int tripduration, String starttime, String stoptime, String start_station_id,
                               String start_station_name, double start_latitude, double start_longitude,
                               String end_station_id, String end_station_name, double end_latitude,
                               double end_longitude, String bikeid, String usertype, int birth_year, int gender) {
        try {
            conn.setAutoCommit(false);
            addTrip.setInt(1, tripduration);
            addTrip.setString(2, starttime);
            addTrip.setString(3, stoptime);
            addTrip.setString(4, start_station_id);
            addTrip.setString(5, start_station_name);
            addTrip.setDouble(6,start_latitude);
            addTrip.setDouble(7, start_longitude);
            addTrip.setString(8, end_station_id);
            addTrip.setString(9, end_station_name);
            addTrip.setDouble(10, end_latitude);
            addTrip.setDouble(11, end_longitude);
            addTrip.setString(12, bikeid);
            addTrip.setString(13, usertype);
            addTrip.setInt(14, birth_year);
            if (birth_year == -1) {
                addTrip.setNull(14, Types.INTEGER);
            }
            addTrip.setInt(15, gender);
            addTrip.executeUpdate();
            edits --;

            if (edits == 0) {
                conn.commit();
            }

        } catch (SQLException e) {
            try {
                conn.rollback();
                addTrip = conn.prepareStatement(addTripString);
                System.out.println(e.getMessage());
            } catch (SQLException e2) {
                System.out.println(e2.getMessage());
            }
        }
    }
    public static void commit() {
        try {
            conn.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}