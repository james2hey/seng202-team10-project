package GUIControllers;


import dataAnalysis.RetailLocation;
import dataAnalysis.Route;
import dataAnalysis.WifiLocation;
import dataHandler.FavouriteRetailData;
import dataHandler.FavouriteRouteData;
import dataHandler.FavouriteWifiData;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
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
    private GridPane gridPane;

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

        tableViewRoutesSelectionListener();
        tableViewWifiSelectionListener();
        tableViewRetailerSelectionListener();
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


    @FXML
    public void deleteFavourite(ActionEvent event) throws IOException {
        if (tableViewRoutes.getSelectionModel().getSelectedItem() != null) {
            FavouriteRouteData frd = new FavouriteRouteData(Main.getDB());
            frd.deleteFavouriteRoute(tableViewRoutes.getSelectionModel().getSelectedItem());

        } else if (tableViewWifi.getSelectionModel().getSelectedItem() != null){
            FavouriteWifiData fwd = new FavouriteWifiData(Main.getDB());
            fwd.deleteFavouriteWifi(tableViewWifi.getSelectionModel().getSelectedItem());

        } else if (tableViewRetailers.getSelectionModel().getSelectedItem() != null) {
            FavouriteRetailData frd = new FavouriteRetailData(Main.getDB());
            frd.deleteFavouriteRetail(tableViewRetailers.getSelectionModel().getSelectedItem());

//            Main.hu.currentCyclist.deleteFavouriteRetail(tableViewRetailers.getSelectionModel().getSelectedItem());


        } else {
            makeErrorDialogueBox("No favourite selected", "No route was selected to delete." +
                    " You must\nchoose which favourite you want to delete.");
        }
    }


    /**
     * tableViewRoutesSelectionListener will deselect the selected cell in tableViewRoutes if the mouse is clicked
     * anywhere else.
     */
    private void tableViewRoutesSelectionListener() {
        ObjectProperty<TableRow<Route>> lastSelectedRow = new SimpleObjectProperty<>();
        tableViewRoutes.setRowFactory(tableView -> {
            TableRow<Route> row = new TableRow<Route>();

            row.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                if (isNowSelected) {
                    lastSelectedRow.set(row);
                }
            });
            return row;
        });

        GridPane stage = gridPane;
        stage.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (lastSelectedRow.get() != null) {
                    Bounds boundsOfSelectedRow = lastSelectedRow.get().localToScene(lastSelectedRow.get().getLayoutBounds());
                    if (boundsOfSelectedRow.contains(event.getSceneX(), event.getSceneY()) == false) {
                        tableViewRoutes.getSelectionModel().clearSelection();
                    }
                }
            }
        });
    }


    /**
     * tableViewRoutesSelectionListener will deselect the selected cell in tableViewWifi if the mouse is clicked
     * anywhere else.
     */
    private void tableViewWifiSelectionListener() {
        ObjectProperty<TableRow<WifiLocation>> lastSelectedRow = new SimpleObjectProperty<>();
        tableViewWifi.setRowFactory(tableView -> {
            TableRow<WifiLocation> row = new TableRow<WifiLocation>();

            row.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                if (isNowSelected) {
                    lastSelectedRow.set(row);
                }
            });
            return row;
        });

        GridPane stage = gridPane;
        stage.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (lastSelectedRow.get() != null) {
                    Bounds boundsOfSelectedRow = lastSelectedRow.get().localToScene(lastSelectedRow.get().getLayoutBounds());
                    if (boundsOfSelectedRow.contains(event.getSceneX(), event.getSceneY()) == false) {
                        tableViewWifi.getSelectionModel().clearSelection();
                    }
                }
            }
        });
    }


    /**
     * tableViewRoutesSelectionListener will deselect the selected cell in tableViewRetailers if the mouse is clicked
     * anywhere else.
     */
    private void tableViewRetailerSelectionListener() {
        ObjectProperty<TableRow<RetailLocation>> lastSelectedRow = new SimpleObjectProperty<>();
        tableViewRetailers.setRowFactory(tableView -> {
            TableRow<RetailLocation> row = new TableRow<RetailLocation>();

            row.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                if (isNowSelected) {
                    lastSelectedRow.set(row);
                }
            });
            return row;
        });

        GridPane stage = gridPane;
        stage.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (lastSelectedRow.get() != null) {
                    Bounds boundsOfSelectedRow = lastSelectedRow.get().localToScene(lastSelectedRow.get().getLayoutBounds());
                    if (boundsOfSelectedRow.contains(event.getSceneX(), event.getSceneY()) == false) {
                        tableViewRetailers.getSelectionModel().clearSelection();
                    }
                }
            }
        });
    }

 }
