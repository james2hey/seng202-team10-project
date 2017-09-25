package dataManipulation;

import dataAnalysis.RetailLocation;
import dataAnalysis.Route;
import dataAnalysis.WifiLocation;
import dataHandler.SQLiteDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

////////////////////////////////


/**
 * DataFilterer class uses methods that sent requests to the database and return a array of data.
 */
public class DataFilterer {

    //---Route Strings---
    private String routeCommand;
    private String genderCommand;
    private String timeCommand;
    private String dateCommand;
    private String startAddressCommand;
    private String endAddressCommand;
    private String getAllRoutesCommand;


    //---Wifi Strings---
    private String wifiCommand;
    private String wifiNameCommand;
    private String boroughCommand;
    private String typeCommand;
    private String providerCommand;
    private String getAllWifiCommand;

    //---Retailer Commands---
    private String retailerCommand;
    private String retailerNameCommand;
    private String streetCommand;
    private String zipCommand;
    private String primaryCommand;
    private String getAllRetailersCommand;

    //---Other---
    private String andCommand;
    private String commandEnd;

    private ArrayList<Route> routes;
    private ArrayList<Integer> filterVariables;
    private ArrayList<String> filterVariableStrings;

    private ArrayList<WifiLocation> wifiLocations;
    private ArrayList<RetailLocation> retailLocations;

    private SQLiteDB db;


    /**
     * Constructor for DataFilterer class.
     */
    public DataFilterer(SQLiteDB db) {
        //---Route Strings---
        routeCommand = "SELECT * FROM route_information WHERE ";
        genderCommand = "gender = ?";
        dateCommand = "start_year || start_month || start_day BETWEEN ? AND ?";
        timeCommand = "start_time BETWEEN ? AND ?";
        startAddressCommand = "start_station_name LIKE ?";
        endAddressCommand = "end_station_name LIKE ?";
        getAllRoutesCommand = "SELECT * FROM route_information;";
        //---Wifi Strings---
        wifiCommand = "SELECT * FROM wifi_location WHERE ";
        wifiNameCommand = "SSID LIKE ?";
        boroughCommand = "suburb LIKE ?";
        typeCommand = "cost LIKE ?";
        providerCommand = "provider LIKE ?";
        getAllWifiCommand = "SELECT * FROM wifi_location;";
        //---Retailer Strings---
        retailerCommand = "SELECT * FROM retailer WHERE ";
        retailerNameCommand = "retailer_name LIKE ?";
        streetCommand = "address LIKE ?";
        zipCommand = "zip = ?";
        primaryCommand = "main_type LIKE ?";
        getAllRetailersCommand = "SELECT * FROM retailer;";

        //---Other---
        andCommand = " AND ";
        commandEnd = ";";
        routes = new ArrayList<>();
        filterVariables = new ArrayList<>();
        filterVariableStrings = new ArrayList<>();
        wifiLocations = new ArrayList<>();
        retailLocations = new ArrayList<>();
        this.db = db;
    }


