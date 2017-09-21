package GUIControllers.ViewDataControllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import dataAnalysis.Route;
import dataAnalysis.WifiLocation;
import dataManipulation.DataFilterer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Main;

import java.util.ArrayList;


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
    public void initialize() {
        Name.setCellValueFactory(new PropertyValueFactory<>("SSIF"));
        Provider.setCellValueFactory(new PropertyValueFactory<>("Provider"));
        Address.setCellValueFactory(new PropertyValueFactory<>("Address"));
        Suburb.setCellValueFactory(new PropertyValueFactory<>("Suburb"));
        Cost.setCellValueFactory(new PropertyValueFactory<>("Cost"));
        tableView.setItems(wifiList);
        tableView.getColumns().setAll(Name, Address, Suburb, Cost);
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
            System.out.println(wifiLocations.get(i).getSSIF());
        }
        tableView.getItems().clear();
        wifiList.addAll(wifiLocations);

    }

}
