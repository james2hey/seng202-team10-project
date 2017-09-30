package GUIControllers;


import dataAnalysis.RetailLocation;
import dataAnalysis.Route;
import dataAnalysis.WifiLocation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller class for home.
 */

public class HomeController extends Controller implements Initializable{

    @FXML
    private TableColumn<Route, String> FavRoutes;

    @FXML
    private TableColumn<Route, String> StartAddress;

    @FXML
    private TableColumn<Route, String> Rating;

    @FXML
    private TableColumn<WifiLocation, String> FavWifi;

    @FXML
    private TableColumn<WifiLocation, String> SSID;

    @FXML
    private TableColumn<WifiLocation, String> WifiAddress;

    @FXML
    private TableColumn<RetailLocation, String> FavRetailers;

    @FXML
    private TableColumn<RetailLocation, String> RetailerName;

    @FXML
    private TableColumn<RetailLocation, String> RetailerAddress;

    @FXML
    private TableView<Route> tableViewRoutes;

    @FXML
    private TableView<WifiLocation> tableViewWifi;

    @FXML
    private TableView<RetailLocation> tableViewRetailers;

    @FXML
    private Text welcomeText;

    private ArrayList<Route> routeList = new ArrayList<>();

    private ArrayList<WifiLocation> wifiList = new ArrayList<>();

    private ArrayList<RetailLocation> retailerList = new ArrayList<>();

    private ObservableList<Route> routeListObservable = FXCollections.observableArrayList();

    private ObservableList<WifiLocation> wifiListObservable = FXCollections.observableArrayList();

    private ObservableList<RetailLocation> retailerListObservable = FXCollections.observableArrayList();

    /**
     * Runs on successfully loading the fxml. Fills the favourites tables.
     * @param location Location of the fxml
     * @param resources Locale-specific data required for the method to run automatically
     */
    public void initialize(URL location, ResourceBundle resources) {
        String username = Main.hu.currentCyclist.getName();
        welcomeText.setText("Welcome: " + username);

        routeList.addAll(Main.hu.currentCyclist.getFavouriteRouteList());
        routeListObservable.addAll(routeList);
        wifiList.addAll(Main.hu.currentCyclist.getFavouriteWifiLocations());
        wifiListObservable.addAll(wifiList);
        retailerList.addAll(Main.hu.currentCyclist.getFavouriteRetailLocations());
        retailerListObservable.addAll(retailerList);

        StartAddress.setCellValueFactory(new PropertyValueFactory<>("StartAddress"));
        Rating.setCellValueFactory(new PropertyValueFactory<>("Rank"));
        tableViewRoutes.setItems(routeListObservable);
        tableViewRoutes.getColumns().setAll(FavRoutes);

        SSID.setCellValueFactory(new PropertyValueFactory<>("SSID"));
        WifiAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        tableViewWifi.setItems(wifiListObservable);
        tableViewWifi.getColumns().setAll(FavWifi);

        RetailerName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        RetailerAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        tableViewRetailers.setItems(retailerListObservable);
        tableViewRetailers.getColumns().setAll(FavRetailers);
    }


    /**
     * Called when View Favourites on Map on map button is pressed. Changes the scene to the plan route with all of the
     * favourites data ready to be loaded in.
     * @param event Created when the method is called
     * @throws IOException Handles errors caused by an fxml not loading correctly
     */
    @FXML
    void showFavourites(ActionEvent event) throws IOException {
        //called when GUI button view on map button is pressed.
        changeToPlanRouteScene(event, wifiList, retailerList, routeList);
    }
}
