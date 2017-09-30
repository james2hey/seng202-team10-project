package GUIControllers.ViewDataControllers;

import com.jfoenix.controls.JFXTextField;
import dataAnalysis.Route;
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
 * Controller class for the detailed route information.
 */

public class DetailedRouteInformation extends RouteDataViewerController {

    @FXML
    private ComboBox<String> gender;

    @FXML
    private JFXTextField startMonth;

    @FXML
    private JFXTextField startAddress;

    @FXML
    private JFXTextField startDay;

    @FXML
    private JFXTextField startYear;

    @FXML
    private JFXTextField bikeID;

    @FXML
    private JFXTextField endLatitude;

    @FXML
    private JFXTextField endYear;

    @FXML
    private JFXTextField startLatitude;

    @FXML
    private JFXTextField cyclistBirthYear;

    @FXML
    private JFXTextField endDay;

    @FXML
    private JFXTextField endLongitude;

    @FXML
    private JFXTextField endStationID;

    @FXML
    private JFXTextField startTime;

    @FXML
    private JFXTextField userType;

    @FXML
    private JFXTextField endTime;

    @FXML
    private JFXTextField startLongitude;

    @FXML
    private JFXTextField startStationID;

    @FXML
    private JFXTextField tripDuration;

    @FXML
    private JFXTextField endMonth;

    @FXML
    private JFXTextField endAddress;

    @FXML
    private Button update;

    private Route currentRoute = null;

    static private ActionEvent mainAppEvent = null;

    static public void setMainAppEvent(ActionEvent event) {
        mainAppEvent = event;
    }

    /**
     * Called upon successful opening on the stage. This fills the text fields with the currently stored values of each.
     *
     * @param location Location of the fxml
     * @param resources Locale-specific data required for the method to run automatically
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        currentRoute = RouteDataViewerController.getRoute();
        startAddress.setText(currentRoute.getStartAddress());
        endAddress.setText(currentRoute.getEndAddress());
        startLatitude.setText(Double.toString(currentRoute.getStartLatitude()));
        endLatitude.setText(Double.toString(currentRoute.getEndLatitude()));
        startLongitude.setText(Double.toString(currentRoute.getStartLongitude()));
        endLongitude.setText(Double.toString(currentRoute.getEndLongitude()));
        startTime.setText(currentRoute.getStartTime());
        endTime.setText(currentRoute.getStopTime());
        startDay.setText(currentRoute.getStartDay());
        endDay.setText(currentRoute.getStopDay());
        startMonth.setText(currentRoute.getStartMonth());
        endMonth.setText(currentRoute.getStopMonth());
        startYear.setText(currentRoute.getStartYear());
        endYear.setText(currentRoute.getStopYear());
        startStationID.setText(Integer.toString(currentRoute.getStartStationID()));
        endStationID.setText(Integer.toString(currentRoute.getEndStationID()));
        tripDuration.setText(Integer.toString(currentRoute.getDuration()));
        cyclistBirthYear.setText(Integer.toString(currentRoute.getAge()));
        gender.getSelectionModel().select(currentRoute.getGender());
        userType.setText(currentRoute.getUserType());
        bikeID.setText(currentRoute.getBikeID());
        startLatListener();
        startLongListener();
        endLatListener();
        endLongListener();
        endDayListener();
        endMonthListener();
        endYearListener();
    }


    /**
     * Checks each textfield and update the values in the database. If any fail, the user is informed and expected to
     * correct them.
     *
     * @param event Created when the method is called
     * @throws IOException Handles errors caused by an fxml not loading correctly
     */
    @FXML
    void updateValues(ActionEvent event) throws IOException{
        try {
            System.out.println("Update button clicked");
            currentRoute.setStartAddress(startAddress.getText());
            currentRoute.setEndAddress(endAddress.getText());
            currentRoute.setStartLat(Double.parseDouble(startLatitude.getText()));
            currentRoute.setEndLat(Double.parseDouble(endLatitude.getText()));
            currentRoute.setStartLong(Double.parseDouble(startLongitude.getText()));
            currentRoute.setEndLong(Double.parseDouble(endLongitude.getText()));
            currentRoute.setStopTime(endTime.getText());
            currentRoute.setStopDay(endDay.getText());
            currentRoute.setStopMonth(endMonth.getText());
            currentRoute.setStopYear(endYear.getText());
            currentRoute.setStartID(Integer.parseInt(startStationID.getText()));
            currentRoute.setEndID(Integer.parseInt(endStationID.getText()));
            currentRoute.setDuration(Integer.parseInt(tripDuration.getText()));
            currentRoute.setAge(Integer.parseInt(cyclistBirthYear.getText()));
            currentRoute.setGender(gender.getSelectionModel().getSelectedItem());
            currentRoute.setUserType(userType.getText());

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            showRoutes(mainAppEvent);
        } catch (Exception exception) {
            makeErrorDialogueBox("Cannot update data.", "One (or more) field(s) is of an " +
                    "incorrect type.");
        }
    }


