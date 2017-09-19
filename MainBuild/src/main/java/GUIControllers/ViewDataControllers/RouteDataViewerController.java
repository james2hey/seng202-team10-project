package GUIControllers.ViewDataControllers;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import dataAnalysis.Route;
import dataManipulation.DataFilterer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Main;

import java.io.IOException;
import java.util.ArrayList;

public class RouteDataViewerController extends DataViewerController {

    @FXML
    private JFXToggleButton female;

    @FXML
    private JFXToggleButton male;

    @FXML
    private ToggleGroup genderToggleGroup;

    @FXML
    private JFXTextField startTimeInput;

    @FXML
    private JFXTextField endTimeInput;

    @FXML
    private TableView<Route> tableView;

    @FXML
    private TableColumn<Route, String> StartLocation;

    @FXML
    private TableColumn<Route, String> EndLocation;

    @FXML
    private TableColumn<Route, String> Date;

    @FXML
    private TableColumn<Route, String> StartTime;

    @FXML
    private TableColumn<Route, String> EndTime;

    @FXML
    private DatePicker startDateInput;

    @FXML
    private DatePicker endDateInput;

    private ObservableList<Route> routeList = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        StartLocation.setCellValueFactory(new PropertyValueFactory<Route, String>("StartLocation"));
        EndLocation.setCellValueFactory(new PropertyValueFactory<Route, String>("EndLocation"));
        Date.setCellValueFactory(new PropertyValueFactory<Route, String>("StartDate"));
        StartTime.setCellValueFactory(new PropertyValueFactory<Route, String>("StartTime"));
        EndTime.setCellValueFactory(new PropertyValueFactory<Route, String>("StopTime"));
        tableView.setItems(routeList);System.out.println("Got data");
        tableView.getColumns().setAll(StartLocation, EndLocation, Date, StartTime, EndTime);
    }


    @FXML
    void displayData(ActionEvent event) throws IOException {
        System.out.println("Display button pressed");
        /*
        int gender;
        if(genderToggleGroup.getSelectedToggle() == null) {
            gender = -1;
        } else {
            gender = Integer.valueOf(genderToggleGroup.getSelectedToggle().getUserData().toString());
        }
        int ageLower = Integer.valueOf(startAgeInput.getText());
        int ageUpper = Integer.valueOf(endAgeInput.getText());
        String dateLower = startDateInput.getText();
        String dateUpper = endDateInput.getText();
        if ("DD/MM/YYYY".equals(dateLower) || "DD/MM/YYYY".equals(dateUpper)) {
            dateLower = null;
            dateUpper = null;
        }
        String timeLower = startTimeInput.getText();
        String timeUpper = endTimeInput.getText();
        if ("HH:MM:SS".equals(timeLower) || "HH:MM:SS".equals(timeUpper)) {
            timeLower = null;
            timeUpper = null;
        }
        DataFilterer filterer = new DataFilterer(Main.getDB());
        ArrayList<Route> routes = filterer.filter(gender, dateLower, dateUpper, ageLower, ageUpper,
                timeLower, timeUpper, -1, -1);
        System.out.println("Got data");
        for (int i = 0; i < routes.size(); i++) {
            System.out.println(routes.get(i).getBikeID());
        }
        tableView.getItems().clear();
        routeList.addAll(routes);

        */

    }


}
