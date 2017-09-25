//package main;
//
//import dataAnalysis.RetailLocation;
//import dataAnalysis.Route;
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
//
//public class CyclistTestDead {
//
//    private Cyclist testCyclist = new Cyclist("Tester");
//
//    private Route createTestRoute() {
//        RetailLocation start = new RetailLocation("t1Name", "t1Street","t1City", "t1Type",
//                "t1Type", 0, 0.0, 0.0);
//        RetailLocation end = new RetailLocation("t2Name", "t2Street","t2City", "t2Type",
//                "t2Type", 1, 100.0, 100.0);
//
//        Route route = new Route(start, end, "00:00:00", "01", "01", "2017");
//        return route;
//    }
//
//    @Test
//    public void updateUserRouteFavourites() throws Exception {
//
//    }
//
//    @Test
//    public void addRoute() throws Exception {
//        Route testRoute = createTestRoute();
//        testCyclist.addRoute(testRoute, "Test Route", 1);
//        //testCyclist.getFavouriteRouteList().get(-1);
//    }
//
//    @Test
//    public void routeAlreadyInListTrue() throws Exception {
//        Route testRoute = createTestRoute();
//        testCyclist.addRoute(testRoute, "Test Route", 1);
//        boolean testAnswer = testCyclist.routeAlreadyInList(testRoute);
//        assertEquals(true, testAnswer);
//    }
//
//    @Test
//    public void routeAlreadyInListFalse() throws Exception {
//        Route testRoute = createTestRoute();
//        boolean testAnswer = testCyclist.routeAlreadyInList(testRoute);
//        assertEquals(false, testAnswer);
//    }
//
//    @Test
//    public void addFavouriteRetail() throws Exception {
//
//    }
//
//    @Test
//    public void addFavouriteWifi() throws Exception {
//
//    }
//
//}