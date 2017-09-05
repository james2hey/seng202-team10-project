package main;

import dataAnalysis.Location;
import dataAnalysis.RetailLocation;
import dataAnalysis.Route;
import dataAnalysis.StationLocation;
import dataAnalysis.WifiLocation;

/**
 * Subclass of User, an instance of this is  created for each analyst using the
 * program to store all of their individual information. Analysts can also add
 * locations to the database.
 */
public class Analyst extends User {

    public Analyst() {}


    //_____________________________________________ADD LOCATIONS_____________________________________________
    /**
     * Adds the given StationLocation into the database.
     * @param stationLocation;
     */
    public void addStationLocation(StationLocation stationLocation) {}

    /**
     * Adds the given RetailLocation into the database.
     * @param retailLocation;
     */
    public void addRetailLocation(RetailLocation retailLocation) {}

    /**
     * Adds the given WifiLocation into the database.
     * @param wifiLocation;
     */

    public void addWifiLocation(WifiLocation wifiLocation) {}

    //_____________________________________________USE CASE METHODS_____________________________________________

    /**
     * Allows the analyst to view data which is already in the system.
     */
    public void viewData() {}

    /**
     * ???
     */
    public void processData() {}

    /**
     * Displays a graphical representation of specified specified data.
     */
    public void graphData() {}
}
