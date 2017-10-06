package GUIControllers;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import dataAnalysis.Route;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import dataHandler.DatabaseUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Cyclist;
import main.Main;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.Arrays;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static main.Cyclist.*;


/**
 * Controller for the user information scene.
 */
public class UserInformationController extends Controller implements Initializable {

    @FXML
    private Text longestRoute;

    @FXML
    private ComboBox<String> gender;

    @FXML
    private DatePicker dob;

    @FXML
    private Text mostVisitedRetailer;

    @FXML
    private JFXTextField name;

    @FXML
    private Text shortestRoute;

    @FXML
    private Text totalDistance;

    @FXML
    private JFXHamburger hamburger;

//    @FXML
//    private BarChart<String, Number> graph;

    @FXML
    private TableView<Route> tableCompletedRoutes;

    @FXML
    private TableColumn<Route, String> startLocation;

    @FXML
    private TableColumn<Route, String> distance;

    @FXML
    private TableColumn<Route, String> completedRoutes;

//    final static String austria = "Austria";
//    final static String brazil = "Brazil";
//    final static String france = "France";
//    final static String italy = "Italy";
//    final static String usa = "USA";

    private ArrayList<Route> routeList = new ArrayList<>();
    private ObservableList<Route> routeListObservable = FXCollections.observableArrayList();



    /**
     * Runs on start up. Sets the values of the user profile to what is currently saved in the database.
     * Also, calculates the User statistics and displays them for the user.
     *
     * @param location  Location of the fxml
     * @param resources Locale-specific data required for the method to run automatically
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        name.setText(getName());
        dob.setValue(LocalDate.of(getBirthYear(), getBmonth(), getBDay())); // This is crashing for some reason @Braden
        System.out.println(getGender());
        if (getGender() == 1) {
            gender.getSelectionModel().select("Male");
        } else if (getGender() == 2) {
            gender.getSelectionModel().select("Female");
        } else {
            gender.getSelectionModel().select("Other");
        }


//        System.out.println("initialising graph");
//        final CategoryAxis xAxis = new CategoryAxis();
//        final NumberAxis yAxis = new NumberAxis();
//        graph.setTitle("Recent Route Distances");
//        xAxis.setLabel("Country");
//        yAxis.setLabel("Value");
//
//        XYChart.Series series1 = new XYChart.Series();
//        series1.setName("Fuck");
//        series1.getData().add(new XYChart.Data(austria, 25601.34));
//        series1.getData().add(new XYChart.Data(brazil, 20148.82));
//        series1.getData().add(new XYChart.Data(france, 10000));
//        series1.getData().add(new XYChart.Data(italy, 35407.15));
//        series1.getData().add(new XYChart.Data(usa, 12000));

//        graph.getData().addAll(series1);

        routeListObservable.addAll(Main.hu.currentCyclist.getTakenRoutes());

        startLocation.setCellValueFactory(new PropertyValueFactory<>("StartLocation"));
        distance.setCellValueFactory(new PropertyValueFactory<>("Distance"));
        tableCompletedRoutes.setItems(routeListObservable);

        tableCompletedRoutes.getColumns().setAll();// needs to have the column table as a parameter.
    }


    /**
     * Called when the update profile button is pressed. Gets the information currently in the GUI fields and sets the
     * current users information to them. Alerts user of success/failure with dialogue boxes.
     *
     * @param event Created on pressing the button. Not used.
     */
    @FXML
    void updateProfile(ActionEvent event) {
        try {
            LocalDate newDOB = dob.getValue();
            String newDOBString = newDOB.toString();
            int newYear = Integer.parseInt(newDOBString.split("-")[0]);
            int newMonth = Integer.parseInt(newDOBString.split("-")[1]);
            int newDay = Integer.parseInt(newDOBString.split("-")[2]);
            int newGender;
            setBirthday(newDay, newMonth, newYear);
            if (gender.getSelectionModel().getSelectedItem().equals("Male")) {
                setGender(1);
                newGender = 1;
            } else if (gender.getSelectionModel().getSelectedItem().equals("Female")) {
                setGender(2);
                newGender = 2;
            } else {
                setGender(0);
                newGender = 0;
            }
            String newName = name.getText();
            setName(newName);
//            DatabaseUser d = new DatabaseUser(Main.getDB());
            Main.databaseUser.updateDetails(newName, newDay, newMonth, newYear, newGender);
            makeSuccessDialogueBox("Success!", "Your profile has been successfully updated.");
        } catch (Exception e) {
            makeErrorDialogueBox("Failed", "An error occurred while updating your profile.");
        }
    }


    /**
     * Deletes the selected route from the users taken routes.
     */
    @FXML
    private void deleteTakenRoute() {
        if (tableCompletedRoutes.getSelectionModel().getSelectedItem() != null) {
            //Main.takenRouteTable.deleteTakenRoute();  --get selected route...
            routeList.remove(tableCompletedRoutes.getSelectionModel().getSelectedItem());
            routeListObservable.remove(tableCompletedRoutes.getSelectionModel().getSelectedItem());
        } else {
            makeErrorDialogueBox("No route selected", "No route was selected to delete." +
                " You must\nchoose which route you want to delete.");

        }
    }


    /**
     * Runs when the log out button is pressed. Changes the scene to the log in scene and signs out the current user.
     *
     * @param event Created when the method is called
     * @throws IOException Handles errors caused by an fxml not loading correctly
     */
    @FXML
    public void logout(ActionEvent event) throws IOException {
        Parent logInParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/startUp.fxml"));
        Scene logInScene = new Scene(logInParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(logInScene);
        Main.hu.logOutOfUser();
    }

}
