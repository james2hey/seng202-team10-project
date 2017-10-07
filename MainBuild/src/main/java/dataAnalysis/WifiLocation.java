package dataAnalysis;

import dataManipulation.UpdateData;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Stores the data about a wifi hotspot.
 */


public class WifiLocation extends Location {
    private int zip;
    private String SSID, cost, provider, remarks, city, suburb, wifiID, listName;


    // Constructor
    public WifiLocation(String id, double wifiLatitude, double wifiLongitude, String wifiAddress, String wifiSSID,
                        String wifiCost, String wifiProvider, String wifiRemarks, String wifiCity, String wifiSuburb,
                        int wifiZIP, String list) {
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
        listName = list;
    }

    // Getters
    public int getZip() {
        return zip;
    }

    // Setters
    public void setZip(int newZip) {
        if (newZip != zip) {
            zip = newZip;
            UpdateData.updateWifiField("zip", Integer.toString(zip), wifiID);
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String newAddress) {
        if (!newAddress.equals(address)) {
            address = newAddress;
            UpdateData.updateWifiField("address", address, wifiID);
        }
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String newProvider) {
        if (!newProvider.equals(provider)) {
            provider = newProvider;
            UpdateData.updateWifiField("provider", provider, wifiID);
        }
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String newSuburb) {
        if (!newSuburb.equals(suburb)) {
            suburb = newSuburb;
            UpdateData.updateWifiField("suburb", suburb, wifiID);
        }
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String newCost) {
        if (!newCost.equals(cost)) {
            cost = newCost;
            UpdateData.updateWifiField("cost", cost, wifiID);
        }
    }

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String newSSID) {
        if (!newSSID.equals(SSID)) {
            SSID = newSSID;
            UpdateData.updateWifiField("ssid", SSID, wifiID);
        }
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String newRemark) {
        if (!newRemark.equals(remarks)) {
            remarks = newRemark;
            UpdateData.updateWifiField("remarks", remarks, wifiID);
        }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String newCity) {
        if (!newCity.equals(city)) {
            city = newCity;
            UpdateData.updateWifiField("city", city, wifiID);
        }
    }

    public String getWifiID() {
        return wifiID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double newLat) {
        if (newLat != latitude) {
            latitude = newLat;
            UpdateData.updateWifiField("lat", Double.toString(latitude), wifiID);
        }
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double newLong) {
        if (newLong != longitude) {
            longitude = newLong;
            UpdateData.updateWifiField("lon", Double.toString(longitude), wifiID);
        }
    }


    public String getListName() {return listName;}

    public void setListName(String newList) {
        if (!newList.equals(listName)) {
            listName = newList;
            UpdateData.updateWifiField("list_name", newList, wifiID);
        }
    }

    /**
     * Defines the hashCode function.
     * Allows for hashsets that don't collide on the same points
     * Collides if the other WifiLocation has same wifiID
     *
     * @return A hash value
     */
    public int hashCode() {
        return new HashCodeBuilder(137, 337)
                .append(wifiID)
                .toHashCode();
    }

    /**
     * Defines the equals functions, allowing for easy equality testing
     * Fails if the other WifiLocation has same wifiID
     *
     * @param obj The object to compare
     * @return Equality boolean
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof WifiLocation))
            return false;
        if (obj == this)
            return true;

        WifiLocation rhs = (WifiLocation) obj;
        return new EqualsBuilder()
                .append(wifiID, rhs.getWifiID())
                .isEquals();
    }
}