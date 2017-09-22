package dataAnalysis;

/**
 * Does...
 */

public class StationLocation extends Location {
    int number;

    public StationLocation(int stationNumber, double stationLatitude, double stationLongitude, String stationAddress) {
        number = stationNumber;
        latitude = stationLatitude;
        longitude = stationLongitude;
        address = stationAddress;
    }


    public int getNumber() {
        return number;
    }



}