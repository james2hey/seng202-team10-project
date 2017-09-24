package GUIControllers.ViewDataControllers;

import com.jfoenix.controls.JFXTextField;
import dataAnalysis.Route;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


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

    private Route currentRoute = null;

    static private ActionEvent mainAppEvent = null;

    static public void setMainAppEvent(ActionEvent event) {
        mainAppEvent = event;
    }

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
    }


    @FXML
    void updateValues(ActionEvent event) throws IOException{
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
    }

}
