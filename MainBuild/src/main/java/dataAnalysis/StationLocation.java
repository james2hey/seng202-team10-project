package dataAnalysis;

/**
 * Does...
 */

public class StationLocation extends Location {
    int number;

    public StationLocation(int stationNumber, double stationLatitude, double stationLongitude) {
        number = stationNumber;
        latitude = stationLatitude;
        longitude = stationLongitude;
    }


    public int getNumber() {
        return number;
    }

}