package GUIControllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.lynden.gmapsfx.ClusteredGoogleMapView;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;

import com.lynden.gmapsfx.service.directions.*;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import com.lynden.gmapsfx.shapes.Polyline;
import com.lynden.gmapsfx.shapes.PolylineOptions;
import com.lynden.gmapsfx.shapes.Rectangle;
import com.lynden.gmapsfx.util.MarkerImageFactory;
import dataAnalysis.RetailLocation;
import dataAnalysis.Route;
import dataAnalysis.WifiLocation;
import dataHandler.SQLiteDB;
import dataManipulation.FindNearbyLocations;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import main.Main;
import main.helperFunctions;
import netscape.javascript.JSObject;


import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class PlanRouteController extends Controller implements Initializable, MapComponentInitializedListener, DirectionsServiceCallback {

    private double STARTLAT = 40.745968;
    private double STARTLON = -73.994039;


    @FXML
    protected TextField startAddressField;

    @FXML
    protected TextField endAddressField;

    @FXML
    protected ClusteredGoogleMapView mapView;

    protected DirectionsService directionsService;

    protected DirectionsPane directionsPane;

    private GeocodingService geocodingService;

    private ClusteredGoogleMap map;

    private StringProperty startAddress = new SimpleStringProperty();
    private StringProperty endAddress = new SimpleStringProperty();

    private ArrayList<Marker> wifiMarkers = new ArrayList<Marker>();
    private ArrayList<Marker> retailerMarkers = new ArrayList<Marker>();
    private ArrayList<Marker> tripMarkers = new ArrayList<Marker>();
    private ArrayList<Polyline> tripLines = new ArrayList<Polyline>();
    private DecimalFormat numberFormat = new DecimalFormat("0.00");
    private FindNearbyLocations nearbyFinder;
    private LatLong currentPoint;



    private SQLiteDB db;

    @Override
    public void mapInitialized() {
        System.out.println("Init");
        geocodingService = new GeocodingService();
        MapOptions mapOptions = new MapOptions();

        currentPoint = new LatLong(STARTLAT, STARTLON);


        mapOptions.center(new LatLong(STARTLAT, STARTLON))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .mapTypeControl(false)
                .zoomControl(true)
                .zoom(12);

        map = mapView.createMap(mapOptions);
        directionsService = new DirectionsService();
        directionsPane = mapView.getDirec();

        clearMarkers();
        addWifiMarkers(Main.wifiLocations);
        addRetailerMarkers(Main.retailLocations);
        addTripMarkers(Main.routes);

//        db = Main.getDB();
//        try {
//            ResultSet rs = db.executeQuerySQL("SELECT * FROM retailer");
//
//            while (rs.next()) {
//                MarkerOptions markerOptions = new MarkerOptions();
//                LatLong latLong = new LatLong(rs.getDouble("LAT"), rs.getDouble("LONG"));
//                markerOptions.position(latLong).title(rs.getString("ADDRESS"));
//                Marker marker = new Marker(markerOptions);
//                map.addClusterableMarker(marker);
//            }
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Init");
        mapView.addMapInializedListener(this);
        System.out.println("Init2");
        startAddress.bindBidirectional(startAddressField.textProperty());
        endAddress.bindBidirectional(endAddressField.textProperty());
        nearbyFinder = new FindNearbyLocations(Main.getDB());
    }

    @FXML
    public void addressTextFieldAction(ActionEvent event) {
        DirectionsRequest request = new DirectionsRequest(startAddress.get(), endAddress.get(), TravelModes.DRIVING);
        directionsService.getRoute(request, this, new DirectionsRenderer(true, mapView.getMap(), directionsPane));
    }

    @Override
    public void directionsReceived(DirectionsResult results, DirectionStatus status) {
    }

    public void addWifiMarkers(ArrayList<WifiLocation> locations) {

        for (WifiLocation location : locations) {
            if (Main.wifiLocations.contains(location))
                continue;
            MarkerOptions options = new MarkerOptions();
            LatLong latLong = new LatLong(location.getLatitude(), location.getLongitude());
            options.position(latLong)
                    .title(location.getName())
                    .label("W")
                    .visible(true);
                    //.icon(getClass().getClassLoader().getResource("Images/greenPin.png").getFile());
            Marker marker = new Marker(options);
            wifiMarkers.add(marker);
            map.addClusterableMarker(marker);

            map.addUIEventHandler(marker, UIEventType.click, (JSObject obj) -> {
                System.out.println("Clicked");
                InfoWindowOptions infoWindowOptions = new InfoWindowOptions()
                        .content(
                                "SSID: " + location.getSSID() + "<br>" +
                                "Provider: " + location.getProvider() + "<br>" +
                                "Address: " + location.getAddress() + "<br>" +
                                "Extra Info: " + location.getRemarks())
                        .position(latLong);
                InfoWindow infoWindow = new InfoWindow(infoWindowOptions);
                infoWindow.open(map);
                currentPoint = latLong;
            });
        }
    }

    public void addRetailerMarkers(ArrayList<RetailLocation> locations) {

        for (RetailLocation location : locations) {
            if (Main.retailLocations.contains(location))
                continue;
            MarkerOptions options = new MarkerOptions();
            LatLong latLong = new LatLong(location.getLatitude(), location.getLongitude());
            options.position(latLong)
                    .title(location.getName())
                    .label("R")
                    .visible(true);
                    //.icon(getClass().getClassLoader().getResource("Images/redPin.png").getFile());
            Marker marker = new Marker(options);
            retailerMarkers.add(marker);
            map.addClusterableMarker(marker);
            System.out.println("Added");
            System.out.println(location.getLatitude());
            System.out.println(location.getLongitude());

            map.addUIEventHandler(marker, UIEventType.click, (JSObject obj) -> {
                System.out.println("Clicked");
                InfoWindowOptions infoWindowOptions = new InfoWindowOptions()
                        .content(
                                "Name: " + location.getName() + "<br>" +
                                "Address: " + location.getAddress() + "<br>" +
                                "Category: " + location.getMainType() + "<br>" +
                                "Extra Info: " + location.getSecondaryType())
                        .position(latLong);
                InfoWindow infoWindow = new InfoWindow(infoWindowOptions);
                infoWindow.open(map);
                currentPoint = latLong;
            });
        }
    }

    public void addTripMarkers(ArrayList<Route> routes) {
        for (Route route : routes) {
            if (Main.routes.contains(route))
                continue;
            MarkerOptions options = new MarkerOptions();
            System.out.println(route.getStartLatitude());
            System.out.println(route.getStartLongitude());
            LatLong latLong = new LatLong(route.getStartLatitude(), route.getStartLongitude());
            options.position(latLong)
                    .title(route.getName())
                    .label("S")
                    .visible(true);
            Marker marker = new Marker(options);

            tripMarkers.add(marker);
            map.addClusterableMarker(marker);
            map.addUIEventHandler(marker, UIEventType.click, (JSObject obj) -> {
                System.out.println("Clicked");
                InfoWindowOptions infoWindowOptions = new InfoWindowOptions()
                        .content(
                                "Start Address: " + route.getStartAddress() + "<br>" +
                                "Start Date: " + route.getStartDate() + "<br>" +
                                "Start Time: " + route.getStartTime() + "<br>" +
                                "Duration: " + helperFunctions.secondsToString(route.getDuration()) + "<br>" +
                                "Distance: " + numberFormat.format(route.getDistance()) + "km")
                        .position(latLong);
                InfoWindow infoWindow = new InfoWindow(infoWindowOptions);
                infoWindow.open(map);
                currentPoint = latLong;
            });

            MarkerOptions options2 = new MarkerOptions();
            LatLong latLong2 = new LatLong(route.getEndLatitude(), route.getEndLongitude());
            options2.position(latLong2)
                    .title(route.getName())
                    .label("E")
                    .visible(true);
                    //.icon(getClass().getClassLoader().getResource("Images/pin.png").getFile());
            Marker marker2 = new Marker(options2);

            tripMarkers.add(marker2);
            map.addClusterableMarker(marker2);

            map.addUIEventHandler(marker2, UIEventType.click, (JSObject obj) -> {
                System.out.println("Clicked");
                InfoWindowOptions infoWindowOptions = new InfoWindowOptions()
                        .content(
                                "End Address: " + route.getEndAddress() + "<br>" +
                                "End Date: " + route.getStopDate() + "<br>" +
                                "End Time: " + route.getStopTime() + "<br>" +
                                "Duration: " + helperFunctions.secondsToString(route.getDuration()) + "<br>" +
                                "Distance: " + numberFormat.format(route.getDistance()) + "km")
                        .position(latLong2);
                InfoWindow infoWindow = new InfoWindow(infoWindowOptions);
                infoWindow.open(map);
                currentPoint = latLong2;
            });

            LatLong[] ary = new LatLong[]{latLong, latLong2};
            MVCArray mvc = new MVCArray(ary);
            PolylineOptions polylineOptions = new PolylineOptions().path(mvc).strokeColor("red").strokeWeight(2);
            Polyline polyline = new Polyline(polylineOptions);

            tripLines.add(polyline);
            map.addMapShape(polyline);
        }
    }

    public void clearMarkers() {
        for (Marker marker : wifiMarkers) {
            map.removeClusterableMarker(marker);
        }
        wifiMarkers.clear();

        for (Marker marker : retailerMarkers) {
            map.removeClusterableMarker(marker);
        }
        retailerMarkers.clear();

        for (Marker marker : tripMarkers) {
            map.removeClusterableMarker(marker);
        }
        for (Polyline line : tripLines) {
            map.removeMapShape(line);
        }
        tripMarkers.clear();
        tripLines.clear();
    }

    @FXML
    public void showNearbyRetailers() {
        //Called by GUI when show nearby retails button is pressed.
        ArrayList<RetailLocation> retailLocations = nearbyFinder.findNearbyRetail(currentPoint.getLatitude(), currentPoint.getLongitude());
        addRetailerMarkers(retailLocations);

    }

    @FXML
    public void showNearbyWifi() {
        //Called by GUI when show nearby wifi button is pressed.
        ArrayList<WifiLocation> wifiLocations = nearbyFinder.findNearbyWifi(currentPoint.getLatitude(), currentPoint.getLongitude());
        addWifiMarkers(wifiLocations);
    }

    @FXML
    public void addRouteToDatabase() {
        //called by GUI when add current route to database button is pressed.

    }


}