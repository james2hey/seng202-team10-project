package dataAnalysis;

/**
 * Superclass for all location types. Allows them to all
 * be generalised as a location and then further extended.
 */
public abstract class Location {
    public double latitude, longitude;
    public String name;

    public Location(){}

    public Location(double latitude, double longitude, String name) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getString() {
        String la = Double.toString(latitude);
        String lo = Double.toString(longitude);
        return name + " " + la + " " + lo;
    }
}
