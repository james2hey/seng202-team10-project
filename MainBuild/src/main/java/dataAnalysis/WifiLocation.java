package dataAnalysis;

/**
 *  Provides a structure for wifi locations found in the database.
 */

//Users should be able to search for wifi locations based on search criteria. We need
//to find out what this search criteria is and query this.

public class WifiLocation extends Location {
    private int zip;
    private double wifiID;
    private String SSID, cost, provider, remarks, city, suburb;


    //WifiLocation Constructor
    public WifiLocation(double id, double wifiLatitude, double wifiLongitude, String wifiAddress, String wifiSSID,
                        String wifiCost, String wifiProvier, String wifiRemarks, String wifiCity, String wifiSuburb,
                        int wifiZIP) {
        wifiID = id;
        latitude = wifiLatitude;
        longitude = wifiLongitude;
        address = wifiAddress;
        SSID = wifiSSID;
        cost = wifiCost;
        provider = wifiProvier;
        remarks = wifiRemarks;
        city = wifiCity;
        suburb = wifiSuburb;
        zip = wifiZIP;
    }


    //Getters
    public String getLocation() {return address;}

    public String getAddress() {return address;}

    public  String getProvider() {return provider;}

    public String getSuburb() {return suburb;}

    public String getCost() {return cost;}

    public String getSSID() {return SSID;}

    public double getWifiID() {return  wifiID;}

    public String getRemarks() {return remarks;}




    /**Should query for a wifi location from given input. The database should be queried for this.
     */
    public void find() { //Will return Route, not void.
        }

}