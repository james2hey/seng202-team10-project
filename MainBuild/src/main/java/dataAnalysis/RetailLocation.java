package dataAnalysis;

import dataManipulation.UpdateData;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Stores the data about a retail store.
 */

public class RetailLocation extends Location {
    private int zip;
    private String city, state, mainType, secondaryType;

    // Constructor
    public RetailLocation(String retailName, String retailStreet, String retailCity, String mType, String sType,
                          String retailState, int zipNumber, double lat, double lon) {
        name = retailName;
        address = retailStreet;
        city = retailCity;
        mainType = mType;
        secondaryType = sType;
        state = retailState;
        zip = zipNumber;
        latitude = lat;
        longitude = lon;
    }


    // Getters
    public int getZip() {
        return zip;
    }

    // Setters
    public void setZip(int newZip) {
        if (newZip != zip) {
            zip = newZip;
            UpdateData.updateRetailerField("zip", Integer.toString(zip), name, address);
        }
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String newCity) {
        if (!newCity.equals(city)) {
            city = newCity;
            UpdateData.updateRetailerField("city", city, name, address);
        }
    }

    public String getState() {
        return state;
    }

    public void setState(String newState) {
        if (!newState.equals(state)) {
            state = newState;
            UpdateData.updateRetailerField("state", state, name, address);
        }
    }

    public String getMainType() {
        return mainType;
    }

    public void setMainType(String newMainType) {
        if (!newMainType.equals(mainType)) {
            mainType = newMainType;
            UpdateData.updateRetailerField("main_type", mainType, name, address);
        }
    }

    public String getSecondaryType() {
        return secondaryType;
    }

    public void setSecondaryType(String newSecondaryType) {
        if (!newSecondaryType.equals(secondaryType)) {
            secondaryType = newSecondaryType;
            UpdateData.updateRetailerField("secondary_type", secondaryType, name, address);
        }
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double newLat) {
        if (newLat != latitude) {
            latitude = newLat;
            UpdateData.updateRetailerField("lat", Double.toString(latitude), name, address);
        }
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double newLong) {
        if (newLong != longitude) {
            longitude = newLong;
            UpdateData.updateRetailerField("long", Double.toString(longitude), name, address);
        }
    }


    /**
     * Defines the hashCode function.
     * Allows for hashsets that don't collide on the same points
     * Collides if the other RetailLocation has same name and address
     *
     * @return A hash value
     */
    public int hashCode() {
        return new HashCodeBuilder(137, 337)
                .append(name)
                .append(address)
                .toHashCode();
    }

    /**
     * Defines the equals functions, allowing for easy equality testing
     * Fails if the other RetailLocation has same name and address
     *
     * @param obj The object to compare
     * @return Equality boolean
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof RetailLocation))
            return false;
        if (obj == this)
            return true;

        RetailLocation rhs = (RetailLocation) obj;
        return new EqualsBuilder()
                .append(name, rhs.getName())
                .append(address, rhs.getAddress())
                .isEquals();
    }
}