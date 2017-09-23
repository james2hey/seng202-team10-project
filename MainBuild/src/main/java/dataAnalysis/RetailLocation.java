package dataAnalysis;

/**
 * Stores the data about a retail store.
 */

public class RetailLocation extends Location {
    private int zip;
    private String city, mainType, secondaryType;

    // Constructor
    public RetailLocation(String retailName, String retailStreet, String retailCity, String mType, String sType,
                          int zipNumber, double lat, double lon) {
        name = retailName;
        address = retailStreet;
        city = retailCity;
        mainType = mType;
        secondaryType = sType;
        zip = zipNumber;
        latitude = lat;
        longitude = lon;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getStreet() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getMainType() {return mainType;}

    public int getZip() {return zip;}

    public String getSecondaryType() {return secondaryType;}

    public String getAddress() {return address;}
}