    /**
     * addAndToStmt checks if the query statement has at least one other filter requirement included. If it does the
     * andCommand string is appended to the end of the query command. Takes a string, queryCommand, and a int,
     * queryLength, as parameters. Returns the query command.
     *
     * @param queryCommand of type String. This is the string that will be used as the query statement to
     *                     the database
     * @param queryLength  of type int. This is the number of filter requirements that have been added to the
     *                     queryCommand already
     * @return queryCommand, of type String. This is the string that will be used as a query statement to the database
     */
    private String addAndToStmt(String queryCommand, int queryLength) {
        if (queryLength > 0) {
            queryCommand = queryCommand + andCommand;
        }
        return queryCommand;
    }


///////////////////////////////---ROUTE FILTERING---\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    /**
     * GenerateRouteArray takes a result set (set of records received from a database query) and creates a Route from
     * each result and adds them to an ArrayList.
     *
     * @param rs is a result set of data records from a query to the database
     */
    private void generateRouteArray(ResultSet rs) {
        try {
            while (rs.next()) {
                routes.add(new Route(rs.getInt("tripduration"), rs.getString("start_time"),
                        rs.getString("end_time"), rs.getString("start_day"),
                        rs.getString("start_month"), rs.getString("start_year"),
                        rs.getString("end_day"), rs.getString("end_month"),
                        rs.getString("end_year"), rs.getDouble("start_latitude"),
                        rs.getDouble("start_longitude"), rs.getDouble("end_latitude"),
                        rs.getDouble("end_longitude"), rs.getInt("start_station_id"),
                        rs.getInt("end_station_id"), rs.getString("start_station_name"),
                        rs.getString("end_station_name"), rs.getString("bikeid"),
                        rs.getInt("gender"), rs.getString("usertype"),
                        rs.getInt("birth_year")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * convertDates takes an upper and lower bound of dates and converts them into a single number. The date format
     * must be DD/MM/YYYY. The date will be converted into the format: YYYYMMDD. This allows the easy querying of dates
     * in the database.
     *
     * Ex. date: 21/01/2016 will be converted into 20160121
     *
     * @param dateLower of type String
     * @param dateUpper of type String
     */
    private void convertDates(String dateLower, String dateUpper) {
        filterVariableStrings.add(dateLower.substring(6) + dateLower.substring(3, 5) + dateLower.substring(0, 2));
        filterVariableStrings.add(dateUpper.substring(6) + dateUpper.substring(3, 5) + dateUpper.substring(0, 2));
    }


    /**
     * generateQueryString takes all the possible filter requirement values and appends the necessary strings onto the
     * end of the database query statement. A value of -1 (int) or null (string) means the parameter has not been set
     * by the user, and this will not be used in the query.
     *
     * @param gender        of type int. A value of -1 means not to filter by gender, 1 means filter by males and 2
     *                      means filter by females
     * @param dateLower     of type String. It is the lower limit that a route was started on, specified by
     *                      the user
     * @param dateUpper     dof type String. It is the upper limit that a route was started on, specified by
     *                      the user
     * @param timeLower     tof type String. It is the lower time limit of starting a route the user wants to
     *                      filter by
     * @param timeUpper     of type String. It is the upper time limit of starting a route the user wants to
     *                      filter by
     * @param startLocation of type String. It is the starting address of a route that the user wants
     *                      to filter by
     * @param endLocation   of type String. It is the ending address of a route that the user wants
     *                      to filter by
     * @return queryCommand, of type String. This is the string that will be used as a query statement to the database
     */
    private String generateQueryString(int gender, String dateLower, String dateUpper, String timeLower,
                                       String timeUpper, String startLocation, String endLocation) {
        String queryCommand = routeCommand;
        int queryLength = 0;

        if (gender != -1) {
            queryCommand = queryCommand + genderCommand;
            filterVariables.add(gender);
            queryLength = 1;
        }
        if (dateLower != null && dateUpper != null) {
            convertDates(dateLower, dateUpper);
            queryCommand = addAndToStmt(queryCommand, queryLength);
            queryCommand = queryCommand + dateCommand;
            queryLength = 1;
        }
        if (timeLower != null && timeUpper != null) {
            queryCommand = addAndToStmt(queryCommand, queryLength);
            queryCommand = queryCommand + timeCommand;
            filterVariableStrings.add(timeLower);
            filterVariableStrings.add(timeUpper);
            queryLength = 1;
        }
        if (startLocation != null) {
            queryCommand = addAndToStmt(queryCommand, queryLength);
            queryCommand = queryCommand + startAddressCommand;
            filterVariableStrings.add("%" + startLocation + "%");
            queryLength = 1;
        }
        if (endLocation != null) {
            queryCommand = addAndToStmt(queryCommand, queryLength);
            queryCommand = queryCommand + endAddressCommand;
            filterVariableStrings.add("%" + endLocation + "%");
            queryLength = 1;
        }
        if (queryLength > 0) {
            queryCommand = queryCommand + commandEnd;
        }
        return queryCommand;
    }

    /**
     * setQueryParameters takes a PreparedStatement as a parameter and uses the values in class ArrayList variables,
     * filterVariables and filterVariableStrings, to set the parameters of the PreparedStatement.
     *
     * @param pstmt of type PreparedStatement. This is the query statement to be called to the database
     * @return PreparedStatement, the updated PreparedStatement, now with its parameters set to the
     * correct values
     */
    private PreparedStatement setQueryParameters(PreparedStatement pstmt) {
        try {
            int i;
            for (i = 0; i < filterVariables.size(); i++) {
                pstmt.setInt(i + 1, filterVariables.get(i));
            }

            for (int j = 0; j < filterVariableStrings.size(); j++) {
                pstmt.setString(i + 1, filterVariableStrings.get(j));
                i++;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return pstmt;
    }


    /**
     * getAllRoutes gets all routes from the database and returns these as Route objects in an ArrayList.
     */
    private void getAllRoutes() {
        try {
            String queryString = getAllRoutesCommand;
            PreparedStatement pstmt;
            pstmt = db.getPreparedStatement(queryString);
            setQueryParameters(pstmt);

            ResultSet rs = pstmt.executeQuery();
            generateRouteArray(rs);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * filterRoutes takes all the possible filter values for routes and returns a ArrayList of Routes that meet the
     * filter requirements.
     *
     * @param gender        of type int. A value of -1 means not to filter by gender, 1 means filter by males and 2
     *                      means filter by females
     * @param dateLower     of type String. It is the lower limit that a route was started on, specified by
     *                      the user
     * @param dateUpper     of type String. It is the upper limit that a route was started on, specified by
     *                      the user
     * @param timeLower     of type String. It is the lower time limit of starting a route the user wants to
     *                      filter by
     * @param timeUpper     of type String. It is the upper time limit of starting a route the user wants to
     *                      filter by
     * @param startLocation of type String. It is the starting address of a route that the user wants
     *                      to filter by
     * @param endLocation   of type String. It is the ending address of a route that the user wants
     *                      to filter by
     * @return ArrayList<Route>, this is an ArrayList that contains all filtered routes
     */
    public ArrayList<Route> filterRoutes(int gender, String dateLower, String dateUpper,
                                   String timeLower, String timeUpper, String startLocation, String endLocation) {
        String queryString;
        queryString = generateQueryString(gender, dateLower, dateUpper, timeLower, timeUpper, startLocation,
                endLocation);
        if (queryString.equals(routeCommand)) {
            getAllRoutes();
            return routes;
        }
        try {

            PreparedStatement pstmt;
            pstmt = db.getPreparedStatement(queryString);
            setQueryParameters(pstmt);

            ResultSet rs = pstmt.executeQuery();
            generateRouteArray(rs);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return routes;
    }


///////////////////////////////---WIFI FILTERING---\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    /**
     * GenerateWifiArray takes a result set (set of records received from a database query) and creates a WifiLocation
     * from each result and adds them to an ArrayList.
     *
     * @param rs is a result set of data records from a query to the database
     */
    private void generateWifiArray(ResultSet rs) {
        try {
            while (rs.next()) {
                wifiLocations.add(new WifiLocation(rs.getString("wifi_id"), rs.getDouble("lat"),
                        rs.getDouble("lon"), rs.getString("address"),
                        rs.getString("ssid"), rs.getString("cost"),
                        rs.getString("provider"), rs.getString("remarks"),
                        rs.getString("city"), rs.getString("suburb"),
                        rs.getInt("zip")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * getAllWifiLocations gets all wifi points from the database and returns these as WifiLocation objects in an
     * ArrayList.
     */
    private void getAllWifiLocations() {
        String queryString = getAllWifiCommand;
        try {
            PreparedStatement pstmt;
            pstmt = db.getPreparedStatement(queryString);

            ResultSet rs = pstmt.executeQuery();
            generateWifiArray(rs);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * filterWifi takes all the possible filter values for wifi points and returns a ArrayList of WifiLocations that
     * meet the filter requirements.
     *
     * @param name of type String. This is a sub string that the user wants to filter wifi SSIDs by
     * @param suburb of type String. This is a string that the user wants to filter wifi suburbs by
     * @param type of type String. This is a string that the user wants to filter wifi types by
     * @param provider of type String. This is a sub string that the user wants to filter providers by
     * @return ArrayList<WifiLocation>, an ArrayList that contains WifiLocation objects
     */
    public ArrayList<WifiLocation> filterWifi(String name, String suburb, String type, String provider) {
        int queryLen = 0;
        String queryString = wifiCommand;

        if (name != null) {
            queryString = queryString + wifiNameCommand;
            queryLen += 1;
            filterVariableStrings.add("%" + name + "%");
        }
        if (suburb != null) {
            queryString = addAndToStmt(queryString, queryLen);
            queryString = queryString + boroughCommand;
            queryLen += 1;
            filterVariableStrings.add(suburb);
        }
        if (type != null) {
            queryString = addAndToStmt(queryString, queryLen);
            queryString = queryString + typeCommand;
            queryLen += 1;
            filterVariableStrings.add(type);
        }
        if (provider != null) {
            queryString = addAndToStmt(queryString, queryLen);
            queryString = queryString + providerCommand;
            queryLen += 1;
            filterVariableStrings.add("%" + provider + "%");
        }
        if (queryLen > 0) {
            queryString = queryString + commandEnd;
        }
        if (queryString.equals(wifiCommand)) {
            getAllWifiLocations();
            return wifiLocations;
        }

        try {
            PreparedStatement pstmt;
            pstmt = db.getPreparedStatement(queryString);
            for (int i = 0; i < queryLen; i++) {;
                pstmt.setString(i + 1, filterVariableStrings.get(i));
            }

            ResultSet rs = pstmt.executeQuery();
            generateWifiArray(rs);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return wifiLocations;
    }


///////////////////////////////---RETAIL FILTERING---\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    /**
     * GenerateRetailArray takes a result set (set of records received from a database query) and creates a
     * RetailLocation from each result and adds them to an ArrayList.
     *
     * @param rs is a result set of data records from a query to the database
     */
    private void generateRetailArray(ResultSet rs) {
        try {
            while (rs.next()) {
                retailLocations.add(new RetailLocation(rs.getString("retailer_name"),
                        rs.getString("address"), rs.getString("city"),
                        rs.getString("main_type"), rs.getString("secondary_type"),
                        rs.getString("state"), rs.getInt("zip"),
                        rs.getDouble("lat"), rs.getDouble("long")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * getAllRetailLocations gets all retailers from the database and returns these as RetailLocations objects in an
     * ArrayList.
     */
    private void getAllRetailLocations() {
        String queryString = getAllRetailersCommand;
        try {
            PreparedStatement pstmt;
            pstmt = db.getPreparedStatement(queryString);

            ResultSet rs = pstmt.executeQuery();
            generateRetailArray(rs);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * filterRetailers takes all the possible filter values for retailers and returns a ArrayList of retailLocations
     * that meet the filter requirements.
     *
     * @param name of type String. This is a sub string that the user wants to filter retail names by
     * @param address of type String. This is a sub string that the user wants to filter retail addresses by
     * @param primary of type String. This is a string that the user wants to filter retail primary types by
     * @param zip of type int. This is a integer that the user wants to filter retailer zip codes by
     * @return ArrayList<RetailLocation>, this is an ArrayList of RetailLocations objects
     */
    public ArrayList<RetailLocation> filterRetailers(String name, String address, String primary,int zip) {
        int queryLen = 0;
        String queryString = retailerCommand;

        if (name != null) {
            queryString = queryString + retailerNameCommand;
            queryLen += 1;
            filterVariableStrings.add("%" + name + "%");
        }
        if (address != null) {
            queryString = addAndToStmt(queryString, queryLen);
            queryString = queryString + streetCommand;
            queryLen += 1;
            filterVariableStrings.add("%" + address + "%");
        }
        if (primary != null) {
            queryString = addAndToStmt(queryString, queryLen);
            queryString = queryString + primaryCommand;
            queryLen += 1;
            filterVariableStrings.add("%" + primary + "%");
        }
        if (zip != -1) {
            queryString = addAndToStmt(queryString, queryLen);
            queryString = queryString + zipCommand;
            queryLen += 1;
            filterVariables.add(zip);
            System.out.println(filterVariables.get(0));
        }

        if (queryLen > 0) {
            queryString = queryString + commandEnd;
        }

        if (queryString.equals(retailerCommand)) {
            getAllRetailLocations();
            return retailLocations;
        }
        System.out.println(queryString);
        try {
            PreparedStatement pstmt;
            pstmt = db.getPreparedStatement(queryString);
            int i;
            for (i = 0; i < filterVariableStrings.size(); i++) {
                System.out.println(filterVariableStrings.get(i));
                pstmt.setString(i + 1, filterVariableStrings.get(i));
            }
            for (int j = 0; j < filterVariables.size(); j++) {
                System.out.println(filterVariables.get(j));
                pstmt.setInt(i + 1, filterVariables.get(j));
                i++;
            }

            ResultSet rs = pstmt.executeQuery();
            generateRetailArray(rs);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return retailLocations;
    }
}

