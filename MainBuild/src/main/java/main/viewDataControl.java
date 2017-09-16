package main;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;

import com.lynden.gmapsfx.service.directions.*;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import com.sun.org.apache.xml.internal.security.Init;
import dataAnalysis.Location;
import dataAnalysis.Route;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;


/**
 * Created by jes143 on 16/09/17.
 */
public class viewDataControl implements Initializable {

    @FXML
    private JFXDrawer drawer;

    @FXML
    private TableView<Route> tableView;

    @FXML
    private TableColumn<Route, String> StartLocation;

    @FXML
    private TableColumn<Route, String> EndLocation;

    @FXML
    private TableColumn<Route, String> Date;

    @FXML
    private TableColumn<Route, String> StartTime;

    @FXML
    private TableColumn<Route, String> EndTime;

    ObservableList<Route> rl = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        StartLocation.setCellValueFactory(new PropertyValueFactory<Route, String>("A"));
        EndLocation.setCellValueFactory(new PropertyValueFactory<Route, String>("B"));
        Date.setCellValueFactory(new PropertyValueFactory<Route, String>("C"));
        StartTime.setCellValueFactory(new PropertyValueFactory<Route, String>("D"));
        EndTime.setCellValueFactory(new PropertyValueFactory<Route, String>("E"));
        tableView.setItems(rl);
    }
    private List<Route> parseList() {
        List<Route> routes = new ArrayList<Route>();
        return routes;
    }

    @FXML
    void openDrawer() throws IOException {
        initializeSideDrawer();

        if (drawer.isShown()) {
            drawer.close();
        }
        else {
            drawer.open();
        }
    }

    //@Override
    public void initializeSideDrawer() throws IOException {
        VBox box = FXMLLoader.load(getClass().getClassLoader().getResource("SidePanel.fxml"));
        drawer.setSidePane(box);
    }

    @FXML
    void displayData(ActionEvent event) throws IOException {
        System.out.println("Display Data button pressed.");
    }

    @FXML
    public void login(ActionEvent actionEvent) {
        System.exit(0);
    }

}
