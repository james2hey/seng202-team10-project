package dataHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * A fake RetailerDataHandler that overrides the process line function to remove the geocoder for unit tests
 */

public class RetailerDataHandlerFake extends RetailerDataHandler {

    public RetailerDataHandlerFake(SQLiteDB db) {
        super(db);
    }
    @Override
    public void processLine(String[] record, Callback callback) {
        System.out.println("here");
        if (record.length == 18 && !record[10].equals("")) {
            double lat;
            double lon;
            try {
                lat = Double.parseDouble(record[10]);
                lon = Double.parseDouble(record[11]);
            } catch (NumberFormatException e) {
                callback.result(false);
                return;
            }
            callback.result(addSingleEntry(record[0], record[1], lat, lon, record[3], record[4], record[5], record[7], record[8]));
        } else if (record.length == 9 || record.length == 18) {
            callback.result(addSingleEntry(record[0], record[1], 0.0, 0.0, record[3], record[4], record[5], record[7], record[8]));
        } else {
            callback.result(false);
        }
    }
}
