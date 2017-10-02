package dataHandler;

import com.opencsv.CSVReader;
import javafx.concurrent.Task;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jes143 on 18/09/17.
 */

public class CSVImporter extends Task<Void> implements Callback{

    SQLiteDB db;

    private final String url;

    private DataHandler handler;
    int[] successFailCounts = {0, 0};
    int resulted = 0;
    int totalCount;

    /**
     * Initializes an object, linked to the given database. Can process CSVs and add single entries
     * @param db
     * @param url
     */
    public CSVImporter(SQLiteDB db, String url, DataHandler handler) {
        this.db = db;
        this.url = url;
        this.handler = handler;
    }


    @Override
    protected Void call() throws Exception {
        System.out.println("-1");
        db.setAutoCommit(false);
        CSVReader reader = new CSVReader(new FileReader(url), ',');
        List<String[]> records = reader.readAll();
        System.out.println("-1");
        if (records.get(0).length != handler.fieldCount()) {
            System.out.println("1");
            throw new NoSuchFieldException(String.format("Incorrect number of fields, expected %d but got %d", handler.fieldCount(), records.get(0).length));
        }
        totalCount = records.size() -1;

        for (int i = 1; i <= totalCount; i++) {
            if (isCancelled()) {
                db.rollback();
                db.setAutoCommit(true);
                return null;
            }
            handler.processLine(records.get(i), this);
        }
        while (resulted < totalCount) {
            Thread.sleep(50);
        }
        db.commit();
        db.setAutoCommit(true);
        return null;
    }

    @Override
    public void result(boolean result) {
        resulted ++;
        if (result) {
            successFailCounts[0] += 1;
        } else {
            successFailCounts[1] += 1;
        }
        updateProgress(resulted, totalCount);

        updateMessage(String.format("Currently processed %d records out of %d.\n" +
                "Successfully imported: %d records\n" +
                "Failed to import: %d records.", resulted, totalCount, successFailCounts[0], successFailCounts[1]));
        System.out.println(totalCount);
        System.out.println(resulted);
    }
}

