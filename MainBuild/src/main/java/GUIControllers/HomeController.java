package GUIControllers;


import dataAnalysis.RetailLocation;
import dataAnalysis.Route;
import dataAnalysis.WifiLocation;
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
        tableViewRoutesSelectionListener();
        tableViewWifiSelectionListener();
        tableViewRetailerSelectionListener();


//        ObjectProperty<TableRow<Route>> lastSelectedRow = new SimpleObjectProperty<>();
//
//        tableViewRoutes.setRowFactory(tableView -> {
//            TableRow<Route> row = new TableRow<Route>();
//
//            row.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
//                if (isNowSelected) {
//                    lastSelectedRow.set(row);
//                }
//            });
//            return row;
//        });
//
//
//        getCurrentStage().getScene().addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//
//            @Override
//            public void handle(MouseEvent event) {
//                if (lastSelectedRow.get() != null) {
//                    Bounds boundsOfSelectedRow = lastSelectedRow.get().localToScene(lastSelectedRow.get().getLayoutBounds());
//                    if (boundsOfSelectedRow.contains(event.getSceneX(), event.getSceneY()) == false) {
//                        tableViewRoutes.getSelectionModel().clearSelection();
//                    }
//                }
//            }
//        });



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


    @FXML
    public void deleteFav(ActionEvent event) throws IOException {
        if (tableViewRoutes.getSelectionModel().getSelectedItem() != null) {


        } else if (tableViewWifi.getSelectionModel().getSelectedItem() != null){


        } else if (tableViewRetailers.getSelectionModel().getSelectedItem() != null) {


        }
    }



    private void tableViewRoutesSelectionListener() {
        tableViewRoutes.setRowFactory(new Callback<TableView<Route>, TableRow<Route>>() {
            @Override
            public TableRow<Route> call(TableView<Route> tableView2) {
                final TableRow<Route> row = new TableRow<>();
                row.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        final int index = row.getIndex();
                        if (index >= 0 && index < tableViewRoutes.getItems().size() && tableViewRoutes.getSelectionModel().isSelected(index)  ) {
                            tableViewRoutes.getSelectionModel().clearSelection();
                            event.consume();
                        }
                    }
                });
                return row;
            }
        });
    }



    private void tableViewWifiSelectionListener() {
        tableViewWifi.setRowFactory(new Callback<TableView<WifiLocation>, TableRow<WifiLocation>>() {
            @Override
            public TableRow<WifiLocation> call(TableView<WifiLocation> tableView2) {
                final TableRow<WifiLocation> row = new TableRow<>();
                row.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        final int index = row.getIndex();
                        if (index >= 0 && index < tableViewWifi.getItems().size() && tableViewWifi.getSelectionModel().isSelected(index)  ) {
                            tableViewWifi.getSelectionModel().clearSelection();
                            event.consume();
                        }
                    }
                });
                return row;
            }
        });
    }



    private void tableViewRetailerSelectionListener() {
        tableViewRetailers.setRowFactory(new Callback<TableView<RetailLocation>, TableRow<RetailLocation>>() {
            @Override
            public TableRow<RetailLocation> call(TableView<RetailLocation> tableView2) {
                final TableRow<RetailLocation> row = new TableRow<>();
                row.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        final int index = row.getIndex();
                        if (index >= 0 && index < tableViewRetailers.getItems().size() && tableViewRetailers.getSelectionModel().isSelected(index)  ) {
                            tableViewRetailers.getSelectionModel().clearSelection();
                            event.consume();
                        }
                    }
                });
                return row;
            }
        });
    }

 }
