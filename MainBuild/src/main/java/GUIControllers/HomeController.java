package GUIControllers;


import dataAnalysis.RetailLocation;
import dataAnalysis.Route;
import dataAnalysis.WifiLocation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import main.HandleUsers;
import javafx.fxml.Initializable;
import main.Main;

import java.net.URL;
import java.util.ResourceBundle;

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

    private ObservableList<Route> routeList = FXCollections.observableArrayList();
    private ObservableList<WifiLocation> wifiList = FXCollections.observableArrayList();
    private ObservableList<RetailLocation> retailerList = FXCollections.observableArrayList();


    public void initialize(URL location, ResourceBundle resources) {
        //String username = HandleUsers.currentCyclist.getName();
        //welcomeText.setText("Welcome: " + username);

        routeList.addAll(Main.hu.currentCyclist.getFavouriteRouteList());
        wifiList.addAll(Main.hu.currentCyclist.getFavouriteWifiLocations());
        retailerList.addAll(Main.hu.currentCyclist.getFavouriteRetailLocations());

        StartAddress.setCellValueFactory(new PropertyValueFactory<>("StartAddress"));
        Rating.setCellValueFactory(new PropertyValueFactory<>("Rank"));
        tableViewRoutes.setItems(routeList);
        tableViewRoutes.getColumns().setAll(FavRoutes);

        SSID.setCellValueFactory(new PropertyValueFactory<>("SSID"));
        WifiAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        tableViewWifi.setItems(wifiList);
        tableViewWifi.getColumns().setAll(FavWifi);

        RetailerName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        RetailerAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        tableViewRetailers.setItems(retailerList);
        tableViewRetailers.getColumns().setAll(FavRetailers);


    }



}
