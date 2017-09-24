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
                        String wifiCost, String wifiProvider, String wifiRemarks, String wifiCity, String wifiSuburb,
                        int wifiZIP) {
        wifiID = id;
        latitude = wifiLatitude;
        longitude = wifiLongitude;
        address = wifiAddress;
        SSID = wifiSSID;
        cost = wifiCost;
        provider = wifiProvider;
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
            Double w = wifiID;
            Integer key = w.intValue();
            UpdateData.updateWifiField("zip", Integer.toString(zip), key);
        }
    }

    public void setAddress(String newAddress) {
        if (!newAddress.equals(address)) {
            address = newAddress;
            Double w = wifiID;
            Integer key = w.intValue();
            UpdateData.updateWifiField("address", address, key);
        }
    }

    public void setProvider(String newProvider) {
        if (!newProvider.equals(provider)) {
            provider = newProvider;
            Double w = wifiID;
            Integer key = w.intValue();
            UpdateData.updateWifiField("provider", provider, key);
        }
    }

    public void setSuburb(String newSuburb) {
        if (!newSuburb.equals(suburb)) {
            suburb = newSuburb;
            Double w = wifiID;
            Integer key = w.intValue();
            UpdateData.updateWifiField("suburb", suburb, key);
        }
    }

    public void setCost(String newCost) {
        if (!newCost.equals(cost)) {
            cost = newCost;
            Double w = wifiID;
            Integer key = w.intValue();
            UpdateData.updateWifiField("cost", cost, key);
        }
    }

    public void setSSID(String newSSID) {
        if (!newSSID.equals(SSID)) {
            SSID = newSSID;
            Double w = wifiID;
            Integer key = w.intValue();
            UpdateData.updateWifiField("ssid", SSID, key);
        }
    }

    public void setRemarks(String newRemark) {
        if (!newRemark.equals(remarks)) {
            remarks = newRemark;
            Double w = wifiID;
            Integer key = w.intValue();
            UpdateData.updateWifiField("remarks", remarks, key);
        }
    }

    public void setCity(String newCity) {
        if (!newCity.equals(city)) {
            city = newCity;
            Double w = wifiID;
            Integer key = w.intValue();
            UpdateData.updateWifiField("city", city, key);
        }
    }

    public void setLatitude(double newLat) {
        if (newLat != latitude) {
            latitude = newLat;
            Double w = wifiID;
            Integer key = w.intValue();
            UpdateData.updateWifiField("lat", Double.toString(latitude), key);
        }
    }

    public void setLongitude(double newLong) {
        if (newLong != longitude) {
            longitude = newLong;
            Double w = wifiID;
            Integer key = w.intValue();
            UpdateData.updateWifiField("lon", Double.toString(longitude), key);
        }
    }
}