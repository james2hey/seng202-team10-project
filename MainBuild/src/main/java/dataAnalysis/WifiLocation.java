package dataAnalysis;

/**
 *  Provides a structure for wifi locations found in the database.
 */

//Users should be able to search for wifi locations based on search criteria. We need
//to find out what this search criteria is and query this.

public class WifiLocation extends Location {
    private double wifiID;
    private String address, SSID;
    private Location location;


    //WifiLocation Constructor
    public WifiLocation(double id, double wifiLatitude, double wifiLongitude, String wifiName, String wifiAddress, String wifiSSID) {
        wifiID = id;
        location  = new StationLocation(1, wifiLatitude, wifiLongitude);
        address = wifiAddress;
        SSID = wifiSSID;
    }


    //Getters
    public Location getLocation() {return location;}

    public String getName() {return name;}

    public String getAddress() {return address;}

    public String getSSIF() {return SSID;}

    public double getWifiID() {return  wifiID;}




    /**Should query for a wifi location from given input. The database should be queried for this.
     */
    public void find() { //Will return Route, not void.
        }

}