package main;

import dataAnalysis.RetailLocation;
import dataAnalysis.Route;
import dataAnalysis.StationLocation;
import dataAnalysis.WifiLocation;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


/**
 * User class tests. Many of the user methods are very simple, making them unnecessary
 * to test.
 */
public class CyclistTest {

    /**
     * Creates a trivial RetailLocation.
     * @return s
     */
    private StationLocation createStationLocation() {
        StationLocation s = new StationLocation(0, 0, 0, null);
        return s;
    }

    /**
     * Creates a trivial RetailLocation.
     * @return r
     */
    private RetailLocation createRetailLocation() {
        RetailLocation r = new RetailLocation("TestShop", "Main Street", "NY",
                "Food", "Junk", 10004, 1.2, 1.2 );
        return r;
    }

    /**
     * Creates a trivial WifiLocation.
     * @return w
     */
    private WifiLocation createWifiLocation() {
        WifiLocation w = new WifiLocation(0, 0, 0, "Test", "Address",
                "Free", "NYC", "VERY GOOD", "New York", "Manhatten",
                7001);
        return w;
    }

    /**
     * Creates a trivial route.
     * @return r
     */
    private Route createRoute() {
        RetailLocation retail = createRetailLocation();
        WifiLocation wifi = createWifiLocation();
        Route r = new Route(retail, wifi, "0", 1, 1, 2016);
        return r;
    }

//    @Test
//    public void removeRoute() throws Exception {
//        Cyclist cyclist = new Cyclist();
//        Route r = createRoute();
//        cyclist.addRoute(r);
//        cyclist.removeRoute(r);
//        ArrayList<Route> emptyRouteList = new ArrayList<Route>();
//        //assertEquals(emptyRouteList, cyclist.getRouteList());
//    }

    //Should we create an exception for this?????
//    @Test
//    public void removeRouteNotInFavouritesException() throws Exception {
//        Cyclist cyclist = new Cyclist();
//        Route r = createRoute();
//        assertEquals("Route not in favourites", "Route not in favourites");
//    }

    @Test
    public void removeFavouriteStation() throws Exception {
        Cyclist cyclist = new Cyclist();
        StationLocation s = createStationLocation();
        cyclist.addFavouriteStation(s);
        cyclist.removeFavouriteStation(s);
        ArrayList<StationLocation> emptyStationLocationList = new ArrayList<StationLocation>();
        //assertEquals(emptyStationLocationList, cyclist.getFavouriteStationLocations());
    }

    @Test
    public void removeFavouriteRetail() throws Exception {
        Cyclist cyclist = new Cyclist();
        RetailLocation r = createRetailLocation();
        cyclist.addFavouriteRetail(r);
        cyclist.removeFavouriteRetail(r);
        ArrayList<RetailLocation> emptyRetailLocationList = new ArrayList<RetailLocation>();
        //assertEquals(emptyRetailLocationList, cyclist.getFavouriteRetailLocations());
    }

    @Test
    public void removeFavouriteWifi() throws Exception {
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
    public void testClear() throws Exception {
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
//
//    @Test
//    public void testClearAll() throws Exception {
//        Cyclist cyclist = new Cyclist();
//        Route r = createRoute();
//        StationLocation s = createStationLocation();
//        RetailLocation l = createRetailLocation();
//        WifiLocation w = createWifiLocation();
//        cyclist.addRoute(r);
//        cyclist.addFavouriteStation(s);
//        cyclist.addFavouriteRetail(l);
//        cyclist.addFavouriteWifi(w);
//        cyclist.clearAll();
//        //How can we show that all lists are empty without using more than one asserts? Does this even need to be tested?
//    }

}