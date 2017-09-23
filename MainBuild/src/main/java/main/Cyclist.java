package main;

import dataAnalysis.RetailLocation;
import dataAnalysis.Route;
import dataAnalysis.StationLocation;
import dataAnalysis.WifiLocation;
import dataAnalysis.Location;
import dataHandler.FavouriteRetailData;
import dataHandler.FavouriteRouteData;
import dataHandler.FavouriteWifiData;

import java.util.ArrayList;

import static org.apache.commons.text.WordUtils.capitalizeFully;

/**
 * The user of the program gets an instance of this is created for each user. it contains all the users
 * favourite routes, stations, retail stores and wifi locations.
 */
public class Cyclist {
    public ArrayList<Route> favouriteRouteList = new ArrayList<Route>();
    private ArrayList<StationLocation> favouriteStationLocations = new ArrayList<StationLocation>();
    private ArrayList<RetailLocation> favouriteRetailLocations = new ArrayList<RetailLocation>();
    private ArrayList<WifiLocation>  favouriteWifiLocations = new ArrayList<WifiLocation>();

    public String name;

    public Cyclist() {}

    public Cyclist(String cName) {
        name = capitalizeFully(cName);
    }

    public ArrayList<WifiLocation> getFavouriteWifiLocations() {return favouriteWifiLocations;}
    public ArrayList<RetailLocation> getFavouriteRetailLocations() {return favouriteRetailLocations;}
    public ArrayList<Route> getFavouriteRouteList() {return favouriteRouteList;}

    //_____________________________________________ADD DATA_____________________________________________

    public void addRouteInstance(Route route) {favouriteRouteList.add(route);}
    public void addWifiInstance(WifiLocation wifi) {favouriteWifiLocations.add(wifi);}
    public void addRetailInstance(RetailLocation retail) {favouriteRetailLocations.add(retail);}
    public String getName() {return name;}


    /**
     * Updates the users favourite routes. This will set the Rank variable for the new Route object that has been added.
     */
    public void updateUserRouteFavourites() {
        favouriteRouteList.clear();
        HandleUsers.getUserRouteFavourites();
    }


    /**
     * Adds a Route to the Users routeList if it is not already in it.
     * @param route
     * @param name
     * @return
     */
    public void addRoute(Route route, String name, int rank) {
            favouriteRouteList.add(route);
            FavouriteRouteData.addFavouriteRoute(name, route.getStartYear(), route.getStartMonth(), route.getStartDay(),
                    route.getStartTime(), route.getBikeID(), rank);
    }

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
     * Adds a StationLocation to the Users favouriteStationLocations.
     * @param station;
     */
    public void addFavouriteStation(StationLocation station) {favouriteStationLocations.add(station);}


    /**
     * Adds a RetailLocation to the Users favouriteStationLocations.
     * @param retail Retail to be added
     * @param name name of the user
     * @return true if the Retail is already in the favouriteRetail list, false otherwise
     */
    public boolean addFavouriteRetail(RetailLocation retail, String name) {
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
            FavouriteRetailData.addFavouriteRetail(name, retail.getName(), retail.getAddress());
        }
        return alreadyInList;
    }


    /**
     * Adds a WifiLocation to the Users favouriteWifiLocations.
     * @param wifi wifi object to be added to favouriteWifi
     * @param name name of the user
     * @return true if the Wifi is already in the favouriteWifi, false otherwise
     */
    public boolean addFavouriteWifi(WifiLocation wifi, String name) {


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
            FavouriteWifiData.addFavouriteWifi(name, wifi.getWifiID());
        }
        return alreadyInList;
    }

    //_____________________________________________REMOVE DATA_____________________________________________
    /**
     * Removes a Route from the users routeList
     * @param route the Route to be removed
     */
    public void removeRoute(Route route) {
        boolean broken = false;
        for(int i=0; i < favouriteRouteList.size(); i++) {
            if(favouriteRouteList.get(i) == route) {
                favouriteRouteList.remove(i);
                broken = true;
                break;
            }
        }
        if(!broken) {System.out.println("Route not in favourites");} //WILL BE DIFFERENT WHEN GUI IS IMPLEMENTED
    }

    /**
     * Removes a Station from the users StationList.
     * @param station the Station to be removed
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

    /**
     * Removes a RetailLocation from the users favouriteRetailList.
     * @param retail RetailLocation to be removed
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

    /**
     * Removes a WifiLocation from the users favouriteWifiList.
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

    /**
     * Removes all elements from the list which is represented by
     * a String representing the type of Location/Route to clear.
     * @param type;
     */
    public void clear(String type) {
        if(type == "ROUTE") {
            favouriteRouteList.clear();
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
        favouriteRouteList.clear();
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
