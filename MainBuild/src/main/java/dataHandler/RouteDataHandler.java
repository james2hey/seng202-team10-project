package dataHandler;

import com.opencsv.CSVReader;
import main.AbstractDataHandler;

import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Created by jes143 on 17/09/17.
 */
public class RouteDataHandler {
    SQLiteDB db;

    String[] fields =
            {"tripduration            INTEGER",
                    "start_year              INTEGER",
                    "start_month             INTEGER",
                    "start_day               INTEGER",
                    "start_time              VARCHAR(19)",
                    "end_year                INTEGER",
                    "end_month               INTEGER",
                    "end_day                 INTEGER",
                    "end_time                VARCHAR(19)",
                    "start_station_id        INTEGER",
                    "start_station_name      VARCHAR(21)",
                    "start_latitude          NUMERIC(9,6) NOT NULL",
                    "start_longitude         NUMERIC(9,6) NOT NULL",
                    "end_station_id          INTEGER",
                    "end_station_name        VARCHAR(21)",
                    "end_latitude            NUMERIC(9,6) NOT NULL",
                    "end_longitude           NUMERIC(9,6) NOT NULL",
                    "bikeid                  INTEGER",
                    "usertype                VARCHAR(10)",
                    "birth_year              INTEGER",
                    "gender                  INTEGER"};
    String primaryKey = "start_year, start_month, start_day, start_time, bikeid";
    String tableName = "route_information";

    PreparedStatement addData;
    String addDataStatement = "insert or fail into route_information values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    /**
     * Initialiser for a RouteDataHandler object that can process CSV files and add to the specified database.
     *
     * @param db The database connection to add data to.
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
            int start_year = Integer.parseInt(date_start[2]);
            int start_month = Integer.parseInt(date_start[0]);
            int start_day = Integer.parseInt(date_start[1]);
            String start_time = date_time_start[1];

            String[] date_time_end = record[2].split(" ");
            String[] date_end = date_time_start[0].split("/");
            int end_year = Integer.parseInt(date_end[2]);
            int end_month = Integer.parseInt(date_end[0]);
            int end_day = Integer.parseInt(date_end[1]);
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
        } catch (Exception e) {
            System.out.println("Unspecified error");
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Boolean addSingleEntry(int tripduration, int start_year, int start_month, int start_day, String start_time,
                                  int end_year, int end_month, int end_day, String end_time, String start_station_id,
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

    public void processCSV(String url) {
        try {
            db.setAutoCommit(false);
            CSVReader reader = new CSVReader(new FileReader(url), ',');

            String[] record;
            reader.readNext(); // Skip first line as it's the desc
            while ((record = reader.readNext()) != null) {
                System.out.println(processLine(record));
            }
            db.setAutoCommit(true);
            db.commit();
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }
}
