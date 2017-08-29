package dataAnalysis;

/**
 * Superclass for all location types. Allows them to all
 * be generalised as a location and then further extended.
 */
public abstract class Location {
    double latitude, longitude;
    String name;


    public Location() {
    }
}
