package dataManipulation;

import dataAnalysis.Route;

import dataAnalysis.WifiLocation;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;




public class FindNearbyLocationsTest {

    private Route route;
    private ArrayList<WifiLocation> wifi;

    @Before
    public void setUp() throws Exception {
        wifi = new ArrayList<>();
        route = new Route(268, 40.757666, -73.985878, 3002,
                40.74854862, -73.98808416, "00:00:41", 1, 1, 2016);
    }


    @Test
    public void findNearbyWifiTest() throws Exception {
        wifi = FindNearbyLocations.findNearbyWifi(route);
        System.out.println(wifi.size());
        for (int j = 0; j < wifi.size(); j++){
            System.out.println(wifi.get(j).getSSIF());
        }

        assertTrue(1 == 1);
    }

}