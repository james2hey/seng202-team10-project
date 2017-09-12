package main;

import dataAnalysis.Route;
import dataAnalysis.StationLocation;
import dataAnalysis.RetailLocation;
import dataAnalysis.WifiLocation;

import java.util.ArrayList;

/**
 * Superclass of Cyclist and Analyst, with its only attribute being a routeList.
 */
public abstract class User {
    public ArrayList<Route> routeList = new ArrayList<Route>();
    public String name;

}
