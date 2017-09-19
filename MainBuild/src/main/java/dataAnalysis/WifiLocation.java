package dataAnalysis;

import com.sun.xml.internal.ws.api.ha.StickyFeature;

/**
 *  Provides a structure for wifi locations found in the database.
 */

//Users should be able to search for wifi locations based on search criteria. We need
//to find out what this search criteria is and query this.

public class WifiLocation extends Location {
    private int zip;
    private double wifiID;
    private String SSID, cost, provider, remarks, city, suburb;
    private Location location;


    //WifiLocation Constructor
    public WifiLocation(double id, double wifiLatitude, double wifiLongitude, String wifiAddress, String wifiSSID,
                        String wifiCost, String wifiProvier, String wifiRemarks, String wifiCity, String wifiSuburb,
                        int wifiZIP) {
        wifiID = id;
        location  = new StationLocation(1, wifiLatitude, wifiLongitude, wifiAddress);
        SSID = wifiSSID;
        cost = wifiCost;
        provider = wifiProvier;
        remarks = wifiRemarks;
        city = wifiCity;
        suburb = wifiSuburb;
        zip = wifiZIP;
    }


    //Getters
    public Location getLocation() {return location;}

    public String getName() {return name;}

    public String getAddress() {return location.getAddress();}

    public String getSSIF() {return SSID;}

    public double getWifiID() {return  wifiID;}




    /**Should query for a wifi location from given input. The database should be queried for this.
     */
    public void find() { //Will return Route, not void.
        }

}