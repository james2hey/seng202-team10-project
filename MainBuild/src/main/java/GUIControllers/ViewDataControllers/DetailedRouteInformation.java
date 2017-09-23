package GUIControllers.ViewDataControllers;

import com.jfoenix.controls.JFXTextField;
import dataAnalysis.Route;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.net.URL;
import java.util.ResourceBundle;


public class DetailedRouteInformation extends RouteDataViewerController {

    @FXML
    private JFXTextField gender;

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
        bikeID.setText(currentRoute.getBikeID());
    }


    @FXML
    void updateValues(ActionEvent event) {
        currentRoute.setStartAddress(startAddress.getText());

    }

}
