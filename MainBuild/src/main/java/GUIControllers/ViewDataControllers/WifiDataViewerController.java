package GUIControllers.ViewDataControllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import dataAnalysis.WifiLocation;
import dataManipulation.DataFilterer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import main.HandleUsers;
import main.Main;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


/**
 * Created by bal65 on 19/09/17.
 */
public class WifiDataViewerController extends DataViewerController {

    @FXML
    private JFXTextField providerInput;

    @FXML
    private TableView<WifiLocation> tableView;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private ComboBox<String> boroughInput;

    @FXML
    private ComboBox<String> typeInput;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private TableColumn<WifiLocation, String> Name;

    @FXML
    private TableColumn<WifiLocation, String> Provider;

    @FXML
    private TableColumn<WifiLocation, String> Address;

    @FXML
    private TableColumn<WifiLocation, String> Suburb;

    @FXML
    private TableColumn<WifiLocation, String> Cost;

    private ObservableList<WifiLocation> wifiList = FXCollections.observableArrayList();


    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        Name.setCellValueFactory(new PropertyValueFactory<>("SSID"));
        Provider.setCellValueFactory(new PropertyValueFactory<>("Provider"));
        Address.setCellValueFactory(new PropertyValueFactory<>("Address"));
        Suburb.setCellValueFactory(new PropertyValueFactory<>("Suburb"));
        Cost.setCellValueFactory(new PropertyValueFactory<>("Cost"));
        tableView.setItems(wifiList);
        tableView.getColumns().setAll(Name, Provider, Address, Suburb, Cost);
        ActionEvent event = new ActionEvent();
        displayData(event);
    }


    @FXML
    void displayDataOnMap(ActionEvent event) {

    }

    @FXML
    void displayData(ActionEvent event) {
        System.out.println("Display button pressed");

        String provider = providerInput.getText();
        if (provider.equals("Company Name") || provider.equals("")) {
            provider = null;
        }
        String suburb = boroughInput.getSelectionModel().getSelectedItem();
        if (suburb == null || suburb.equals("No Selection")) {
            suburb = null;
        }
        String cost = typeInput.getSelectionModel().getSelectedItem();
        if (cost == null || cost.equals("No Selection")) {
            cost = null;
        }
        DataFilterer filterer = new DataFilterer(Main.getDB());
        ArrayList<WifiLocation> wifiLocations = filterer.filterWifi(suburb, cost, provider);
        System.out.println("Got data");
        for (int i = 0; i < wifiLocations.size(); i++) {
            System.out.println(wifiLocations.get(i).getSSID());
        }

        tableView.getItems().clear();
        wifiList.addAll(wifiLocations);
        Main.wifiLocations = wifiLocations;

    }

    /**
     * Adds the wifi location to the users favourites list if it is not already in their favourites, otherwise it
     * creates an error dialogue box telling them it has already been added to their favourites.
     */
    @FXML
    private void addFavouriteWifi() {
        if (HandleUsers.currentAnalyst == null) {
            if (tableView.getSelectionModel().getSelectedItem() == null) {
                System.out.println("Select Wifi location to add!");
            } else {
                String name = HandleUsers.currentCyclist.getName();
                WifiLocation wifiToAdd = tableView.getSelectionModel().getSelectedItem();
                boolean alreadyInList= HandleUsers.currentCyclist.addFavouriteWifi(wifiToAdd, name);
                if (!alreadyInList) {
                    makeSuccessDialogueBox(wifiToAdd.getWifiID() + " successfully added.", "");
                } else {
                    makeErrorDialogueBox(wifiToAdd.getWifiID() + " already in favourites", "This wifi location has already been " +
                            "added\nto this users favourites list.");
                }
            }
        } else {
            System.out.println("Feature not available for analyst!");
        }
    }

    @FXML
    void editData(ActionEvent event) {
    //Called when view/edit wifi location button is pressed.
    }


}
