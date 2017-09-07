package main;

import dataAnalysis.RetailLocation;
import dataAnalysis.Route;
import dataAnalysis.StationLocation;
import dataAnalysis.WifiLocation;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * User class tests. Many of the user methods are very simple, making them unnecessary
 * to test.
 */
class CyclistTest {

    /**
     * Creates a trivial RetailLocation.
     * @return s
     */
    public StationLocation createStationLocation() {
        StationLocation s = new StationLocation(0, 0, 0);
        return s;
    }

    /**
     * Creates a trivial RetailLocation.
     * @return r
     */
    public RetailLocation createRetailLocation() {
        RetailLocation r = new RetailLocation("TestShop", "Main Street", "NY" );
        return r;
    }

    /**
     * Creates a trivial WifiLocation.
     * @return w
     */
    public WifiLocation createWifiLocation() {
        WifiLocation w = new WifiLocation(0, 0, "Test", "Address", "0000");
        return w;
    }

    /**
     * Creates a trivial route.
     * @return r
     */
    public Route createRoute() {
        RetailLocation retail = createRetailLocation();
        WifiLocation wifi = createWifiLocation();
        Route r = new Route(retail, wifi, 0, 0);
        return r;
    }

    @Test
    public void removeRoute() {
        Cyclist cyclist = new Cyclist();
        Route r = createRoute();
        cyclist.addRoute(r);
        cyclist.removeRoute(r);
        ArrayList<Route> emptyRouteList = new ArrayList<Route>();
        //assertEquals(emptyRouteList, cyclist.getRouteList());
    }

    //Should we create an exception for this?????
    @Test
    public void removeRouteNotInFavouritesException() {
        Cyclist cyclist = new Cyclist();
        Route r = createRoute();
        assertEquals("Route not in favourites", "Something");
    }

    @Test
    public void removeFavouriteStation() {
        Cyclist cyclist = new Cyclist();
        StationLocation s = createStationLocation();
        cyclist.addFavouriteStation(s);
        cyclist.removeFavouriteStation(s);
        ArrayList<StationLocation> emptyStationLocationList = new ArrayList<StationLocation>();
        //assertEquals(emptyStationLocationList, cyclist.getFavouriteStationLocations());
    }

    @Test
    public void removeFavouriteRetail() {
        Cyclist cyclist = new Cyclist();
        RetailLocation r = createRetailLocation();
        cyclist.addFavouriteRetail(r);
        cyclist.removeFavouriteRetail(r);
        ArrayList<RetailLocation> emptyRetailLocationList = new ArrayList<RetailLocation>();
        //assertEquals(emptyRetailLocationList, cyclist.getFavouriteRetailLocations());
    }

    @Test
    public void removeFavouriteWifi() {
        Cyclist cyclist = new Cyclist();
        WifiLocation w = createWifiLocation();
        cyclist.addFavouriteWifi(w);
        cyclist.removeFavouriteWifi(w);
        ArrayList<WifiLocation> emptyWifiLocationList = new ArrayList<WifiLocation>();
        //assertEquals(emptyWifiLocationList, cyclist.getFavouriteWifiLocations());
    }

    /**
     * It only seems necessary to test for one type of list. It the above tests pass,
     * it makes sense that each boolean in the clear method would act the same way.
     */
    @Test
    public void testClear() {
        Cyclist cyclist = new Cyclist();
        WifiLocation w1 = createWifiLocation();
        WifiLocation w2 = createWifiLocation();
        WifiLocation w3 = createWifiLocation();
        cyclist.addFavouriteWifi(w1);
        cyclist.addFavouriteWifi(w2);
        cyclist.addFavouriteWifi(w3);
        cyclist.clear("WIFI");
        ArrayList<WifiLocation> emptyWifiLocationList = new ArrayList<WifiLocation>();
        //assertEquals(emptyWifiLocationList,cyclist.getFavouriteWifiLocations());
    }

    @Test
    public void testClearAll() {
        Cyclist cyclist = new Cyclist();
        Route r = createRoute();
        StationLocation s = createStationLocation();
        RetailLocation l = createRetailLocation();
        WifiLocation w = createWifiLocation();
        cyclist.addRoute(r);
        cyclist.addFavouriteStation(s);
        cyclist.addFavouriteRetail(l);
        cyclist.addFavouriteWifi(w);
        cyclist.clearAll();
        //How can we show that all lists are empty without using more than one asserts? Does this even need to be tested?
    }



}