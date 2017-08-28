package main;

import java.io.FileReader;

import java.io.IOException;
import java.util.Arrays;
import com.opencsv.CSVReader;

/**
 * Created by jes143 on 28/08/17.
 */
public class CSV_Importer {

    public static void readcsv(String file) {
        try {
            CSVReader reader = new CSVReader(new FileReader(file), ',');

            String[] record = null;

            while ((record = reader.readNext()) != null) {
                System.out.println(record[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
