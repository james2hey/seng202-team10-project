package dataManipulation;

import dataObjects.Route;

import java.util.ArrayList;


public interface AddRouteCallback {
    void addRoute(Route route);
    void addRoutes (ArrayList<Route> routes);
}
