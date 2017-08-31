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
    private String userType;
    private ArrayList<Route> routeList = new ArrayList<Route>();
    private ArrayList<StationLocation> favouriteStationLocations = new ArrayList<StationLocation>();
    private ArrayList<RetailLocation> favouriteRetailLocations = new ArrayList<RetailLocation>();
    private ArrayList<WifiLocation>  favouriteWifiLocations = new ArrayList<WifiLocation>();

    public User(String type) {
        userType = type;
    }

    public String getUserType() {return userType;}

    public ArrayList<Route> getRouteList() {return routeList;}

    public ArrayList<StationLocation> getFavouriteStationLocations() {return favouriteStationLocations;}

    public ArrayList<RetailLocation> getFavouriteRetailLocations() {return favouriteRetailLocations;}

    public ArrayList<WifiLocation> getFavouriteWifiLocations() {return favouriteWifiLocations;}

    //_____________________________________________ADD ELEMENTS_____________________________________________

    /**Adds a route to the Users routeList.
     * @param route;
     */
    public void addRoute(Route route) {routeList.add(route);}

    /**Adds a StationLocation to the Users favouriteStationLocations
     * @param station;
     */
    public void addFavouriteStation(StationLocation station) {favouriteStationLocations.add(station);}

    /**Adds a RetailLocation to the Users favouriteStationLocations
     * @param retail;
     */
    public void addFavouriteRetail(RetailLocation retail) {favouriteRetailLocations.add(retail);}

    /**Adds a WifiLocation to the Users favouriteStationLocations
     * @param wifi;
     */
    public void addFavouriteWifi(WifiLocation wifi) {favouriteWifiLocations.add(wifi);}

    //_____________________________________________REMOVE ELEMENTS_____________________________________________
    /**Removes a Route from the users routeList
     * @param route;
     */
    public void removeRoute(Route route) {
        boolean broken = false;
        for(int i=0; i < routeList.size(); i++) {
            if(routeList.get(i) == route) {
                routeList.remove(i);
                broken = true;
                break;
            }
        }
        if(!broken) {System.out.println("Not in list");} //WILL BE DIFFERENT WHEN GUI IS IMPLEMENTED
    }

    /**Removes a StationLocation from the users routeList
     * @param station;
     */
    public void removeFavouriteStation(StationLocation station) {
        boolean broken = false;
        for(int i=0; i < favouriteStationLocations.size(); i++) {
            if(favouriteStationLocations.get(i) == station) {
                favouriteStationLocations.remove(i);
                broken = true;
                break;
            }
        }
        if(!broken) {System.out.println("Not in list");} //WILL BE DIFFERENT WHEN GUI IS IMPLEMENTED
    }

    /**Removes a RetailLocation from the users routeList
     * @param retail;
     */
    public void removeFavouriteRetail(RetailLocation retail) {
        boolean broken = false;
        for(int i=0; i < favouriteRetailLocations.size(); i++) {
            if(favouriteRetailLocations.get(i) == retail) {
                favouriteRetailLocations.remove(i);
                broken = true;
                break;
            }
        }
        if(!broken) {System.out.println("Not in list");} //WILL BE DIFFERENT WHEN GUI IS IMPLEMENTED
    }

    /**Removes a WifiLocation from the users routeList
     * @param wifi;
     */
    public void removeFavouriteWifi(WifiLocation wifi) {
        boolean broken = false;
        for(int i=0; i < favouriteWifiLocations.size(); i++) {
            if(favouriteWifiLocations.get(i) == wifi) {
                favouriteWifiLocations.remove(i);
                broken = true;
                break;
            }
        }
        if(!broken) {System.out.println("Not in list");} //WILL BE DIFFERENT WHEN GUI IS IMPLEMENTED
    }

    /**Removes all elements from the list which is represented by
     * a String representing the type of Location/Route to clear.
     * @param type;
     */
    public void clear(String type) {
        if(type == "ROUTE") {
            routeList.clear();
        } else if(type == "STATION") {
            favouriteStationLocations.clear();
        } else if(type == "RETAIL") {
            favouriteRetailLocations.clear();
        } else if (type == "WIFI"){
            favouriteWifiLocations.clear();
        } else {
            System.out.println("Parameter must be a String - ROUTE/STATION/RETAIL/WIFI");
        }
    }

    /**Clears all of the Users ArrayLists.
     */
    public void clearAll() {
        routeList.clear();
        favouriteStationLocations.clear();
        favouriteRetailLocations.clear();
        favouriteWifiLocations.clear();
    }




}
