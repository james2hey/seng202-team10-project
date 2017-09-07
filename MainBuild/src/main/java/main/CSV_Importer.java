package main;

import java.io.FileReader;

import java.io.IOException;
import java.util.Arrays;
import com.opencsv.CSVReader;
import org.apache.commons.lang3.ObjectUtils;


/**
 * Created by jes143 on 28/08/17.
 * Currently this whole thing requires the default formatting of CSVs
 */
public class CSV_Importer {

    public static void readcsv(String file, int type) {
        try {
            CSVReader reader = new CSVReader(new FileReader(file), ',');

            String[] record;
            reader.readNext(); // Skip first line as it's the desc
            while ((record = reader.readNext()) != null) {
                //System.out.println(record[0]);
                if (type == 1) {
                    DatabaseManager.addRetailer(record[0], record[1], 0.0, 0.0, record[3], record[4], record[5], record[7], record[8]);
                } else if (type == 2) {
                    double lat = Double.parseDouble(record[7]);
                    double lon = Double.parseDouble(record[8]);
                    DatabaseManager.addWifi(record [0], record[3], record[4], record[6], lat, lon, record[12], record[13], record[14], record[18], record[22]);
                } else if (type == 3) {
                    int duration = Integer.parseInt(record[0]);
                    double start_lat = Double.parseDouble(record[5]);
                    double start_lon = Double.parseDouble(record[6]);
                    double end_lat = Double.parseDouble(record[9]);
                    double end_lon = Double.parseDouble(record[10]);
                    int birth_year = -1;
                    if (! "".equals(record[13])) {
                        birth_year = Integer.parseInt(record[13]);
                    }
                    int gender = Integer.parseInt(record[14]);
                    DatabaseManager.addTrip(duration, record[1], record[2], record[3], record[4], start_lat, start_lon, record[7], record[8], end_lat, end_lon, record[11], record[12], birth_year, gender);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}