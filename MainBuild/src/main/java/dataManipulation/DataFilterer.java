package dataManipulation;

import java.sql.*;
import java.util.ArrayList;
import java.time.Year;
////////////////////////////////

import dataAnalysis.Route;
import dataAnalysis.MapKeyLocations;


/**
 * DataFilterer class uses methods that sent requests to the database and return a array of data.
 */
public class DataFilterer {

    private String databaseCommand;
    private ArrayList<Route> routes;


    /**
     * Constructor for DataFilterer class.
     */
    public DataFilterer() {
        databaseCommand = "";
        routes = new ArrayList<>();
    }


    /**
     * connect class establishes a connection to the database. Used by all methods that request a query from the
     * database.
     *
     * @return Connection
     */
    private Connection connect() {

        String url = "";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * GenerateRouteArray takes a result set and creates a Route from each result and adds them to a ArrayList.
     *
     * @param rs rs is a result set of data records from a query to the database.
     */
    private void generateRouteArray(ResultSet rs) {
        try {
            while (rs.next()) {
                routes.add(new Route(rs.getInt("trip_duration"), rs.getInt("start_time"),
                        rs.getInt("stop_time"), rs.getInt("start_date"),
                        rs.getInt("stop_date"), rs.getDouble("start_station_latitude"),
                        rs.getDouble("start_station_longitude"),
                        rs.getDouble("end_station_latitude"),
                        rs.getDouble("end_station_longitude"), rs.getInt("start_station_id"),
                        rs.getInt("end_station_id")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * filterByGender takes a string that specifies the gender to filter records by and gets all route records that have
     * the corresponding gender to the method parameter.
     *
     * @param gender gender of type String. Specifies the gender to filter records by.
     */
    public void filterByGender(String gender) {

        int genderInteger = 0;

        if ("F".equals(gender)) {
            genderInteger = 2;
        } else if ("M".equals(gender)) {
            genderInteger = 1;
        }

        databaseCommand = "SELECT trip_duration, start_time, stop_time, start_date, stop_date, start_station_name, " +
                "end_station_name, start_station_latitude, start_station_longitude, end_station_latitude, " +
                "end_station_latitude, gender, date_of_birth, start_station_id, end_station_id FROM route_data WHERE " +
                "gender = ?";

        try(Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(databaseCommand)) {

            //set value using parameter
            pstmt.setInt(1, genderInteger);

            ResultSet rs = pstmt.executeQuery();
            generateRouteArray(rs);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * filterByDate take an upper date and lower date and query's the database to find all records that were started
     * between those dates.
     *
     * @param upperDate upperDate is of type Date from the sql package. It is the upper limit that a route was
     *                  started on, specified by the user.
     * @param lowerDate lowerDate is of type Date from the sql package. It is the lower limit that a route was
     *                  started on, specified by the user.
     */
    public void filterByDate(Date upperDate, Date lowerDate) {

        databaseCommand = "SELECT trip_duration, start_time, stop_time, start_date, stop_date, start_station_name, " +
                "end_station_name, start_station_latitude, start_station_longitude, end_station_latitude, " +
                "end_station_latitude, gender, date_of_birth, start_station_id, end_station_id FROM route_data WHERE " +
                "start_date BETWEEN ? AND ?";

        try(Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(databaseCommand)) {

            //set value using parameter
            pstmt.setDate(1, lowerDate);
            pstmt.setDate(2, upperDate);

            ResultSet rs = pstmt.executeQuery();
            generateRouteArray(rs);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * filterByAge takes an upper age limit and lower age limit and query's the database to find all records that were
     * completed between those two ages.
     *
     * @param upperAge upperAge is of type int. It is the upper age limit of the person that completed a route, that the
     *                 user wants to filter by.
     * @param lowerAge lowerAge is of type int. It is the lower age limit of the person that completed a route, that the
     *                 user wants to filter by.
     */
    public void filterByAge(int upperAge, int lowerAge) {

        int year = Year.now().getValue();
        int LowerRequiredYear = year - upperAge;
        int UpperRequiredYear = year - lowerAge;

        databaseCommand = "SELECT trip_duration, start_time, stop_time, start_date, stop_date, start_station_name, " +
                "end_station_name, start_station_latitude, start_station_longitude, end_station_latitude, " +
                "end_station_latitude, gender, date_of_birth, start_station_id, end_station_id FROM route_data WHERE " +
                "date_of_birth BETWEEN ? AND ?";

        try(Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(databaseCommand)) {

            //set value using parameter
            pstmt.setInt(1, LowerRequiredYear);
            pstmt.setInt(2, UpperRequiredYear);

            ResultSet rs = pstmt.executeQuery();
            generateRouteArray(rs);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * filterByTime takes an upper time limit and lower time limit and query's the database to find all records that
     * were started between those times.
     *
     * @param upperTime upperTime is of type Time from the sql package. It is the upper time limit of starting a route
     *                  the user wants to filter by.
     * @param lowerTime lowerTime is of type Time from the sql package. It is the lower time limit of starting a route
     *                  the user wants to filter by.
     */
    public void filterByTime(Time upperTime, Time lowerTime) {

        databaseCommand = "SELECT trip_duration, start_time, stop_time, start_date, stop_date, start_station_name, " +
                "end_station_name, start_station_latitude, start_station_longitude, end_station_latitude, " +
                "end_station_latitude, gender, date_of_birth, start_station_id, end_station_id FROM route_data WHERE " +
                "start_time BETWEEN ? AND ?";

        try(Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(databaseCommand)) {

            //set value using parameter
            pstmt.setTime(1, lowerTime);
            pstmt.setTime(2, upperTime);

            ResultSet rs = pstmt.executeQuery();
            generateRouteArray(rs);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}