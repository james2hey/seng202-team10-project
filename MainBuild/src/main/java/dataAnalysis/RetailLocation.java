package dataAnalysis;

/**
 * Provides a structure for retail locations found in the database.
 */

public class RetailLocation extends Location {
    private int zip;
    private String city, mainType, secondaryType;

    //RetailLocation constructor
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

    //Getters
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



    /**
     * Finds the latitude and longitude of the retails location from the given address.
     */
    public void findPosition(String address) {
        //calculate x and y;
        //latitude = x;
        //logitude = y;
    }

    /**Should queary for a retail location from given input. The database should be queried for this.
     */
    public void find() { //Will return a trip not void
    }

}