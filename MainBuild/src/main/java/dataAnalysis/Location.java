package dataAnalysis;

/**
 * Superclass for all location types. Allows them to all
 * be generalised as a location and then further extended.
 */
public abstract class Location {
    public double latitude, longitude;
    public String name;


    public Location() {
    }
}
