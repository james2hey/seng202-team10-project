package GUIControllers.ViewDataControllers;

import dataManipulation.DataFilterer;
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

import java.util.ArrayList;

/**
 * Created by bal65 on 19/09/17.
 */
public class RetailerDataViewerController extends DataViewerController {

    @FXML
    private JFXTextField streetInput;

    @FXML
    private JFXTextField primaryInput;

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
    public void initialize() {
        Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        Address.setCellValueFactory(new PropertyValueFactory<>("Street"));
        Zip.setCellValueFactory(new PropertyValueFactory<>("Zip"));
        PrimaryType.setCellValueFactory(new PropertyValueFactory<>("MainType"));
        tableView.setItems(retailList);
        tableView.getColumns().setAll(Name, Address, Zip, PrimaryType);
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
        if (zipInput.getText().equals("") || zipInput.getText().equals("Zip Code")) {
            zip = -1;
        } else {
            zip = Integer.valueOf(zipInput.getText());
        }
        String primaryType = primaryInput.getText();
        if (primaryType == null || primaryType.equals("Company Type")) {
            primaryType = null;
        }
        DataFilterer filterer = new DataFilterer(Main.getDB());
        ArrayList<RetailLocation> wifiLocations = filterer.filterRetailers(address, primaryType, zip);
        System.out.println("Got data");
        for (int i = 0; i < wifiLocations.size(); i++) {
            System.out.println(wifiLocations.get(i).getName());
        }
        tableView.getItems().clear();
        retailList.addAll(wifiLocations);
    }

}
