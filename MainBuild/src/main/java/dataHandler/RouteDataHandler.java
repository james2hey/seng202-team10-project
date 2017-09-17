package dataHandler;

import com.opencsv.CSVReader;
import main.DatabaseManager;

import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

/**
 * Created by jes143 on 17/09/17.
 */
public class RouteDataHandler {
    SQLiteDB db;

    String[] fields =
          { "tripduration            INTEGER" ,
            "start_year              INTEGER" ,
            "start_month             INTEGER" ,
            "start_day               INTEGER" ,
            "start_time              VARCHAR(19)" ,
            "end_year                INTEGER" ,
            "end_month               INTEGER" ,
            "end_day                 INTEGER" ,
            "end_time                VARCHAR(19)" ,
            "start_station_id        INTEGER" ,
            "start_station_name      VARCHAR(21)" ,
            "start_latitude          NUMERIC(9,6) NOT NULL" ,
            "start_longitude         NUMERIC(9,6) NOT NULL" ,
            "end_station_id          INTEGER" ,
            "end_station_name        VARCHAR(21)" ,
            "end_latitude            NUMERIC(9,6) NOT NULL" ,
            "end_longitude           NUMERIC(9,6) NOT NULL" ,
            "bikeid                  INTEGER" ,
            "usertype                VARCHAR(10)" ,
            "birth_year              INTEGER" ,
            "gender                  INTEGER"};
    String primarykey = "start_year, start_month, start_day, start_time, bikeid";

    PreparedStatement addTrip;
    String addTripString = "insert or fail into route_information values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    /**
     * Initialiser for a RouteDataHandler object that can process CSV files and add to the specified database.
     * @param db The database connection to add data to.
     */
    public RouteDataHandler(SQLiteDB db) {
        this.db = db;
        db.addTable("route_information", fields, primarykey);
        addTrip = db.getPreparedStatement(addTripString);
    }

    /**
     * Processes a CSV line and adds to the database if valid.
     * @param record A string array of object corresponding to the CSV
     * @return A bool stating the success state of the process.
     */
    private Boolean processLine(String[] record) {
        try {
            System.out.println("here" + record[0]);
            int duration = Integer.parseInt(record[0]);
            double start_lat = Double.parseDouble(record[5]);
            double start_lon = Double.parseDouble(record[6]);
            double end_lat = Double.parseDouble(record[9]);
            double end_lon = Double.parseDouble(record[10]);

            String[] date_time_start = record[1].split(" ");
            String[] date_start = date_time_start[0].split("/");
            int start_year = Integer.parseInt(date_start[2]);
            int start_month = Integer.parseInt(date_start[0]);
            int start_day = Integer.parseInt(date_start[1]);

            String[] date_time_end = record[2].split(" ");
            String[] date_end = date_time_start[0].split("/");
            int end_year = Integer.parseInt(date_end[2]);
            int end_month = Integer.parseInt(date_end[0]);
            int end_day = Integer.parseInt(date_end[1]);

            int gender = Integer.parseInt(record[14]);

            addTrip.setInt(1, duration);
            addTrip.setInt(2, start_year);
            addTrip.setInt(3, start_month);
            addTrip.setInt(4, start_day);
            addTrip.setString(5, date_time_start[1]);
            addTrip.setInt(6, end_year);
            addTrip.setInt(7, end_month);
            addTrip.setInt(8, end_day);
            addTrip.setString(9, date_time_end[1]);
            addTrip.setString(10, record[3]);
            addTrip.setString(11, record[4]);
            addTrip.setDouble(12, start_lat);
            addTrip.setDouble(13, start_lon);
            addTrip.setString(14, record[7]);
            addTrip.setString(15, record[8]);
            addTrip.setDouble(16, end_lat);
            addTrip.setDouble(17, end_lon);
            addTrip.setString(18, record[11]);
            addTrip.setString(19, record[12]);
            if (record[13].equals("")) {
                addTrip.setNull(20, Types.INTEGER);
            } else {
                addTrip.setInt(20, Integer.parseInt(record[13]));
            }
            addTrip.setInt(21, gender);
            addTrip.executeUpdate();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Incorrect string array size");
            return false;
        } catch (SQLException e) {
            // Re-init the prepstatement as it gets broken if we have a Primary Key fail
            addTrip = db.getPreparedStatement(addTripString);
            System.out.println(e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Unspecified error");
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
        }
        return true;
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
