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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Cyclist;
import main.HelperFunctions;
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
public class StatisticsController extends Controller implements Initializable {

    @FXML
    private Text longestRoute;

    @FXML
    private Text averageRoute;

    @FXML
    private Text shortestRoute;

    @FXML
    private Text totalDistance;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private BarChart<String, Number> graph;

    @FXML
    private TableView<Route> tableCompletedRoutes;

    @FXML
    private TableColumn<Route, String> startLocation;

    @FXML
    private TableColumn<Route, String> endLocation;

    @FXML
    private TableColumn<Route, String> distance;

    @FXML
    private TableColumn<Route, String> completedRoutes;

    final static String austria = "Austria";
    final static String brazil = "Brazil";
    final static String france = "France";
    final static String italy = "Italy";
    final static String usa = "USA";

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
        findStatistics();

        //Initialise the graph.
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setTickLabelFill(Color.RED);
        graph.setTitle("Recent Route Distances");

        //Selecting data to add
        XYChart.Series series1 = new XYChart.Series();
        series1.getData().add(new XYChart.Data("01/08/2017", 2.35));
        series1.getData().add(new XYChart.Data("03/10/2017", 1.50));
        series1.getData().add(new XYChart.Data("24/10/2017", 3.00));
        series1.getData().add(new XYChart.Data("25/10/2017", 0.89));
        series1.getData().add(new XYChart.Data("03/11/2017", 0.99));

        graph.getData().addAll(series1);
        graph.setLegendVisible(false);




        //Initialise routes completed table.
        routeListObservable.addAll(Main.hu.currentCyclist.getTakenRoutes());
        startLocation.setCellValueFactory(new PropertyValueFactory<>("StartAddress"));
        endLocation.setCellValueFactory(new PropertyValueFactory<>("EndAddress"));
        distance.setCellValueFactory(new PropertyValueFactory<>("Distance"));
        tableCompletedRoutes.setItems(routeListObservable);
        tableCompletedRoutes.getColumns().setAll(completedRoutes);
    }

    /**
     * Finds all of the most recent statistics for the user and displays them.
     */
    private void findStatistics() {
        double total = HelperFunctions.calculateDistanceCycled();
        double average = HelperFunctions.cacluateAverageDistance();

        double shortest = HelperFunctions.calculateShortestRoute();
        if (shortest == 9999999) {
            shortest = 0;
        }
        double longest = HelperFunctions.calculateLongestRoute();
        if (longest == -1) {
            longest = 0;
        }

        totalDistance.setText(total + " km");
        averageRoute.setText(average + " km");
        shortestRoute.setText(shortest + " km");
        longestRoute.setText(longest + " km");
    }


    /**
     * Deletes the selected route from the users taken routes.
     */
    @FXML
    private void deleteTakenRoute() {
        if (tableCompletedRoutes.getSelectionModel().getSelectedItem() != null) {
            Route removingRoute = tableCompletedRoutes.getSelectionModel().getSelectedItem();
            Main.takenRouteTable.deleteTakenRoute(removingRoute);
            routeList.remove(removingRoute);
            routeListObservable.remove(removingRoute);
            Main.hu.currentCyclist.getTakenRoutes().remove(removingRoute);
            findStatistics();
        } else {
            makeErrorDialogueBox("No route selected", "No route was selected to delete." +
                    " You must\nchoose which route you want to delete.");

        }
    }

}
