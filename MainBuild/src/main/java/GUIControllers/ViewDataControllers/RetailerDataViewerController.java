package GUIControllers.ViewDataControllers;

import dataAnalysis.WifiLocation;
import dataManipulation.DataFilterer;
import javafx.scene.control.ComboBox;
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
    void displayDataOnMap(ActionEvent event) {

    }

    @FXML
    void displayData(ActionEvent event) {
        System.out.println("Display button pressed");

        String address = streetInput.getText();
        if (address == null || address.equals("Address")) {
            address = null;
        }
        int zip;
        try {
            if (zipInput.getText().equals("") || zipInput.getText().equals("Zip Code")) {
                zip = -1;
            } else {
                if (Integer.valueOf(zipInput.getText()) <= 0) {
                    throw new NumberFormatException("Please enter a number greater than 0");
                }
                zip = Integer.valueOf(zipInput.getText());
            }
        } catch (NumberFormatException e) {
            zipInput.setText("Enter a number greater than 0");
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
        Main.retailLocations = retailLocations;
    }

    @FXML
    private void addFavouriteRetail() {
        if (HandleUsers.currentAnalyst == null) {
            if (tableView.getSelectionModel().getSelectedItem() == null) {
                System.out.println("Select retail location to add!");
            } else {
                String name = HandleUsers.currentCyclist.getName();
                RetailLocation retailToAdd = tableView.getSelectionModel().getSelectedItem();
                HandleUsers.currentCyclist.addFavouriteRetail(retailToAdd, name);
                System.out.println("ADDED " + retailToAdd.getName() + " to cyclist favourites.");
            }
        } else {
            System.out.println("Feature not available for analyst!");
        }
    }

}
