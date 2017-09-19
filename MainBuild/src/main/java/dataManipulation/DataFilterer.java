package dataManipulation;

import java.sql.*;
import java.util.ArrayList;
import java.time.Year;
////////////////////////////////

import dataAnalysis.Route;
import dataHandler.SQLiteDB;
import main.Main;


/**
 * DataFilterer class uses methods that sent requests to the database and return a array of data.
 */
public class DataFilterer {



    private String databaseCommand;
    private String genderCommand;
    private String ageCommand;
    private String timeCommand;
    private String dateYearCommand;
    private String dateMonthCommand;
    private String dateDayCommand;
    private String durationCommand;
    private String andCommand;
    private String commandEnd;

    private ArrayList<Route> routes;
    private ArrayList<Integer> filterVariables;
    private ArrayList<String> filterVariableStrings;

    private SQLiteDB db;


    /**
     * Constructor for DataFilterer class.
     */
    public DataFilterer(SQLiteDB db) {
        databaseCommand = "SELECT " +
                "* " +
                "FROM route_information WHERE ";
        genderCommand = "gender = ?";
        durationCommand = "tripduration BETWEEN ? AND ?";
        ageCommand = "birth_year BETWEEN ? AND ?";
        dateYearCommand = "start_year BETWEEN ? AND ?";
        dateMonthCommand = "start_month BETWEEN ? AND ?";
        dateDayCommand = "start_day BETWEEN ? AND ?";
        timeCommand = "start_time BETWEEN ? AND ?";
        andCommand = " AND ";
        commandEnd = ";";
        routes = new ArrayList<>();
        filterVariables = new ArrayList<>();
        filterVariableStrings = new ArrayList<>();
        this.db = db;
    }

    /**
     * clearRoutes clears the class variable ArrayList routes.
     */
    private void clearRoutes() {
        routes.clear();
    }

