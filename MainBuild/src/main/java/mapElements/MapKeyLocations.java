package mapElements;

/**
 * Holds data for key locations on the NY map.
  */

public class MapKeyLocations {
    int longitude;
    int latitute;
    String name;
    String city;

    /**
     * Getters and setters.
     */

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int input) {
        longitude = input;
    }

    public int getLatitute() {
        return latitute;
    }

    public void setLatitute(int input) {
        latitute = input;
    }

}