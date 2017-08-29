package main;

import dataAnalysis.Route;
import dataAnalysis.StationLocation;
import dataAnalysis.RetailLocation;
import dataAnalysis.WifiLocation;

import java.util.ArrayList;

/**
 * Hard to say
 */
public class User {
    String userType;
    private ArrayList<Route> routeList = new ArrayList<>();
    private ArrayList<StationLocation> favouriteStationLocations = new ArrayList<>();
    private ArrayList<RetailLocation> favouriteRetailLocations = new ArrayList<>();
    private ArrayList<WifiLocation> favouriteWifiLocations = new ArrayList<>();

    /**Adds a route to the Users routeList.
     * @param route
     */
    public void addRoute(Route route) {
        routeList.add(route);
    }

    /**Adds a StationLocation to the Users favouriteStationLocations
     * @param station
     */
    public void addFavouriteStation(StationLocation station) {
        favouriteStationLocations.add(station);
    }

    /**Adds a RetailLocation to the Users favouriteStationLocations
     * @param retail
     */
    public void addFavouriteRetail(RetailLocation retail) {
        favouriteRetailLocations.add(retail);
    }

    /**Adds a WifiLocation to the Users favouriteStationLocations
     * @param wifi
     */
    public void addFavouriteWifi(WifiLocation wifi) {
        favouriteWifiLocations.add(wifi);
    }

    /**Removes a Route from the users routeList
     * @param route
     */
    public void removeRoute(Route route) {
        //Write tests first.
    }

    /**Removes a StationLocation from the users routeList
     * @param station
     */
    public void removeFavouriteStation(StationLocation station) {
        //Write tests first.
    }

    /**Removes a RetailLocation from the users routeList
     * @param retail
     */
    public void removeFavouriteRetail(RetailLocation retail) {

        //Write tests first.
    }

    /**Removes a WifiLocation from the users routeList
     * @param wifi
     */
    public void removeFavouriteWifi(StationLocation wifi) {
        //Write tests first.
    }

    public void clear(String type) {
        if(type == "ROUTE") {
            routeList.clear();
        }
        else if(type == "STATION") {
            routeList.clear();
        }
    }


}
