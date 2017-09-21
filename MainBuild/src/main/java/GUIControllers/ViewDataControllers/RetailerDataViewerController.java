package GUIControllers.ViewDataControllers;

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
    private TableColumn<RetailLocation, String> PrimaryType;


    private ObservableList<RetailLocation> retailList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        Address.setCellValueFactory(new PropertyValueFactory<>("Street"));
        PrimaryType.setCellValueFactory(new PropertyValueFactory<>("MainType"));
        tableView.setItems(retailList);
        tableView.getColumns().setAll(Name, Address, PrimaryType);
    }

    @FXML
    void displayDataOnMap(ActionEvent event) {

    }

    @FXML
    void displayData(ActionEvent event) {
        System.out.println("Display button pressed");

//        String provider = providerInput.getText();
//        if (provider.equals("Company Name") || provider.equals("")) {
//            provider = null;
//        }
//        String suburb = boroughInput.getSelectionModel().getSelectedItem();
//        if (suburb == null || suburb.equals("No Selection")) {
//            suburb = null;
//        }
//        String cost = typeInput.getSelectionModel().getSelectedItem();
//        if (cost == null || cost.equals("No Selection")) {
//            cost = null;
//        }
//        DataFilterer filterer = new DataFilterer(Main.getDB());
//        ArrayList<WifiLocation> wifiLocations = filterer.filterWifi(suburb, cost, provider);
//        System.out.println("Got data");
//        for (int i = 0; i < wifiLocations.size(); i++) {
//            System.out.println(wifiLocations.get(i).getSSIF());
//        }
//        tableView.getItems().clear();
//        wifiList.addAll(wifiLocations);
    }

}
