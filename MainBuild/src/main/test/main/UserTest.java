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
class UserTest {

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
        User user = new User("Cyclist");
        Route r = createRoute();
        user.addRoute(r);
        user.removeRoute(r);
        ArrayList<Route> emptyRouteList = new ArrayList<Route>();
        assertEquals(emptyRouteList, user.getRouteList());
    }

    //Should we create an exception for this?????
    @Test
    public void removeRouteNotInFavouritesException() {
        User user = new User("Cyclist");
        Route r = createRoute();
        assertEquals("Route not in favourites", "Something");
    }

    @Test
    public void removeFavouriteStation() {
        User user = new User("Cyclist");
        StationLocation s = createStationLocation();
        user.addFavouriteStation(s);
        user.removeFavouriteStation(s);
        ArrayList<StationLocation> emptyStationLocationList = new ArrayList<StationLocation>();
        assertEquals(emptyStationLocationList, user.getFavouriteStationLocations());
    }

    @Test
    public void removeFavouriteRetail() {
        User user = new User("Cyclist");
        RetailLocation r = createRetailLocation();
        user.addFavouriteRetail(r);
        user.removeFavouriteRetail(r);
        ArrayList<RetailLocation> emptyRetailLocationList = new ArrayList<RetailLocation>();
        assertEquals(emptyRetailLocationList, user.getFavouriteRetailLocations());
    }

    @Test
    public void removeFavouriteWifi() {
        User user = new User("Cyclist");
        WifiLocation w = createWifiLocation();
        user.addFavouriteWifi(w);
        user.removeFavouriteWifi(w);
        ArrayList<WifiLocation> emptyWifiLocationList = new ArrayList<WifiLocation>();
        assertEquals(emptyWifiLocationList, user.getFavouriteWifiLocations());
    }

    /**
     * It only seems necessary to test for one type of list. It the above tests pass,
     * it makes sense that each boolean in the clear method would act the same way.
     */
    @Test
    public void testClear() {
        User user = new User("Cyclist");
        WifiLocation w1 = createWifiLocation();
        WifiLocation w2 = createWifiLocation();
        WifiLocation w3 = createWifiLocation();
        user.addFavouriteWifi(w1);
        user.addFavouriteWifi(w2);
        user.addFavouriteWifi(w3);
        user.clear("WIFI");
        ArrayList<WifiLocation> emptyWifiLocationList = new ArrayList<WifiLocation>();
        assertEquals(emptyWifiLocationList,user.getFavouriteWifiLocations());
    }

    @Test
    public void testClearAll() {
        User user = new User("Cyclist");
        Route r = createRoute();
        StationLocation s = createStationLocation();
        RetailLocation l = createRetailLocation();
        WifiLocation w = createWifiLocation();
        user.addRoute(r);
        user.addFavouriteStation(s);
        user.addFavouriteRetail(l);
        user.addFavouriteWifi(w);
        user.clearAll();
        //How can we show that all lists are empty without using more than one asserts? Does this even need to be tested?
    }



}