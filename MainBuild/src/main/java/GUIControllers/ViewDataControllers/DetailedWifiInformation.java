package GUIControllers.ViewDataControllers;

import com.jfoenix.controls.JFXTextField;
import dataAnalysis.WifiLocation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for detailed wifi information.
 */
public class DetailedWifiInformation extends DataViewerController {

    @FXML
    private JFXTextField Zip;

    @FXML
    private JFXTextField address;

    @FXML
    private ComboBox<String> cost;

    @FXML
    private JFXTextField provider;

    @FXML
    private JFXTextField city;

    @FXML
    private JFXTextField wifiID;

    @FXML
    private JFXTextField longitude;

    @FXML
    private ComboBox<String> suburb;

    @FXML
    private JFXTextField lattitude;

    @FXML
    private JFXTextField SSID;

    @FXML
    private JFXTextField remarks;

    private WifiLocation currentWifi = null;

    static private ActionEvent mainAppEvent = null;

    static public void setMainAppEvent(ActionEvent event) {
        mainAppEvent = event;
    }

    /**
     * Fills each textfield to what is currently stored in the database.
     *
     * @param location Location of the fxml
     * @param resources Locale-specific data required for the method to run automatically
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        currentWifi = WifiDataViewerController.getWifi();
        wifiID.setText(currentWifi.getWifiID());
        address.setText(currentWifi.getAddress());
        provider.setText(currentWifi.getProvider());
        cost.getSelectionModel().select(currentWifi.getCost());
        lattitude.setText(Double.toString(currentWifi.getLatitude()));
        longitude.setText(Double.toString(currentWifi.getLongitude()));
        remarks.setText(currentWifi.getRemarks());
        city.setText(currentWifi.getCity());
        SSID.setText(currentWifi.getSSID());
        suburb.getSelectionModel().select(currentWifi.getSuburb());
        Zip.setText(Integer.toString(currentWifi.getZip()));
    }


    /**
     * Checks each text field and updates the database to what the user has inputted. If any update fails, the user
     * is informed and expected to update their input.
     *
     * @param event Created when the method is called
     * @throws IOException Handles errors caused by an fxml not loading correctly
     */
    @FXML
    void updateValues(ActionEvent event) throws IOException{
        try {
            currentWifi.setAddress(address.getText());
            currentWifi.setProvider(provider.getText());
            currentWifi.setCost(cost.getSelectionModel().getSelectedItem());
            currentWifi.setLatitude(Double.parseDouble(lattitude.getText()));
            currentWifi.setLongitude(Double.parseDouble(longitude.getText()));
            currentWifi.setRemarks(remarks.getText());
            currentWifi.setCity(city.getText());
            currentWifi.setSSID(SSID.getText());
            currentWifi.setSuburb(suburb.getSelectionModel().getSelectedItem());
            currentWifi.setZip(Integer.parseInt(Zip.getText()));

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            showWifiLocations(mainAppEvent);
        } catch (Exception exception) {
            makeErrorDialogueBox("Cannot update data.", "One (or more) field(s) is of an incorrect type.");
        }
    }

}