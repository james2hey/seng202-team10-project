package dataAnalysis;

/**
 *  Provides a structure for wifi locations found in the database.
 */

//Users should be able to search for wifi locations based on search criteria. We need
//to find out what this search criteria is and query this.

public class WifiLocation extends Location {
    private String location, address, SSIF;

    //Getters
    public String getLocation() {return location;}

    public String getName() {return name;}

    public String getAddress() {return address;}

    public String getSSIF() {return SSIF;}

    //WifiLocation Constructor
    public WifiLocation(int wifiLatitude, int wifiLongitude, String wifiName, String wifiAddress, String wifiSSIF) {
        latitude = wifiLatitude;
        longitude = wifiLongitude;
        name = wifiName;
        address = wifiAddress;
        SSIF = wifiSSIF;
    }


    /**Should query for a wifi location from given input. The database should be queried for this.
     */
    public void find() { //Will return Route, not void.
        }

}