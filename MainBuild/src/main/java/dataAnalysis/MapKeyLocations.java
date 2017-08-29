package dataAnalysis;

/**
 * Holds data for key locations on the NY map.
  */

public class MapKeyLocations {
    private int longitude;
    private int latitute;
    private String name;
    private String city;

    /**
     * Constructor for MapKeyLocations.
     */
    public MapKeyLocations() {
        longitude = 0;
        latitute = 0;
        name = "NULL";
        city = "NULL";
    }

    public MapKeyLocations(int locationLongitude, int locationLatitude, String locationName, String locationCity) {
        longitude = locationLongitude;
        latitute = locationLatitude;
        name = locationName;
        city = locationCity;
    }

    /**
     * Getters, no need for setters as the latitude and longitude is set in
     * the location class.
     */

    public int getLongitude() {
        return longitude;
    }

    public int getLatitute() {
        return latitute;
    }

}