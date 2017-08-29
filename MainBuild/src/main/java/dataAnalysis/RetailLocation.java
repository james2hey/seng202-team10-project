package dataAnalysis;

/**
 * Provides a structure for retail locations found in the database.
 */

public class RetailLocation extends Location {
    private String street, city;

    //RetailLocation constructor
    public RetailLocation(String retailName, String retailStreet, String retailCity) {
        name = retailName;
        street = retailStreet;
        city = retailCity;
    }

    //Getters
    public String getName() {
        return name;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }


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