    /**
     * GenerateRouteArray takes a result set (set of records received from a database query) and creates a Route from
     * each result and adds them to an ArrayList.
     *
     * @param rs rs is a result set of data records from a query to the database.
     */
    private void generateRouteArray(ResultSet rs) {
        clearRoutes();
        try {
            while (rs.next()) {
                routes.add(new Route(rs.getInt("tripduration"), rs.getString("start_time"),
                        rs.getString("end_time"), rs.getInt("start_day"),
                        rs.getInt("start_month"), rs.getInt("start_year"),
                        rs.getInt("end_day"), rs.getInt("end_month"),
                        rs.getInt("end_year"), rs.getDouble("start_latitude"),
                        rs.getDouble("start_longitude"),
                        rs.getDouble("end_latitude"),
                        rs.getDouble("end_longitude"), rs.getInt("start_station_id"),
                        rs.getInt("end_station_id"), rs.getString("bikeid")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * addAndToStmt checks if the query statement has at least one other filter requirement included. If it does the
     * andCommand string is appended to the end of the query command. Takes a string, queryCommand, and a int,
     * queryLength, as parameters. Returns the query command.
     *
     * @param queryCommand queryCommand of type String. This is the string that will be used as the query statement to
     *                     the database.
     * @param queryLength queryLength of type int. This is the number of filter requirements that have been added to the
     *                    queryCommand already.
     * @return queryCommand, of type String. This is the string that will be used as a query statement to the database.
     */
    private String addAndToStmt(String queryCommand, int queryLength) {
        if (queryLength > 0) {
            queryCommand = queryCommand + andCommand;
        }
        return queryCommand;
    }


    /**
     * convertAges takes a lower and upper age and coverts them to a year of birth. This is required when filtering by
     * age as only the year of birth is stored for each record in the database, but the user will input the age to be
     * filtered by, not the year of birth.
     *
     * @param ageLower ageLower is of type int. It is the lower age limit of the person that completed a route, that the
     *                 user wants to filter by.
     * @param ageUpper ageUpper is of type int. It is the upper age limit of the person that completed a route, that the
     *                 user wants to filter by.
     * @return yearsOfBirth, of type int Array. This contains the upper and lower year of birth that data needs to be
     * filtered by.
     */
    private int[] convertAges(int ageLower, int ageUpper) {
        int yearsOfBirth[] = new int[2];
        int year = Year.now().getValue();
        int lowerRequiredYear = year - ageUpper;
        int upperRequiredYear = year - ageLower;
        yearsOfBirth[0] = lowerRequiredYear;
        yearsOfBirth[1] = upperRequiredYear;
        return yearsOfBirth;
    }


    /**
     * convertDates takes an upper and lower bound of dates and splits them up into separate integers. The date format
     * must be DD/MM/YYYY. These integers are added to a array, the order in the array is {DD,DD,MM,MM,YYYY,YYYY} with
     * the lowerDate values first. This array is returned.
     *
     * Ex. date: 21/01/2016 will be split into the integers 21, 1 and 2016
     *
     *
     * @param dateLower dateLower is of type String.
     * @param dateUpper dateUpper is of type String.
     * @return dateInts, of type int[]. This array holds all the integers from the lower and upper dates.
     */
    private int[] convertDates(String dateLower, String dateUpper) {
        int dateInts[] = new int[6];
        dateInts[0] = Integer.parseInt(dateLower.substring(0, 2));
        dateInts[1] = Integer.parseInt(dateUpper.substring(0, 2));
        dateInts[2] = Integer.parseInt(dateLower.substring(3, 5));
        dateInts[3] = Integer.parseInt(dateUpper.substring(3, 5));
        dateInts[4] = Integer.parseInt(dateLower.substring(6));
        dateInts[5] = Integer.parseInt(dateUpper.substring(6));
        return dateInts;
    }


    /**
     * generateQueryString takes all the possible filter requirement values and appends the necessary strings onto the
     * end of the database query statement. A value of -1 (int) or null (string) means the parameter has not been set
     * by the user, and this will not be used in the query.
     *
     * @param gender gender of type int. A value of -1 means not to filter by gender, 1 means filter by males and 2
     *               means filter by females.
     * @param dateLower dateLower is of type String. It is the lower limit that a route was started on, specified by
     *                  the user.
     * @param dateUpper dateUpper is of type String. It is the upper limit that a route was started on, specified by
     *                  the user.
     * @param ageLower ageLower is of type int. It is the lower age limit of the person that completed a route, that the
     *                 user wants to filter by.
     * @param ageUpper ageUpper is of type int. It is the upper age limit of the person that completed a route, that the
     *                 user wants to filter by.
     * @param timeLower timeLower is of type String. It is the lower time limit of starting a route the user wants to
     *                  filter by.planRoute
     * @param timeUpper timeUpper is of type String. It is the upper time limit of starting a route the user wants to
     *                  filter by.
     * @param durationLower durationLower is of type int. It is the lower duration of a route that the user wants to
     *                      filter by.
     * @param durationUpper durationUpper is of type int. It is the upper duration of a route that the user wants to
     *                      filter by.
     * @return queryCommand, of type String. This is the string that will be used as a query statement to the database.
     */
    private String generateQueryString(int gender, String dateLower, String dateUpper, int ageLower,
                                                int ageUpper, String timeLower, String timeUpper, int durationLower,
                                                int durationUpper) {
        String queryCommand = databaseCommand;
        int queryLength = 0;

        if (gender != -1) {
            queryCommand = queryCommand + genderCommand;
            filterVariables.add(gender);
            queryLength = 1;
        }
        if (durationLower != -1 && durationUpper != -1) {
            queryCommand = addAndToStmt(queryCommand, queryLength);
            queryCommand = queryCommand + durationCommand;
            filterVariables.add(durationLower);
            filterVariables.add(durationUpper);
            queryLength = 1;
        }
        if (ageLower != -1 && ageUpper != -1) {
            int ages[] = convertAges(ageLower, ageUpper);
            queryCommand = addAndToStmt(queryCommand, queryLength);
            queryCommand = queryCommand + ageCommand;
            filterVariables.add(ages[0]);
            filterVariables.add(ages[1]);
            queryLength = 1;
        }
        if (dateLower != null && dateUpper != null) {
            int dates[] = convertDates(dateLower, dateUpper);
            queryCommand = addAndToStmt(queryCommand, queryLength);
            queryCommand = queryCommand + dateDayCommand;
            queryLength = 1;
            queryCommand = addAndToStmt(queryCommand, queryLength);
            queryCommand = queryCommand + dateMonthCommand;
            queryCommand = addAndToStmt(queryCommand, queryLength);
            queryCommand = queryCommand + dateYearCommand;
            int datesLength = dates.length;
            for (int i = 0; i < datesLength; i++) {
                filterVariables.add(dates[i]);
            }
            queryLength = 1;
        }
        if (timeLower != null && timeUpper != null) {
            queryCommand = addAndToStmt(queryCommand, queryLength);
            queryCommand = queryCommand + timeCommand;
            filterVariableStrings.add(timeLower);
            filterVariableStrings.add(timeUpper);
        }
        if (queryLength > 0) {
            queryCommand = queryCommand + commandEnd;
        }
        return queryCommand;
    }

    /**
     * setQueryParameters takes a PreparedStatement as a parameter and uses the values in an class variable ArrayList,
     * filterVariables, to set the parameters of the PreparedStatement.
     *
     * @param pstmt pstmt of type PreparedStatement. This is the query statement to be called to the database.
     * @return pstmt, of type PreparedStatement. The updated PreparedStatement, now with its parameters set to the
     * correct values.
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
     * filter takes all the possible filter requirement values and returns a ArrayList of routes that meet the filter
     * requirements.
     *
     * @param gender gender of type int. A value of -1 means not to filter by gender, 1 means filter by males and 2
     *               means filter by females.
     * @param dateLower dateLower is of type String. It is the lower limit that a route was started on, specified by
     *                  the user.
     * @param dateUpper dateUpper is of type String. It is the upper limit that a route was started on, specified by
     *                  the user.
     * @param ageLower ageLower is of type int. It is the lower age limit of the person that completed a route, that the
     *                 user wants to filter by.
     * @param ageUpper ageUpper is of type int. It is the upper age limit of the person that completed a route, that the
     *                 user wants to filter by.
     * @param timeLower timeLower is of type String. It is the lower time limit of starting a route the user wants to
     *                  filter by.
     * @param timeUpper timeUpper is of type String. It is the upper time limit of starting a route the user wants to
     *                  filter by.
     * @param durationLower durationLower is of type int. It is the lower duration of a route that the user wants to
     *                      filter by.
     * @param durationUpper durationUpper is of type int. It is the upper duration of a route that the user wants to
     *                      filter by.
     * @return routes, of type ArrayList. This is the array that contains all filtered routes.
     */
    public ArrayList<Route> filter(int gender, String dateLower, String dateUpper, int ageLower,
                                   int ageUpper, String timeLower, String timeUpper, int durationLower,
                                   int durationUpper) {
        String queryString;
        queryString = generateQueryString(gender, dateLower, dateUpper, ageLower, ageUpper, timeLower, timeUpper,
                durationLower, durationUpper);
        if (queryString.equals(databaseCommand)) {
            clearRoutes();
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


//    /**
//     * filterByGender takes a string that specifies the gender to filter records by and gets all route records that have
//     * the corresponding gender to the method parameter.
//     *
//     * @param gender gender of type String. Specifies the gender to filter records by.
//     */
//    public ArrayList<Route> filterByGender(String gender) {
//
//        int genderInteger = 0;
//
//        if ("F".equals(gender)) {
//            genderInteger = 2;
//        } else if ("M".equals(gender)) {
//            genderInteger = 1;
//        }
//
//        String command = "gender = ?";
//
//        try(Connection conn = this.connect();
//            PreparedStatement pstmt = conn.prepareStatement(databaseCommand + command)) {
//
//            //set value using parameter
//            pstmt.setInt(1, genderInteger);
//
//            ResultSet rs = pstmt.executeQuery();
//            generateRouteArray(rs);
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        return routes;
//    }
//
//
//    /**
//     * filterByDate take an upper date and lower date and query's the database to find all records that were started
//     * between those dates.
//     *
//     * @param upperDate upperDate is of type Date from the sql package. It is the upper limit that a route was
//     *                  started on, specified by the user.
//     * @param lowerDate lowerDate is of type Date from the sql package. It is the lower limit that a route was
//     *                  started on, specified by the user.
//     */
//    public ArrayList<Route> filterByDate(Date upperDate, Date lowerDate) {
//
//        String command = "start_date BETWEEN ? AND ?";
//
//        try(Connection conn = this.connect();
//            PreparedStatement pstmt = conn.prepareStatement(databaseCommand + command)) {
//
//            //set value using parameter
//            pstmt.setDate(1, lowerDate);
//            pstmt.setDate(2, upperDate);
//
//            ResultSet rs = pstmt.executeQuery();
//            generateRouteArray(rs);
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        return routes;
//    }
//
//
//    /**
//     * filterByAge takes an upper age limit and lower age limit and query's the database to find all records that were
//     * completed between those two ages.
//     *
//     * @param upperAge upperAge is of type int. It is the upper age limit of the person that completed a route, that the
//     *                 user wants to filter by.
//     * @param lowerAge lowerAge is of type int. It is the lower age limit of the person that completed a route, that the
//     *                 user wants to filter by.
//     */
//    public ArrayList<Route> filterByAge(int upperAge, int lowerAge) {
//
//        int year = Year.now().getValue();
//        int LowerRequiredYear = year - upperAge;
//        int UpperRequiredYear = year - lowerAge;
//
//        String command = "birth_year BETWEEN ? AND ?";
//
//        try(Connection conn = this.connect();
//            PreparedStatement pstmt = conn.prepareStatement(databaseCommand + command)) {
//
//            //set value using parameter
//            pstmt.setInt(1, LowerRequiredYear);
//            pstmt.setInt(2, UpperRequiredYear);
//
//            ResultSet rs = pstmt.executeQuery();
//            generateRouteArray(rs);
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        return routes;
//    }
//
//
//    /**
//     * filterByTime takes an upper time limit and lower time limit and query's the database to find all records that
//     * were started between those times.
//     *
//     * @param upperTime upperTime is of type Time from the sql package. It is the upper time limit of starting a route
//     *                  the user wants to filter by.
//     * @param lowerTime lowerTime is of type Time from the sql package. It is the lower time limit of starting a route
//     *                  the user wants to filter by.
//     */
//    public ArrayList<Route> filterByTime(Time upperTime, Time lowerTime) {
//
//        String command = "start_time BETWEEN ? AND ?";
//
//        try(Connection conn = this.connect();
//            PreparedStatement pstmt = conn.prepareStatement(databaseCommand + command)) {
//
//            //set value using parameter
//            pstmt.setTime(1, lowerTime);
//            pstmt.setTime(2, upperTime);
//
//            ResultSet rs = pstmt.executeQuery();
//            generateRouteArray(rs);
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        return routes;
//    }
//
//
//    /**
//     * filterByDuration takes a minimum duration limit and maximum duration limit and query's the database to return all
//     * records with a duration that falls within the two limits.
//     *
//     * @param upperDuration upperDuration is of type int. It is the upper duration of a route that the user wants to
//     *                      filter by.
//     * @param lowerDuration lowerDuration is of type int. It is the lower duration of a route that the user wants to
//     *                      filter by.
//     */
//    public ArrayList<Route> filterByDuration(int upperDuration, int lowerDuration) {
//
//        String command = "tripduration BETWEEN ? AND ?";
//
//        try(Connection conn = this.connect();
//            PreparedStatement pstmt = conn.prepareStatement(databaseCommand + command)) {
//
//
//            //set value using parameter
//            pstmt.setInt(1, lowerDuration);
//            pstmt.setInt(2, upperDuration);
//
//            ResultSet rs = pstmt.executeQuery();
//            generateRouteArray(rs);
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        return routes;
//    }

//    public ArrayList<Route> filterdata() {
//
//        String command = "start_time BETWEEN ? AND ?";
//
//        try(Connection conn = this.connect();
//            PreparedStatement pstmt = conn.prepareStatement(databaseCommand + command)) {
//
//            //set value using parameter
//            pstmt.setDate(1, lowerDate);
//            pstmt.setDate(2, upperDate);
//
//            ResultSet rs = pstmt.executeQuery();
//            generateRouteArray(rs);
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        return routes;
//    }
}