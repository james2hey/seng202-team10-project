package dataHandler;

import GUIControllers.Controller;
import com.opencsv.CSVReader;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;

import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by jes143 on 17/09/17.
 */
public class RouteDataHandler {
    SQLiteDB db;

    String[] fields =
            {
                    "tripduration            INTEGER",
                    "start_year              VARCHAR(4)",
                    "start_month             VARCHAR(2)",
                    "start_day               VARCHAR(2)",
                    "start_time              VARCHAR(19)",
                    "end_year                VARCHAR(4)",
                    "end_month               VARCHAR(2)",
                    "end_day                 VARCHAR(2)",
                    "end_time                VARCHAR(19)",
                    "start_station_id        VARCHAR(6)",
                    "start_station_name      VARCHAR(21)",
                    "start_latitude          NUMERIC(9,6) NOT NULL",
                    "start_longitude         NUMERIC(9,6) NOT NULL",
                    "end_station_id          VARCHAR(6)",
                    "end_station_name        VARCHAR(21)",
                    "end_latitude            NUMERIC(9,6) NOT NULL",
                    "end_longitude           NUMERIC(9,6) NOT NULL",
                    "bikeid                  VARCHAR(20)",
                    "usertype                VARCHAR(10)",
                    "birth_year              INTEGER",
                    "gender                  INTEGER"};
    String primaryKey = "start_year, start_month, start_day, start_time, bikeid";
    String tableName = "route_information";

    PreparedStatement addData;
    String addDataStatement = "insert or fail into route_information values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    /**
     * Initializes an object, linked to the given database. Can process CSVs and add single entries
     * @param db
     */
    public RouteDataHandler(SQLiteDB db) {
        this.db = db;
        db.addTable(tableName, fields, primaryKey);
        addData = db.getPreparedStatement(addDataStatement);
    }

    /**
     * Processes a CSV line and adds to the database if valid.
     *
     * @param record A string array of object corresponding to the CSV
     * @return A bool stating the success state of the process.
     */
    private Boolean processLine(String[] record) {
        try {
            int duration = Integer.parseInt(record[0]);

            String[] date_time_start = record[1].split(" ");
            String[] date_start = date_time_start[0].split("/");
            String start_year = date_start[2];
            String start_month = StringUtils.leftPad(date_start[0], 2, '0');
            String start_day = StringUtils.leftPad(date_start[1], 2, '0');
            String start_time = date_time_start[1];

            String[] date_time_end = record[2].split(" ");
            String[] date_end = date_time_start[0].split("/");
            String end_year = date_end[2];
            String end_month = StringUtils.leftPad(date_end[0], 2, '0');
            String end_day = StringUtils.leftPad(date_end[1], 2, '0');
            String end_time = date_time_end[1];


            double start_lat = Double.parseDouble(record[5]);
            double start_lon = Double.parseDouble(record[6]);
            double end_lat = Double.parseDouble(record[9]);
            double end_lon = Double.parseDouble(record[10]);

            int gender = Integer.parseInt(record[14]);
            Integer birth_year;
            if (record[13].equals("")) {
                birth_year = null;
            } else {
                birth_year = Integer.parseInt(record[13]);
            }

            return addSingleEntry(
                    duration, start_year, start_month, start_day, start_time, end_year,
                    end_month, end_day, end_time, record[3], record[4], start_lat, start_lon, record[7],
                    record[8], end_lat, end_lon, record[11], record[12], birth_year, gender);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Incorrect string array size");
            return false;
        }
    }

