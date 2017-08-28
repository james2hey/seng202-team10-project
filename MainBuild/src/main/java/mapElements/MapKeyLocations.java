package mapElements;

/**
 * Holds data for key locations on the NY map.
  */

public class MapKeyLocations {
    private int longitude;
    private int latitute;
    private String name;
    private String city;

    /**
     * Getters
     */
    //I dont think we need setters. They're set in their own class and their position shouldn't change.

    public int getLongitude() {
        return longitude;
    }

    public int getLatitute()
    {
        return latitute;
    }

    /**
     * Calculates the distance between two locations
     */
}