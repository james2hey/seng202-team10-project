package GUIControllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.lynden.gmapsfx.ClusteredGoogleMapView;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;

import com.lynden.gmapsfx.service.directions.*;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import dataHandler.SQLiteDB;
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


import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class PlanRouteController extends Controller implements Initializable, MapComponentInitializedListener, DirectionsServiceCallback {


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

    private SQLiteDB db;

    @Override
    public void mapInitialized() {
        System.out.println("Init");
        geocodingService = new GeocodingService();
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(-43.5235375, 172.5839233))
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


        MarkerOptions m1o = new MarkerOptions();
        LatLong m1l = new LatLong(-43.5305738, 172.601639);
        m1o.position(m1l)
                .title("My new Marker")
                .visible(true);
        Marker m1 = new Marker(m1o);

        MarkerOptions m2o = new MarkerOptions();
        LatLong m2l = new LatLong(-43.5355207, 172.5912535);
        m2o.position(m2l)
                .title("My new 2")
                .visible(true);
        Marker m2 = new Marker(m2o);

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


        map.addClusterableMarker(m1);
        map.addClusterableMarker(m2);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Init");
        mapView.addMapInializedListener(this);
        System.out.println("Init2");
        startAddress.bindBidirectional(startAddressField.textProperty());
        endAddress.bindBidirectional(endAddressField.textProperty());
    }

    @FXML
    public void addressTextFieldAction(ActionEvent event) {
        DirectionsRequest request = new DirectionsRequest(startAddress.get(), endAddress.get(), TravelModes.DRIVING);
        directionsService.getRoute(request, this, new DirectionsRenderer(true, mapView.getMap(), directionsPane));
    }

    @Override
    public void directionsReceived(DirectionsResult results, DirectionStatus status) {
    }

}