    /**
     * Takes a full list of parameters for an element in the table and adds that to the database using a PreparedStatement
     * @param tripduration
     * @param start_year
     * @param start_month
     * @param start_day
     * @param start_time
     * @param end_year
     * @param end_month
     * @param end_day
     * @param end_time
     * @param start_station_id
     * @param start_station_name
     * @param start_latitude
     * @param start_longitude
     * @param end_station_id
     * @param end_station_name
     * @param end_latitude
     * @param end_longitude
     * @param bikeid
     * @param usertype
     * @param birth_year
     * @param gender
     * @return A value representing the success of the addition. Fails on such things as PrimaryKey collisions.
     */
    public Boolean addSingleEntry(int tripduration, String start_year, String start_month, String start_day, String start_time,
                                  String end_year, String end_month, String end_day, String end_time, String start_station_id,
                                  String start_station_name, double start_latitude, double start_longitude,
                                  String end_station_id, String end_station_name, double end_latitude,
                                  double end_longitude, String bikeid, String usertype, Integer birth_year, int gender) {
        try {
            addData.setObject(1, tripduration);
            addData.setObject(2, start_year);
            addData.setObject(3, start_month);
            addData.setObject(4, start_day);
            addData.setObject(5, start_time);
            addData.setObject(6, end_year);
            addData.setObject(7, end_month);
            addData.setObject(8, end_day);
            addData.setObject(9, end_time);
            addData.setObject(10, start_station_id);
            addData.setObject(11, start_station_name);
            addData.setObject(12, start_latitude);
            addData.setObject(13, start_longitude);
            addData.setObject(14, end_station_id);
            addData.setObject(15, end_station_name);
            addData.setObject(16, end_latitude);
            addData.setObject(17, end_longitude);
            addData.setObject(18, bikeid);
            addData.setObject(19, usertype);
            addData.setObject(20, birth_year);
            addData.setObject(21, gender);
            addData.executeUpdate();
            return true;
        } catch (SQLException e) {
            addData = db.getPreparedStatement(addDataStatement);
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Takes a CSV file and repeatedly calls processLine on the records
     * @param url A string directing to a valid filepath
     * @return An integer list with the count of successful additions and failed additions.
     * @throws IOException Thrown if there are errors reading the file
     * @throws NoSuchFieldException Thrown if there are an incorrect number of fields in the CSV
     */
    public int[] processCSV(String url) throws IOException, NoSuchFieldException {
        int[] successFailCounts = {0, 0};
        db.setAutoCommit(false);
        CSVReader reader = new CSVReader(new FileReader(url), ',');

        String[] record;
        record = reader.readNext(); // Skip first line as it's the desc
        if (record.length != 15) {
            throw new NoSuchFieldException("Incorrect number of fields, expected 15 but got " + record.length);
        }
        while ((record = reader.readNext()) != null) {
            if (processLine(record)) {
                successFailCounts[0] += 1;
                System.out.println("Suc");
            } else {
                successFailCounts[1] += 1;
                System.out.println("F");
            }
        }
        db.setAutoCommit(true);
        db.commit();
        return successFailCounts;
    }

    /**
     * Takes a series of strings and calculates the difference in times between the two. Ignore daylight savings time currently
     * @param start_year
     * @param start_month
     * @param start_day
     * @param start_time
     * @param end_year
     * @param end_month
     * @param end_day
     * @param end_time
     * @return Difference in seconds
     */
    public int getDuration(String start_year, String start_month, String start_day, String start_time,
                           String end_year, String end_month, String end_day, String end_time) {
        String[] start_time_seperated = start_time.split(":");
        int start_hour = Integer.parseInt(start_time_seperated[0]);
        int start_min = Integer.parseInt(start_time_seperated[1]);
        int start_sec = Integer.parseInt(start_time_seperated[2]);

        String[] end_time_seperated = end_time.split(":");
        int end_hour = Integer.parseInt(end_time_seperated[0]);
        int end_min = Integer.parseInt(end_time_seperated[1]);
        int end_sec = Integer.parseInt(end_time_seperated[2]);

        int start_year_int = Integer.parseInt(start_year);
        int start_month_int = Integer.parseInt(start_month);
        int start_day_int = Integer.parseInt(start_day);

        int end_year_int = Integer.parseInt(end_year);
        int end_month_int = Integer.parseInt(end_month);
        int end_day_int = Integer.parseInt(end_day);

        DateTime start = new DateTime(start_year_int, start_month_int, start_day_int, start_hour, start_min, start_sec, DateTimeZone.UTC);
        DateTime end = new DateTime(end_year_int, end_month_int, end_day_int, end_hour, end_min, end_sec, DateTimeZone.UTC);
        Duration duration = new Duration(start, end);
        return duration.toStandardSeconds().getSeconds();
    }
}

