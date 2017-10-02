package GUIControllers.ViewDataControllers;


import com.jfoenix.controls.JFXTextField;
import dataAnalysis.RetailLocation;
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
 * Controller for the detailed retailer information fxml.
 */

public class DetailedRetailerInformation extends DataViewerController {

    static private ActionEvent mainAppEvent = null;
    @FXML
    private JFXTextField zip;
    @FXML
    private JFXTextField secondaryType;
    @FXML
    private JFXTextField address;
    @FXML
    private JFXTextField retailerName;
    @FXML
    private JFXTextField latitude;
    @FXML
    private JFXTextField city;
    @FXML
    private JFXTextField state;
    @FXML
    private ComboBox<String> mainType;
    @FXML
    private JFXTextField longitude;
    @FXML
    private Button update;
    private RetailLocation currentRetailer = null;

    static public void setMainAppEvent(ActionEvent event) {
        mainAppEvent = event;
    }


    /**
     * Called on start up and initialises the values in each of the editable and non-editable fields.
     *
     * @param location  Location of the fxml
     * @param resources Locale-specific data required for the method to run automatically
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        currentRetailer = RetailerDataViewerController.getRetailer();
        retailerName.setText(currentRetailer.getName());
        address.setText(currentRetailer.getAddress());
        latitude.setText(Double.toString(currentRetailer.getLatitude()));
        longitude.setText(Double.toString(currentRetailer.getLongitude()));
        city.setText(currentRetailer.getCity());
        state.setText(currentRetailer.getState());
        zip.setText(Integer.toString(currentRetailer.getZip()));
        mainType.getSelectionModel().select(currentRetailer.getMainType());
        secondaryType.setText(currentRetailer.getSecondaryType());
        addressListener();
        latitudeListener();
        longitudeListener();
        cityListener();
        stateListener();
        zipListener();
        secondaryListener();
    }

    /**
     * Called when the Update button is pressed. It gets the current value from all text fields and updates them in
     * the database. If any fail to update, an error message prompts the user to check the input.
     *
     * @param event Created when the method is called
     * @throws IOException Handles errors caused by an fxml not loading correctly
     */
    @FXML
    void updateValues(ActionEvent event) throws IOException {
        try {
            currentRetailer.setAddress(address.getText());
            currentRetailer.setLatitude(Double.parseDouble(latitude.getText()));
            currentRetailer.setLongitude(Double.parseDouble(longitude.getText()));
            currentRetailer.setCity(city.getText());
            currentRetailer.setState(state.getText());
            currentRetailer.setZip(Integer.parseInt(zip.getText()));
            currentRetailer.setMainType(mainType.getSelectionModel().getSelectedItem());
            currentRetailer.setSecondaryType(secondaryType.getText());

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            showRetailers(mainAppEvent);
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
     * Error handler for state field. Uses a listener to see state of text. Sets color and makes confirm button
     * un-selectable if text field incorrect.
     */
    private void stateListener() {
        state.textProperty().addListener(((observable, oldValue, newValue) -> {
            System.out.println("TextField Text Changed (newValue: " + newValue + ")");
            if (state.getText().length() > 12) {
                state.setFocusColor(RED);
                state.setUnFocusColor(RED);
                update.setDisable(true);
            } else {
                state.setFocusColor(DARKSLATEBLUE);
                state.setUnFocusColor(GREEN);
                update.setDisable(false);
            }
        }));
    }


    /**
     * Error handler for zip field. Uses a listener to see state of text. Sets color and makes confirm button
     * un-selectable if text field incorrect.
     */
    private void zipListener() {
        zip.textProperty().addListener(((observable, oldValue, newValue) -> {
            System.out.println("TextField Text Changed (newValue: " + newValue + ")");
            if (!zip.getText().matches("[0-9]*") || zip.getText().length() > 8) {
                zip.setFocusColor(RED);
                zip.setUnFocusColor(RED);
                update.setDisable(true);
            } else {
                zip.setFocusColor(DARKSLATEBLUE);
                zip.setUnFocusColor(GREEN);
                update.setDisable(false);
            }
        }));
    }


    /**
     * Error handler for secondaryType field. Uses a listener to see state of text. Sets color and makes confirm button
     * un-selectable if text field incorrect.
     */
    private void secondaryListener() {
        secondaryType.textProperty().addListener(((observable, oldValue, newValue) -> {
            System.out.println("TextField Text Changed (newValue: " + newValue + ")");
            if (secondaryType.getText().length() > 50) {
                secondaryType.setFocusColor(RED);
                secondaryType.setUnFocusColor(RED);
                update.setDisable(true);
            } else {
                secondaryType.setFocusColor(DARKSLATEBLUE);
                secondaryType.setUnFocusColor(GREEN);
                update.setDisable(false);
            }
        }));
    }

}
