package dataHandler;

/**
 * Created by jes143 on 2/10/17.
 */
public interface DataHandler {

    void processLine(String[] record, Callback callback);
    int fieldCount();

}
