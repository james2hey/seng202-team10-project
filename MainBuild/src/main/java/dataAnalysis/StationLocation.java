package dataAnalysis;

/**
 * Stores data about a bike station.
 */

public class StationLocation extends Location {
    int number;

    // Constructor
    public StationLocation(int stationNumber, double stationLatitude, double stationLongitude, String stationAddress) {
        number = stationNumber;
        latitude = stationLatitude;
        longitude = stationLongitude;
        address = stationAddress;
    }
}