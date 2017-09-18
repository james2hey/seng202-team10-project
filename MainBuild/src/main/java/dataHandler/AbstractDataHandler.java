//package dataHandler;
//
//import java.io.FileReader;
//
//import java.io.IOException;
//import java.sql.PreparedStatement;
//import java.util.Arrays;
//import com.opencsv.CSVReader;
//import dataHandler.SQLiteDB;
//import main.DatabaseManager;
//import main.DatabaseUser;
//import org.apache.commons.lang3.ObjectUtils;
//
//
///**
// * Created by jes143 on 28/08/17.
// * Currently this whole thing requires the default formatting of CSVs
// */
//public class AbstractDataHandler {
//
//    protected SQLiteDB db;
//    protected String[] fields;
//    protected String primaryKey;
//    protected String tableName;
//    protected PreparedStatement addData;
//    protected String addDataStatement;
//
//    /**
//     * Initialiser for an AbstractDataHandler object that can process CSV files and add to the specified database.
//     * @param db The database connection to add data to.
//     */
//    public AbstractDataHandler (SQLiteDB db) {
//        System.out.println("here");
//        this.db = db;
//        System.out.println(tableName);
//    }
//
//    public static void readcsv(String file, int type) {
//        try {
//            CSVReader reader = new CSVReader(new FileReader(file), ',');
//
//            String[] record;
//            reader.readNext(); // Skip first line as it's the desc
//            while ((record = reader.readNext()) != null) {
//                //System.out.println(record[0]);
//                if (type == 1) {
//                    DatabaseManager.addRetailer(record[0], record[1], 0.0, 0.0, record[3], record[4], record[5], record[7], record[8]);
//                } else if (type == 2) {
//                    double lat = Double.parseDouble(record[7]);
//                    double lon = Double.parseDouble(record[8]);
//                    DatabaseManager.addWifi(record [0], record[3], record[4], record[6], lat, lon, record[12], record[13], record[14], record[18], record[22]);
//                } else if (type == 3) {
//                    int duration = Integer.parseInt(record[0]);
//                    double start_lat = Double.parseDouble(record[5]);
//                    double start_lon = Double.parseDouble(record[6]);
//                    double end_lat = Double.parseDouble(record[9]);
//                    double end_lon = Double.parseDouble(record[10]);
//                    String[] date_time_start = record[1].split(" ");
//                    String[] date_start = date_time_start[0].split("/");
//
//                    String[] date_time_end = record[2].split(" ");
//                    String[] date_end = date_time_start[0].split("/");
//
//                    int birth_year = -1;
//                    if (!"".equals(record[13])) {
//                        birth_year = Integer.parseInt(record[13]);
//                    }
//                    int gender = Integer.parseInt(record[14]);
//                    System.out.println("2");
//                    DatabaseManager.addTrip(duration, Integer.parseInt(date_start[2]), Integer.parseInt(date_start[0]), Integer.parseInt(date_start[1]), date_time_start[1], Integer.parseInt(date_end[2]), Integer.parseInt(date_end[0]), Integer.parseInt(date_end[1]), date_time_end[1], record[3], record[4], start_lat, start_lon, record[7], record[8], end_lat, end_lon, record[11], record[12], birth_year, gender);
//                } else if (type == 4) {
//                    String name = record[0];
//                    DatabaseUser.addUser(name);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void processCSV(String url) {
//        try {
//            System.out.println("CSV");
//            db.setAutoCommit(false);
//            System.out.println("CSV");
//            CSVReader reader = new CSVReader(new FileReader(url), ',');
//
//            String[] record;
//            reader.readNext(); // Skip first line as it's the desc
//            while ((record = reader.readNext()) != null) {
//                System.out.println(processLine(record));
//            }
//            db.setAutoCommit(true);
//            db.commit();
//        } catch (IOException e) {
//            System.out.println("File not found");
//        }
//    }
//
//    private Boolean processLine(String[] record) {
//        return false;
//    };
//
//}
