package dataManipulation;

import dataAnalysis.Route;

import java.util.ArrayList;

/**
 * Created by jes143 on 8/10/17.
 */
public interface AddRouteCallback {
    void addRoute(Route route);
    void addRoutes (ArrayList<Route> routes);
}
