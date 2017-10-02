package dataAnalysis;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Superclass for all location types. Allows them to all
 * be generalised as a location and then further extended.
 */
public abstract class Location {
    public int number;
    public double latitude, longitude;
    public String name, address;

    public Location() {
    }

    public Location(double latitude, double longitude, String name, String address, int number) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.address = address;
        this.number = number;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getString() {
        String la = Double.toString(latitude);
        String lo = Double.toString(longitude);
        return name + " " + la + " " + lo;
    }

    /**
     * Defines the hashCode function.
     * Allows for hashsets that don't collide on the same points
     * Collides if the other location has same lat, lon, and name
     *
     * @return A hash value
     */
    public int hashCode() {
        return new HashCodeBuilder(137, 337)
                .append(latitude)
                .append(longitude)
                .append(name)
                .toHashCode();
    }

    /**
     * Defines the equals functions, allowing for easy equality testing
     * Fails if the other location has same lat, lon, and name
     *
     * @param obj The object to compare
     * @return Equality boolean
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof Location)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        Location rhs = (Location) obj;
        return new EqualsBuilder()
                .append(latitude, rhs.getLatitude())
                .append(longitude, rhs.getLongitude())
                .append(name, rhs.getName())
                .isEquals();
    }
}
