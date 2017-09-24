package GUIControllers.ViewDataControllers;

import GUIControllers.PlanRouteController;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import dataAnalysis.Route;
import dataAnalysis.WifiLocation;
import dataManipulation.DataFilterer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.CurrentStates;
import main.HandleUsers;
import main.Main;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;



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
    private ArrayList<WifiLocation> wifiLocations = new ArrayList<>();

    static private WifiLocation wifi = null;


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
    void displayDataOnMap(ActionEvent event) throws IOException {
        changeToPlanRouteScene(event, wifiLocations, null, null);
    }

    @FXML
    void displaySelectedDataOnMap(ActionEvent event) throws IOException {
        if (tableView.getSelectionModel().getSelectedItem() == null) {
            makeErrorDialogueBox("No Wifi Location selected.", "Please select one from the table.");
        } else {
            //Get it done...
            ArrayList<WifiLocation> wifiLocation = new ArrayList<>();
            wifiLocation.add(tableView.getSelectionModel().getSelectedItem());
            changeToPlanRouteScene(event, wifiLocation, null, null);
        }
    }

    @FXML
    void displayData(ActionEvent event) {
        System.out.println("Display button pressed");

        String provider = providerInput.getText();
        if (provider.equals("")) {
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
        wifiLocations = filterer.filterWifi(suburb, cost, provider);
        System.out.println("Got data");
        for (int i = 0; i < wifiLocations.size(); i++) {
            System.out.println(wifiLocations.get(i).getSSID());
        }

        tableView.getItems().clear();
        wifiList.addAll(wifiLocations);
    }

    /**
     * Adds the wifi location to the users favourites list if it is not already in their favourites, otherwise it
     * creates an error dialogue box telling them it has already been added to their favourites.
     */
    @FXML
    private void addFavouriteWifi() {
        if (tableView.getSelectionModel().getSelectedItem() == null) {
            makeSuccessDialogueBox("Select which wifi hotspot to add.", "");
        } else {
            String name = Main.hu.currentCyclist.getName();
            WifiLocation wifiToAdd = tableView.getSelectionModel().getSelectedItem();
                    boolean alreadyInList= Main.hu.currentCyclist.addFavouriteWifi(wifiToAdd, name);
            if (!alreadyInList) {
                makeSuccessDialogueBox(wifiToAdd.getProvider() + " successfully added.", "");
            } else {
                makeErrorDialogueBox(wifiToAdd.getProvider() + " already in favourites", "This wifi location has already been " +
                        "added\nto this users favourites list.");
            }
        }
    }

    @FXML
    void editData(ActionEvent event) throws IOException {
        //Called when view/edit wifi location button is pressed.
        if (tableView.getSelectionModel().getSelectedItem() == null) {
            makeErrorDialogueBox("No Wifi Location selected.", "Please select one from the table.");
        } else {
            wifi = tableView.getSelectionModel().getSelectedItem();
            Stage popup = new Stage();
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.initOwner(((Node) event.getSource()).getScene().getWindow());
            Parent popupParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/detailedWifiLocationInformation.fxml"));
            Scene popupScene = new Scene(popupParent);
            popup.setScene(popupScene);
            popup.show();
        }
    }

    static public WifiLocation getWifi() {
        return wifi;
    }


}
