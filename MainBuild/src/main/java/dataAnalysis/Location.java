package dataAnalysis;

/**
 * Superclass for all location types. Allows them to all
 * be generalised as a location and then further extended.
 */
public abstract class Location {
    int latitude, longitude;
    String name;

    public Location() {
    }
}
