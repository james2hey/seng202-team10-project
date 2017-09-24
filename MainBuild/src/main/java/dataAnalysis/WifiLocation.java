package dataAnalysis;

import dataManipulation.UpdateData;

/**
 *  Stores the data about a wifi hotspot.
 */


public class WifiLocation extends Location {
    private int zip;
    private double wifiID;
    private String SSID, cost, provider, remarks, city, suburb;


    // Constructor
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

    // Getters
    public int getZip() {return zip;}

    public String getAddress() {return address;}

    public String getProvider() {return provider;}

    public String getSuburb() {return suburb;}

    public String getCost() {return cost;}

    public String getSSID() {return SSID;}

    public String getRemarks() {return remarks;}

    public String getCity() {return city;}

    public double getWifiID() {return  wifiID;}

    public double getLatitude() {return latitude;}

    public double getLongitude() {return longitude;}


    // Setters
    public void setZip(int newZip) {
        if (newZip != zip) {
            zip = newZip;
            UpdateData.updateWifiField("zip", Integer.toString(zip), Double.toString(wifiID));
        }
    }

    public void setAddress(String newAddress) {
        if (!newAddress.equals(address)) {
            address = newAddress;
            UpdateData.updateWifiField("address", address, Double.toString(wifiID));
        }
    }

    public void setProvider(String newProvider) {
        if (!newProvider.equals(provider)) {
            provider = newProvider;
            UpdateData.updateWifiField("provider", provider, Double.toString(wifiID));
        }
    }

    public void setSuburb(String newSuburb) {
        if (!newSuburb.equals(suburb)) {
            suburb = newSuburb;
            UpdateData.updateWifiField("suburb", suburb, Double.toString(wifiID));
        }
    }

    public void setCost(String newCost) {
        if (!newCost.equals(city)) {
            city = newCost;
            UpdateData.updateWifiField("cost", cost, Double.toString(wifiID));
        }
    }

    public void setSSID(String newSSID) {
        if (!newSSID.equals(SSID)) {
            SSID = newSSID;
            UpdateData.updateWifiField("ssid", SSID, Double.toString(wifiID));
        }
    }

    public void setRemarks(String newRemark) {
        if (!newRemark.equals(remarks)) {
            remarks = newRemark;
            UpdateData.updateWifiField("remarks", remarks, Double.toString(wifiID));
        }
    }

    public void setCity(String newCity) {
        if (!newCity.equals(city)) {
            city = newCity;
            UpdateData.updateWifiField("city", city, Double.toString(wifiID));
        }
    }

    public void setLatitude(double newLat) {
        if (newLat != latitude) {
            latitude = newLat;
            UpdateData.updateWifiField("lat", Double.toString(latitude), Double.toString(wifiID));
        }
    }

    public void setLongitude(double newLong) {
        if (newLong != longitude) {
            longitude = newLong;
            UpdateData.updateWifiField("lon", Double.toString(longitude), Double.toString(wifiID));
        }
    }
}