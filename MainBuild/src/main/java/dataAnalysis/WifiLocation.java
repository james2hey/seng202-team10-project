package dataAnalysis;

/**
 *  Provides a structure for wifi locations found in the database.
 */

//TODO: Users should be able to search for wifi locations based on search criteria. We need
//      to find out what this search critera is and query this.

public class WifiLocation {
    private int latitude, longitude;
    private String location, name, address, SSIF;

    //WifiLocation Constructer
    public WifiLocation(int wifiLatitude, int wifiLogitude, String wifiName, String wifiAddress, String wifiSSIF) {
        latitude = wifiLatitude;
        longitude = wifiLogitude;
        name = wifiName;
        address = wifiAddress;
        SSIF = wifiSSIF;
    }

    /**Should queary for a wifi location from given input. The database should be queried for this.
     */
    public void find() { //Will return Route, not void.
    }


}