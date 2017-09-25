package main;

import dataAnalysis.RetailLocation;
import dataAnalysis.Route;
import dataAnalysis.WifiLocation;
import dataHandler.FavouriteRetailData;
import dataHandler.FavouriteRouteData;
import dataHandler.FavouriteWifiData;
import dataHandler.SQLiteDB;

import java.util.ArrayList;

import static org.apache.commons.text.WordUtils.capitalizeFully;

/**
 * The user of the program gets an instance of this is created for each user. it contains all the users
 * favourite routes, stations, retail stores and wifi locations.
 */
public class Cyclist {
    public ArrayList<Route> favouriteRouteList = new ArrayList<Route>();
    private ArrayList<RetailLocation> favouriteRetailLocations = new ArrayList<RetailLocation>();
    private ArrayList<WifiLocation> favouriteWifiLocations = new ArrayList<WifiLocation>();

    public String name;

    public Cyclist() {
    }

    public Cyclist(String cName) {
        name = capitalizeFully(cName);
    }

    //Getters

    public ArrayList<WifiLocation> getFavouriteWifiLocations() {
        return favouriteWifiLocations;
    }

    public ArrayList<RetailLocation> getFavouriteRetailLocations() {
        return favouriteRetailLocations;
    }

    public ArrayList<Route> getFavouriteRouteList() {
        return favouriteRouteList;
    }

    public String getName() {
        return name;
    }


    /**
     * Adds a route to the cyclists favouriteRoute list.
     * @param route the route to be added
     */
    public void addRouteInstance(Route route) {
        favouriteRouteList.add(route);
    }


    /**
     * Adds a wifi location to the cyclists favouriteWifiLocation list.
     * @param wifi the wifi location to be added
     */
    public void addWifiInstance(WifiLocation wifi) {
        favouriteWifiLocations.add(wifi);
    }


    /**
     * Adds a retail store to the cyclists favouriteRetailLocation list.
     * @param retail
     */
    public void addRetailInstance(RetailLocation retail) {
        favouriteRetailLocations.add(retail);
    }


    /**
     * Updates the users favourite routes. This will set the rank variable for the new Route object that has been added.
     */
    public void updateUserRouteFavourites(HandleUsers hu) {
        favouriteRouteList.clear();
        hu.getUserRouteFavourites();
    }


    /**
     * Adds a Route to the Users routeList if it is not already in it.
     * @param route the route to be added
     * @param name the username of whose favourite route this is
     * @param rank the rank score which the user gives. If none is given it is set to 0
     * @param db the database's favourite_route table that is to have the row added.
     */
    public void addRoute(Route route, String name, int rank, SQLiteDB db) {
        favouriteRouteList.add(route);
        FavouriteRouteData f = new FavouriteRouteData(db);
        f.addFavouriteRoute(name, route.getStartYear(), route.getStartMonth(), route.getStartDay(),
                route.getStartTime(), route.getBikeID(), rank, Main.hu);
    }


    /**
     * Checks to see if a route is already in the cyclists favouriteRoute list.
     * @param route the route to be checked if it is already in the list
     * @return true if it is already in the list, otherwise false
     */
    public boolean routeAlreadyInList(Route route) {
        boolean alreadyInList = false;
        for (int i = 0; i < favouriteRouteList.size(); i++) {
            Route tempRoute = favouriteRouteList.get(i);
            if (route.getStartYear().equals(tempRoute.getStartYear()) && route.getStartMonth().equals(tempRoute.getStartMonth()) &&
                    route.getStartDay().equals(tempRoute.getStartDay()) && route.getStartTime().equals(tempRoute.getStartTime()) &&
                    route.getBikeID().equals(tempRoute.getBikeID())) {
                alreadyInList = true;
                break;
            }
        }
        return alreadyInList;
    }


    /**
     * Adds a RetailLocation to the Users favouriteStationLocations.
     *
     * @param retail Retail to be added
     * @param name   name of the user
     * @return true if the Retail is already in the favouriteRetail list, false otherwise
     */
    public boolean addFavouriteRetail(RetailLocation retail, String name, SQLiteDB db) {
        boolean alreadyInList = false;
        for (int i = 0; i < favouriteRetailLocations.size(); i++) {
            RetailLocation tempRetail = favouriteRetailLocations.get(i);
            if (retail.getName().equals(tempRetail.getName()) && retail.getAddress().equals(tempRetail.getAddress())) {
                alreadyInList = true;
                break;
            }
        }
        if (!alreadyInList) {
            favouriteRetailLocations.add(retail);
            FavouriteRetailData f = new FavouriteRetailData(db);
            f.addFavouriteRetail(name, retail.getName(), retail.getAddress());
        }
        return alreadyInList;
    }


    /**
     * Adds a WifiLocation to the Users favouriteWifiLocations.
     *
     * @param wifi wifi object to be added to favouriteWifi
     * @param name name of the user
     * @return true if the Wifi is already in the favouriteWifi, false otherwise
     */
    public boolean addFavouriteWifi(WifiLocation wifi, String name, SQLiteDB db) {
        boolean alreadyInList = false;
        for (int i = 0; i < favouriteWifiLocations.size(); i++) {
            WifiLocation tempWifi = favouriteWifiLocations.get(i);
            if (wifi.getWifiID() == tempWifi.getWifiID()) {
                alreadyInList = true;
                break;
            }
        }
        if (!alreadyInList) {
            favouriteWifiLocations.add(wifi);
            FavouriteWifiData f = new FavouriteWifiData(db);
            f.addFavouriteWifi(name, wifi.getWifiID());
        }
        return alreadyInList;
    }
}