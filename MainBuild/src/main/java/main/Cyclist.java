package main;

import dataAnalysis.RetailLocation;
import dataAnalysis.Route;
import dataAnalysis.StationLocation;
import dataAnalysis.WifiLocation;
import dataAnalysis.Location;

import java.util.ArrayList;

/**
 * Subclass of User, an instance of this is  created for each cyclist using the
 * program to store all their key information about their favourite locations. Unlike
 * the Anaylst, the Cyclist also includes route data.
 */
public class Cyclist extends User {
    private ArrayList<StationLocation> favouriteStationLocations = new ArrayList<StationLocation>();
    private ArrayList<RetailLocation> favouriteRetailLocations = new ArrayList<RetailLocation>();
    private ArrayList<WifiLocation>  favouriteWifiLocations = new ArrayList<WifiLocation>();

    public Cyclist() {}
    //_____________________________________________ADD DATA_____________________________________________

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

    //_____________________________________________REMOVE DATA_____________________________________________
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
    //_____________________________________________CALCULATIONS_________________________________________________

    /**
     * Finds the routes that are used the most out of cyclists routeList.
     */
    public String findMostUsedRoutes() {
        String mostUsedRoutes = "";
//        for(int i = 0; i < routeList.size(); i++) {
//            if(routeList.get(i).getTimesTaken() > 5) { //5 arbitrary, to be changed to what?
//                mostUsedRoutes.concat(routeList.get(i).getName() + ", ");
//            }
//        }
        return mostUsedRoutes;
    }


    //_____________________________________________USE CASE METHODS_____________________________________________

    /**
     * Allows the cyclist to view data which is already in the system.
     */
    public void viewData() {}

    /**
     * The user searcher for a bike path and then sets that as their current
     * destination.
     */
    public void planRoute() {}

    /**
     * Searches for the location in the database.
     * @param location;
     */
    public void findLocation(Location location) {// Maybe this should be in a different class?}
    }

    /**
     * ???
     */
    public void proccessData() {}

    /**
     * Displays a graphical representation of specified specified data.
     */
    public void graphData() {}

    /**
     * Saves the Cyclists settings to the database.
     */
    public void storeData() {}



}
