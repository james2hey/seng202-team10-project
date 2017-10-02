package GUIControllers.ViewDataControllers;

import com.jfoenix.controls.JFXTextField;
import dataAnalysis.WifiLocation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.paint.Color.*;

/**
 * Controller class for detailed wifi information.
 */
public class DetailedWifiInformation extends DataViewerController {

    static private ActionEvent mainAppEvent = null;
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
    private JFXTextField latitude;
    @FXML
    private JFXTextField SSID;
    @FXML
    private JFXTextField remarks;
    @FXML
    private Button update;
    private WifiLocation currentWifi = null;

    static public void setMainAppEvent(ActionEvent event) {
        mainAppEvent = event;
    }

    /**
     * Fills each textfield to what is currently stored in the database.
     *
     * @param location  Location of the fxml
     * @param resources Locale-specific data required for the method to run automatically
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        currentWifi = WifiDataViewerController.getWifi();
        wifiID.setText(currentWifi.getWifiID());
        address.setText(currentWifi.getAddress());
        provider.setText(currentWifi.getProvider());
        cost.getSelectionModel().select(currentWifi.getCost());
        latitude.setText(Double.toString(currentWifi.getLatitude()));
        longitude.setText(Double.toString(currentWifi.getLongitude()));
        remarks.setText(currentWifi.getRemarks());
        city.setText(currentWifi.getCity());
        SSID.setText(currentWifi.getSSID());
        suburb.getSelectionModel().select(currentWifi.getSuburb());
        Zip.setText(Integer.toString(currentWifi.getZip()));
        addressListener();
        providerListener();
        latitudeListener();
        longitudeListener();
        remarksListener();
        cityListener();
        SSIDListener();
        zipListener();
    }


    /**
     * Checks each text field and updates the database to what the user has inputted. If any update fails, the user
     * is informed and expected to update their input.
     *
     * @param event Created when the method is called
     * @throws IOException Handles errors caused by an fxml not loading correctly
     */
    @FXML
    void updateValues(ActionEvent event) throws IOException {
        try {
            currentWifi.setAddress(address.getText());
            currentWifi.setProvider(provider.getText());
            currentWifi.setCost(cost.getSelectionModel().getSelectedItem());
            currentWifi.setLatitude(Double.parseDouble(latitude.getText()));
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


    /**
     * Error handler for address field. Uses a listener to see state of text. Sets color and makes confirm button
     * un-selectable if text field incorrect.
     */
    private void addressListener() {
        address.textProperty().addListener(((observable, oldValue, newValue) -> {
            System.out.println("TextField Text Changed (newValue: " + newValue + ")");
            if (address.getText().length() > 50) {
                address.setFocusColor(RED);
                address.setUnFocusColor(RED);
                update.setDisable(true);
            } else {
                address.setFocusColor(DARKSLATEBLUE);
                address.setUnFocusColor(GREEN);
                update.setDisable(false);
            }
        }));
    }


    /**
     * Error handler for provider field. Uses a listener to see state of text. Sets color and makes confirm button
     * un-selectable if text field incorrect.
     */
    private void providerListener() {
        provider.textProperty().addListener(((observable, oldValue, newValue) -> {
            System.out.println("TextField Text Changed (newValue: " + newValue + ")");
            if (provider.getText().length() > 50) {
                provider.setFocusColor(RED);
                provider.setUnFocusColor(RED);
                update.setDisable(true);
            } else {
                provider.setFocusColor(DARKSLATEBLUE);
                provider.setUnFocusColor(GREEN);
                update.setDisable(false);
            }
        }));
    }


    /**
     * Error handler for latitude field. Uses a listener to see state of text. Sets color and makes confirm button
     * un-selectable if text field incorrect.
     */
    private void latitudeListener() {
        latitude.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("TextField Text Changed (newValue: " + newValue + ")");
            if (!latitude.getText().matches("-?[0-9]?[0-9]?[0-9].[0-9]+")) {
                latitude.setFocusColor(RED);
                latitude.setUnFocusColor(RED);
                update.setDisable(true);
            } else {
                latitude.setFocusColor(DARKSLATEBLUE);
                latitude.setUnFocusColor(GREEN);
                update.setDisable(false);
            }
        });
    }


    /**
     * Error handler for longitude field. Uses a listener to see state of text. Sets color and makes confirm button
     * un-selectable if text field incorrect.
     */
    private void longitudeListener() {
        longitude.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("TextField Text Changed (newValue: " + newValue + ")");
            if (!longitude.getText().matches("-?[0-9]?[0-9]?[0-9].[0-9]+")) {
                longitude.setFocusColor(RED);
                longitude.setUnFocusColor(RED);
                update.setDisable(true);
            } else {
                longitude.setFocusColor(DARKSLATEBLUE);
                longitude.setUnFocusColor(GREEN);
                update.setDisable(false);
            }
        });
    }


    /**
     * Error handler for remarks field. Uses a listener to see state of text. Sets color and makes confirm button
     * un-selectable if text field incorrect.
     */
    private void remarksListener() {
        remarks.textProperty().addListener(((observable, oldValue, newValue) -> {
            System.out.println("TextField Text Changed (newValue: " + newValue + ")");
            if (remarks.getText().length() > 50) {
                remarks.setFocusColor(RED);
                remarks.setUnFocusColor(RED);
                update.setDisable(true);
            } else {
                remarks.setFocusColor(DARKSLATEBLUE);
                remarks.setUnFocusColor(GREEN);
                update.setDisable(false);
            }
        }));
    }


    /**
     * Error handler for city field. Uses a listener to see state of text. Sets color and makes confirm button
     * un-selectable if text field incorrect.
     */
    private void cityListener() {
        city.textProperty().addListener(((observable, oldValue, newValue) -> {
            System.out.println("TextField Text Changed (newValue: " + newValue + ")");
            if (city.getText().length() > 50) {
                city.setFocusColor(RED);
                city.setUnFocusColor(RED);
                update.setDisable(true);
            } else {
                city.setFocusColor(DARKSLATEBLUE);
                city.setUnFocusColor(GREEN);
                update.setDisable(false);
            }
        }));
    }


    /**
     * Error handler for SSID field. Uses a listener to see state of text. Sets color and makes confirm button
     * un-selectable if text field incorrect.
     */
    private void SSIDListener() {
        SSID.textProperty().addListener(((observable, oldValue, newValue) -> {
            System.out.println("TextField Text Changed (newValue: " + newValue + ")");
            if (SSID.getText().length() > 50) {
                SSID.setFocusColor(RED);
                SSID.setUnFocusColor(RED);
                update.setDisable(true);
            } else {
                SSID.setFocusColor(DARKSLATEBLUE);
                SSID.setUnFocusColor(GREEN);
                update.setDisable(false);
            }
        }));
    }


    /**
     * Error handler for zip field. Uses a listener to see state of text. Sets color and makes confirm button
     * un-selectable if text field incorrect.
     */
    private void zipListener() {
        Zip.textProperty().addListener(((observable, oldValue, newValue) -> {
            System.out.println("TextField Text Changed (newValue: " + newValue + ")");
            if (!Zip.getText().matches("[0-9]*") || Zip.getText().length() > 8) {
                Zip.setFocusColor(RED);
                Zip.setUnFocusColor(RED);
                update.setDisable(true);
            } else {
                Zip.setFocusColor(DARKSLATEBLUE);
                Zip.setUnFocusColor(GREEN);
                update.setDisable(false);
            }
        }));
    }

}