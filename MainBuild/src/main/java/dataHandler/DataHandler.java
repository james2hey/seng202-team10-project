package dataHandler;

public interface DataHandler {

    void processLine(String[] record, Callback callback);

    boolean canProcess(int columnCount);

    String getFieldCounts();

}