    /**
     * Error handler for startLatitude field. Uses a listener to see state of text. Sets color and makes confirm button
     * un-selectable if text field incorrect.
     */
    private void startLatListener() {
        startLatitude.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("TextField Text Changed (newValue: " + newValue + ")");
            if (!startLatitude.getText().matches("-?[0-9]?[0-9].[0-9]+")) {
                startLatitude.setFocusColor(RED);
                startLatitude.setUnFocusColor(RED);
                update.setDisable(true);
            } else {
                startLatitude.setFocusColor(DARKSLATEBLUE);
                startLatitude.setUnFocusColor(GREEN);
                update.setDisable(false);
            }
        });
    }


    /**
     * Error handler for startLongitude field. Uses a listener to see state of text. Sets color and makes confirm button
     * un-selectable if text field incorrect.
     */
    private void startLongListener() {
        startLongitude.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("TextField Text Changed (newValue: " + newValue + ")");
            if (!startLongitude.getText().matches("-?[0-9]?[0-9].[0-9]+")) {
                startLongitude.setFocusColor(RED);
                startLongitude.setUnFocusColor(RED);
                update.setDisable(true);
            } else {
                startLongitude.setFocusColor(DARKSLATEBLUE);
                startLongitude.setUnFocusColor(GREEN);
                update.setDisable(false);
            }
        });
    }


    /**
     * Error handler for endLatitude field. Uses a listener to see state of text. Sets color and makes confirm button
     * un-selectable if text field incorrect.
     */
    private void endLatListener() {
        endLatitude.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("TextField Text Changed (newValue: " + newValue + ")");
            if (!endLatitude.getText().matches("-?[0-9]?[0-9].[0-9]+")) {
                endLatitude.setFocusColor(RED);
                endLatitude.setUnFocusColor(RED);
                update.setDisable(true);
            } else {
                endLatitude.setFocusColor(DARKSLATEBLUE);
                endLatitude.setUnFocusColor(GREEN);
                update.setDisable(false);
            }
        });
    }


    /**
     * Error handler for endLongitude field. Uses a listener to see state of text. Sets color and makes confirm button
     * un-selectable if text field incorrect.
     */
    private void endLongListener() {
        endLongitude.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("TextField Text Changed (newValue: " + newValue + ")");
            if (!endLongitude.getText().matches("-?[0-9]?[0-9].[0-9]+")) {
                endLongitude.setFocusColor(RED);
                endLongitude.setUnFocusColor(RED);
                update.setDisable(true);
            } else {
                endLongitude.setFocusColor(DARKSLATEBLUE);
                endLongitude.setUnFocusColor(GREEN);
                update.setDisable(false);
            }
        });
    }


    /**
     * Error handler for endDay field. Uses a listener to see state of text. Sets color and makes confirm button
     * un-selectable if text field incorrect.
     */
    private void endDayListener() {
        endDay.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("TextField Text Changed (newValue: " + newValue + ")");
            if (endDay.getText().matches("[a-zA-Z]+") || endDay.getText().length() > 2) {
                endDay.setFocusColor(RED);
                endDay.setUnFocusColor(RED);
                update.setDisable(true);
            } else {
                endDay.setFocusColor(DARKSLATEBLUE);
                endDay.setUnFocusColor(GREEN);
                update.setDisable(false);
            }
        });
    }


    /**
     * Error handler for endMonth field. Uses a listener to see state of text. Sets color and makes confirm button
     * un-selectable if text field incorrect.
     */
    private void endMonthListener() {
        endMonth.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("TextField Text Changed (newValue: " + newValue + ")");
            if (endMonth.getText().matches("[a-zA-Z]+") || endMonth.getText().length() > 2) {
                endMonth.setFocusColor(RED);
                endMonth.setUnFocusColor(RED);
                update.setDisable(true);
            } else {
                endMonth.setFocusColor(DARKSLATEBLUE);
                endMonth.setUnFocusColor(GREEN);
                update.setDisable(false);
            }
        });
    }


    /**
     * Error handler for endYear field. Uses a listener to see state of text. Sets color and makes confirm button
     * un-selectable if text field incorrect.
     */
    private void endYearListener() {
        endYear.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("TextField Text Changed (newValue: " + newValue + ")");
            if (endYear.getText().matches("[a-zA-Z]+") || endYear.getText().length() < 4 ||
                    endYear.getText().length() > 4) {
                endYear.setFocusColor(RED);
                endYear.setUnFocusColor(RED);
                update.setDisable(true);
            } else {
                endYear.setFocusColor(DARKSLATEBLUE);
                endYear.setUnFocusColor(GREEN);
                update.setDisable(false);
            }
        });
    }
}
