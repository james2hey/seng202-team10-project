package dataManipulation;

import java.sql.*;
import java.util.ArrayList;
////////////////////////////////

import mapElements.Route;
import mapElements.MapKeyLocations;


/**
 * DataFilterer class uses methods that sent requests to the database and return a array of data.
 */
public class DataFilterer {

    private String databaseCommand;
    private ArrayList<Route> routes = new ArrayList<>();
    private int genderInteger =  -1;


    public void generateRouteArray(ResultSet rs) {
        try {
            while (rs.next()) {
                routes.add(new Route(rs.getInt("trip_duration"), rs.getInt("start_time"),
                        rs.getString("stop_time"), rs.getString("start_date"),
                        rs.getString("stop_date"), rs.getString("start_station_name"),
                        rs.getString("end_station_name"), rs.getString("start_station_latitude"),
                        rs.getString("start_station_longitude"),
                        rs.getString("end_station_latitude"),
                        rs.getString("end_station_longitude"), rs.getString("gender"),
                        rs.getString("date_of_birth"), rs.getString("start_station_id"),
                        rs.getString("end_station_id")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public ArrayList<Route> filterer() {
        try(Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(databaseCommand)) {

            //set value using parameter
            pstmt.setInt(1, genderInteger);

            ResultSet rs = pstmt.executeQuery();
            generateRouteArray(rs);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return routes;
    }


    /**
     * filterByGender gets all route records that have the corresponding gender to the method parameter.
     *
     * @param gender gender of data records to be filtered.
     * @return ArrayList<Route>
     */
    public ArrayList<Route> filterByGender(String gender) {

        if (gender == "F") {
            genderInteger = 2;
        } else if (gender == "M") {
            genderInteger = 1;
        }

        databaseCommand = "SELECT trip_duration, start_time, stop_time, start_date, stop_date, start_station_name, " +
                "end_station_name, start_station_latitude, start_station_longitude, end_station_latitude, " +
                "end_station_latitude, gender, date_of_birth, start_station_id, end_station_id FROM route_data WHERE " +
                "gender = ?";


    }


    public static ArrayList<Route> filterByDate(String date) {
        ArrayList<Route> filteredData;
        return filteredData;
    }


    public ArrayList<Route> filterByAge(int age) {


        databaseCommand = "SELECT trip_duration, start_time, stop_time, start_date, stop_date, start_station_name, " +
                "end_station_name, start_station_latitude, start_station_longitude, end_station_latitude, " +
                "end_station_latitude, gender, date_of_birth, start_station_id, end_station_id FROM route_data WHERE " +
                "date_of_birth = ?";

    }


    public static ArrayList<Route> filterByTime(String time) {
        ArrayList<Route> filteredData;
        return filteredData;
    }
}