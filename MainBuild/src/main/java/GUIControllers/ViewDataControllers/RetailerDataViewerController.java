package GUIControllers.ViewDataControllers;

import dataManipulation.DataFilterer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.CurrentStates;
import main.HandleUsers;
import main.Main;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import dataAnalysis.RetailLocation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by bal65 on 19/09/17.
 */
public class RetailerDataViewerController extends DataViewerController {

    @FXML
    private JFXTextField streetInput;

    @FXML
    private ComboBox<String> primaryInput;

    @FXML
    private TableView<RetailLocation> tableView;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXTextField zipInput;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private TableColumn<RetailLocation, String> Name;

    @FXML
    private TableColumn<RetailLocation, String> Address;

    @FXML
    private TableColumn<RetailLocation, Integer> Zip;

    @FXML
    private TableColumn<RetailLocation, String> PrimaryType;


    private ObservableList<RetailLocation> retailList = FXCollections.observableArrayList();

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        Address.setCellValueFactory(new PropertyValueFactory<>("Street"));
        Zip.setCellValueFactory(new PropertyValueFactory<>("Zip"));
        PrimaryType.setCellValueFactory(new PropertyValueFactory<>("MainType"));
        tableView.setItems(retailList);
        tableView.getColumns().setAll(Name, Address, Zip, PrimaryType);

        ActionEvent event = new ActionEvent();
        displayData(event);
    }

    @FXML
    void displayDataOnMap(ActionEvent event) throws IOException {
        //Called when GUI button View on map is pressed.
        changeToPlanRouteScene(event);
    }

    @FXML
    void displayData(ActionEvent event) {
        System.out.println("Display button pressed");

        String address = streetInput.getText();
        if (address.equals("")) {
            address = null;
        }

        int zip;
        try {
            if (zipInput.getText().equals("")) {
                zip = -1;
            } else {
                if (Integer.valueOf(zipInput.getText()) <= 0) {
                    throw new NumberFormatException();
                }
                if (Integer.valueOf(zipInput.getText()) >= 100000000) {
                    throw new NumberFormatException();
                }
                zip = Integer.valueOf(zipInput.getText());
            }
        } catch (NumberFormatException e) {
            makeErrorDialogueBox("Incorrect input for zip number", "Please enter a positive number between 1 and 8\ndigits long.");
            zip = -1;
        }

        String primaryType = primaryInput.getSelectionModel().getSelectedItem();
        if (primaryType == null || primaryType.equals("No Selection")) {
            primaryType = null;
        }
        DataFilterer filterer = new DataFilterer(Main.getDB());
        ArrayList<RetailLocation> retailLocations = filterer.filterRetailers(address, primaryType, zip);
        System.out.println("Got data");
        for (int i = 0; i < retailLocations.size(); i++) {
            System.out.println(retailLocations.get(i).getName());
        }
        tableView.getItems().clear();
        retailList.addAll(retailLocations);
        CurrentStates.clearRetailLocations();
        CurrentStates.addRetailLocations(retailLocations);
    }


    /**
     * Adds the retail store to the users favourites list if it is not already in their favourites, otherwise it
     * creates an error dialogue box telling them it has already been added to their favourites.
     */
    @FXML
    private void addFavouriteRetail() {
        if (tableView.getSelectionModel().getSelectedItem() == null) {
            makeSuccessDialogueBox("Select which retail store to add.", "");
        } else {
            String name = Main.hu.currentCyclist.getName();
            RetailLocation retailToAdd = tableView.getSelectionModel().getSelectedItem();
            boolean alreadyInList = Main.hu.currentCyclist.addFavouriteRetail(retailToAdd, name);
            if (!alreadyInList) {
                makeSuccessDialogueBox(retailToAdd.getName() + " successfully added.", "");
            } else {
                makeErrorDialogueBox(retailToAdd.getName() + " already in favourites", "This retail store has already been " +
                        "added\nto this users favourites list.");
                }
        }
    }

    static private RetailLocation retailer = null;

    @FXML
    void editData(ActionEvent event) throws IOException{
        //Called when view/edit retailer is pressed.

        if (tableView.getSelectionModel().getSelectedItem() == null) {
            makeErrorDialogueBox("No route selected.", "Please select a route from the table.");
        } else {
            retailer = tableView.getSelectionModel().getSelectedItem();
            Stage popup = new Stage();
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.initOwner(((Node) event.getSource()).getScene().getWindow());
            Parent popupParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/detailedRetailerInformation.fxml"));
            Scene popupScene = new Scene(popupParent);
            popup.setScene(popupScene);
            popup.show();

        }
    }

    static public RetailLocation getRetailer() {
        return retailer;
    }

}
