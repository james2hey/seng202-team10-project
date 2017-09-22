package GUIControllers.ViewDataControllers;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import dataAnalysis.Route;
import dataManipulation.DataFilterer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.HandleUsers;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class RouteDataViewerController extends DataViewerController {

    @FXML
    private JFXToggleButton female;

    @FXML
    private JFXToggleButton male;

    @FXML
    private JFXTextField startLocationInput;

    @FXML
    private JFXTextField endLocationInput;

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
    public void initialize(URL location, ResourceBundle resources) {
        StartLocation.setCellValueFactory(new PropertyValueFactory<>("StartAddress"));
        EndLocation.setCellValueFactory(new PropertyValueFactory<>("EndAddress"));
        Date.setCellValueFactory(new PropertyValueFactory<>("StartDate"));
        StartTime.setCellValueFactory(new PropertyValueFactory<>("StartTime"));
        EndTime.setCellValueFactory(new PropertyValueFactory<>("StopTime"));
        tableView.setItems(routeList);
        tableView.getColumns().setAll(StartLocation, EndLocation, Date, StartTime, EndTime);

        ActionEvent event = new ActionEvent();
        try {
            displayData(event);
        } catch (Exception e) {
            System.out.println("Initialising data has failed.");
        }
    }


    @FXML
    void displayData(ActionEvent event) throws IOException {
        System.out.println("Display button pressed");

        int gender;
        if((male.isSelected() == true && female.isSelected() == true) || (male.isSelected() == false && female.isSelected() == false)) {
            gender = -1;
        } else if (male.isSelected() == true){
            gender = Integer.valueOf(male.getUserData().toString());
        } else {
            gender = Integer.valueOf(female.getUserData().toString());
        }
        String dateLower;
        String dateUpper;
        String pattern = "dd/MM/yyyy";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
        LocalDate startDate = startDateInput.getValue();
        LocalDate endDate = endDateInput.getValue();
        System.out.println(startDate);
        if (startDate != null) {
            dateLower = startDate.format(dateFormatter);
            System.out.println(dateLower);
        } else {
            dateLower = null;
        }
        if (endDate != null) {
            dateUpper = endDate.format(dateFormatter);
        } else {
            dateUpper = null;
        }
        if ("DD/MM/YYYY".equals(dateLower) || "DD/MM/YYYY".equals(dateUpper)) {
            dateLower = null;
            dateUpper = null;
        }
        String timeLower = startTimeInput.getText();
        String timeUpper = endTimeInput.getText();
        if ("HH:MM:SS".equals(timeLower)) {
            timeLower = null;
        } else {
            if (timeLower.matches("[0-9][0-9]:[0-9][0-9]:[0-9][0-9]") == false) {
                startTimeInput.setText("Use the format HH:MM:SS");
                timeLower = null;
            }
        }
        if ("HH:MM:SS".equals(timeUpper)) {
            timeUpper = null;
        } else {
            if (timeUpper.matches("[0-9][0-9]:[0-9][0-9]:[0-9][0-9]") == false) {
                endTimeInput.setText("Use the format HH:MM:SS");
                timeUpper = null;
            }
        }


        String startLocation = startLocationInput.getText();
        String endLocation = endLocationInput.getText();
        if ("Address".equals(startLocation)) {
            startLocation = null;
        }
        if ("Address".equals(endLocation)) {
            endLocation = null;
        }
        DataFilterer filterer = new DataFilterer(Main.getDB());
        ArrayList<Route> routes = filterer.filterRoutes(gender, dateLower, dateUpper,
                timeLower, timeUpper, startLocation, endLocation);
        System.out.println("Got data");
        System.out.println(routes.size());
        for (int i = 0; i < routes.size(); i++) {
            System.out.println(routes.get(i).getBikeID());
        }
        tableView.getItems().clear();
        routeList.addAll(routes);
        Main.routes = routes;

    }

    // ___________________ Favourites button - feel free to change around the ugly composition _______________

    @FXML
    private Label favouritesError;

    /**
     * Adds the currently selected route to the Cyclists routeList.
     */
    public void addFavouriteRoute() {
        favouritesError.setVisible(false);
        if (HandleUsers.currentAnalyst == null) {
            if (tableView.getSelectionModel().getSelectedItem() == null) {
                System.out.println("Select route to add!");
            } else {
                String name = HandleUsers.currentCyclist.getName();
                Route routeToAdd = tableView.getSelectionModel().getSelectedItem();
                boolean alreadyInList = HandleUsers.currentCyclist.addRoute(routeToAdd, name);
                if (!alreadyInList) {
                    System.out.println("ADDED " + routeToAdd.getBikeID() + " to cyclist favourites.");
                    favouritesError.setVisible(true);
                } else {
                    System.out.println("Already in favourites!");
                    favouritesError.setVisible(false);
                }
            }
        } else {
            System.out.println("Feature not available for analyst!");
        }
    }
}
