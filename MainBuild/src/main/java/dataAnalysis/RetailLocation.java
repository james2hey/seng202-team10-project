package dataAnalysis;

import dataManipulation.UpdateData;

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
    public int getZip() {return zip;}

    public String getName() {
        return name;
    }

    public String getAddress() {return address;}

    public String getCity() {
        return city;
    }

    public String getState() {return state;}

    public String getMainType() {return mainType;}

    public String getSecondaryType() {return secondaryType;}

    public double getLatitude() {return latitude;}

    public double getLongitude() {return longitude;}


    // Setters
    public void setZip(int newZip) {
        if (newZip != zip) {
            zip = newZip;
            UpdateData.updateRetailerField("zip", Integer.toString(zip), name, address);
        }
    }

    public void setCity(String newCity) {
        if (!newCity.equals(city)) {
            city = newCity;
            UpdateData.updateRetailerField("city", city, name, address);
        }
    }

    public void setState(String newState) {
        if (!newState.equals(state)) {
            state = newState;
            UpdateData.updateRetailerField("state", state, name, address);
        }
    }

    public void setMainType(String newMainType) {
        if (!newMainType.equals(mainType)) {
            mainType = newMainType;
            UpdateData.updateRetailerField("main_type", mainType, name, address);
        }
    }

    public void setSecondaryType(String newSecondaryType) {
        if (!newSecondaryType.equals(secondaryType)) {
            secondaryType = newSecondaryType;
            UpdateData.updateRetailerField("secondary_type", secondaryType, name, address);
        }
    }

    public void setLatitude(double newLat) {
        if (newLat != latitude) {
            latitude = newLat;
            UpdateData.updateRetailerField("lat", Double.toString(latitude), name, address);
        }
    }

    public void setLongitude(double newLong) {
        if (newLong != longitude) {
            longitude = newLong;
            UpdateData.updateRetailerField("long", Double.toString(longitude), name, address);
        }
    }
}