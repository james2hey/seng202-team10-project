package GUIControllers.ViewDataControllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import dataAnalysis.RetailLocation;
import dataManipulation.DataFilterer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller class for retailer data viewer.
 */
public class RetailerDataViewerController extends DataViewerController {

    static private RetailLocation retailer = null;
    @FXML
    private JFXTextField streetInput;
    @FXML
    private ComboBox<String> primaryInput;
    @FXML
    private TableView<RetailLocation> tableView;
    @FXML
    private JFXTextField nameInput;
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
    private ArrayList<RetailLocation> retailLocations = new ArrayList<RetailLocation>();

    static public RetailLocation getRetailer() {
        return retailer;
    }


    /**
     * Runs on successfully loading up and fills the table with data currently stored in the database.
     * It then calls displayData() to visually display these.
     *
     * @param location  Location of the fxml
     * @param resources Locale-specific data required for the method to run automatically
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        Address.setCellValueFactory(new PropertyValueFactory<>("Address"));
        Zip.setCellValueFactory(new PropertyValueFactory<>("Zip"));
        PrimaryType.setCellValueFactory(new PropertyValueFactory<>("MainType"));
        tableView.setItems(retailList);
        tableView.getColumns().setAll(Name, Address, Zip, PrimaryType);

        ActionEvent event = new ActionEvent();
        try {
            displayData(event);
        } catch (Exception e) {
            System.out.println("Initialising data has failed.");
        }
    }

    /**
     * Called when the display all on map button is pressed. It changes the scene to the plan route one so that all data
     * can be seen on the map.
     *
     * @param event Created when the method is called
     * @throws IOException Handles errors caused by an fxml not loading correctly
     */
    @FXML
    void displayDataOnMap(ActionEvent event) throws IOException {
        //Called when GUI button View on map is pressed.
        changeToPlanRouteScene(event, null, retailLocations, null);
    }

    /**
     * Called when the display selected on map button is pressed. If nothing is selected, it will prompt the user,
     * otherwise it will get the selected item, place it on the map, and change the scene so it is visible.
     *
     * @param event Created when the method is called
     * @throws IOException Handles errors caused by an fxml not loading correctly
     */
    @FXML
    void displaySelectedDataOnMap(ActionEvent event) throws IOException {
        if (tableView.getSelectionModel().getSelectedItem() == null) {
            makeErrorDialogueBox("No retailer selected.", "Please select a retailer from the table.");
        } else {
            //Get it done.
            ArrayList<RetailLocation> retailLocation = new ArrayList<>();
            retailLocation.add(tableView.getSelectionModel().getSelectedItem());
            changeToPlanRouteScene(event, null, retailLocation, null);
        }
    }

    /**
     * Called when the filter button is pressed. It gets what the user has inputted into the filter text fields,
     * filters the data, and calls for a refresh of the scene so it can be seen.
     *
     * @param event Created when the method is called
     **/
    @FXML
    void displayData(ActionEvent event) {

        String name = nameInput.getText();
        if (name.equals("")) {
            name = null;
        }

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
        retailLocations = filterer.filterRetailers(name, address, primaryType, zip);
        System.out.println("Got data");
        for (RetailLocation retailLocation : retailLocations) {
            System.out.println(retailLocation.getName());
        }
        tableView.getItems().clear();
        retailList.addAll(retailLocations);
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
            boolean alreadyInList = Main.hu.currentCyclist.addFavouriteRetail(retailToAdd, name, Main.getDB());
            if (!alreadyInList) {
                makeSuccessDialogueBox(retailToAdd.getName() + " successfully added.", "");
            } else {
                makeErrorDialogueBox(retailToAdd.getName() + " already in favourites", "This retail store has already been " +
                        "added\nto this users favourites list.");
            }
        }
    }


    /**
     * Called when view/edit retailer is pressed. If nothing is selected, it will prompt the user. Otherwise,
     * it will open open the detailed view stage.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void editData(ActionEvent event) throws IOException {

        if (tableView.getSelectionModel().getSelectedItem() == null) {
            makeErrorDialogueBox("No retailer selected.", "Please select a retailer from the table.");
        } else {
            retailer = tableView.getSelectionModel().getSelectedItem();
            Stage popup = new Stage();
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.initOwner(((Node) event.getSource()).getScene().getWindow());
            Parent popupParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/DataViewerFXMLs/detailedRetailerInformation.fxml"));
            Scene popupScene = new Scene(popupParent);
            popup.setScene(popupScene);
            popup.show();
            DetailedRetailerInformation.setMainAppEvent(event);
        }
    }


}
