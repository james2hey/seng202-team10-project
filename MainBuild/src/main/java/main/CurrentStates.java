package main;

import dataAnalysis.RetailLocation;
import dataAnalysis.Route;
import dataAnalysis.WifiLocation;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by jes143 on 23/09/17.
 */
public class CurrentStates {

//    public static ArrayList<WifiLocation> wifiLocations = new ArrayList<WifiLocation>();
//    public static ArrayList<RetailLocation> retailLocations = new ArrayList<RetailLocation>();
//    public static ArrayList<Route> routes = new ArrayList<Route>();

    private static HashSet<WifiLocation> wifiLocations = new HashSet<>();
    private static HashSet<RetailLocation> retailLocations = new HashSet<RetailLocation>();
    private static HashSet<Route> routes = new HashSet<Route>();

    public static void addWifiLocations(ArrayList<WifiLocation> newWifiLocations) {
        for (WifiLocation location : newWifiLocations) {
            wifiLocations.add(location);
        }
    }

    public static void clearWifiLocations() {
        wifiLocations.clear();
    }

    public static void addRetailLocations(ArrayList<RetailLocation> newRetailLocations) {
        for (RetailLocation location : newRetailLocations) {
            retailLocations.add(location);
        }
    }

    public static void clearRetailLocations() {
        retailLocations.clear();
    }

    public static void addRoutes(ArrayList<Route> newRoutes) {
        for (Route route : newRoutes) {
            routes.add(route);
        }
    }

    public static void clearRoutes() {
        routes.clear();
    }

    public static HashSet<WifiLocation> getWifiLocations() {
        return wifiLocations;
    }

    public static HashSet<RetailLocation> getRetailLocations() {
        return retailLocations;
    }

    public static HashSet<Route> getRoutes() {
        return routes;
    }
}
