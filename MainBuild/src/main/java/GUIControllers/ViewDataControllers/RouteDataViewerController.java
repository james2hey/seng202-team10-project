package GUIControllers.ViewDataControllers;

import com.jfoenix.controls.JFXTextField;
import customExceptions.FilterByTimeException;
import dataAnalysis.Route;
import dataManipulation.DataFilterer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class for the route data viewer.
 */

public class RouteDataViewerController extends DataViewerController {

    static private Route route = null;


    @FXML
    private ToggleGroup genderGroup;

    @FXML
    private JFXTextField startLocationInput;

    @FXML
    private JFXTextField endLocationInput;

    @FXML
    private JFXTextField startTimeInput;

    @FXML
    private JFXTextField endTimeInput;

    @FXML
    private JFXTextField bikeIDInput;

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

    @FXML
    private Label favouritesError;

    private ObservableList<Route> routeList = FXCollections.observableArrayList();

    private ArrayList<Route> routes = new ArrayList<Route>();

    private int getRank() {
        return 0;
    }

    static public Route getRoute() {
        return route;
    }

    /**
     * Runs on successfully loading fxml. Loads routes from database and displays them in the table.
     * @param location Location of the fxml
     * @param resources Locale-specific data required for the method to run automatically
     */
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

    /**
     * Called when filter button is pressed. Gets all input from text fields, checks it is valid, and let's user
     * know if it isn't. If all input is valid, it will filter the data and proceed to update the table with the new set
     * of data.
     * @param event Created when the method is called
     * @throws IOException Handles errors caused by an fxml not loading correctly
     */
    @FXML
    void displayData(ActionEvent event) throws IOException {

        int gender;
        System.out.println(genderGroup.getSelectedToggle());
        if(genderGroup.getSelectedToggle() == null) {
            gender = -1;
        } else {
            gender = Integer.parseInt(genderGroup.getSelectedToggle().getUserData().toString());
        }

        String dateLower;
        String dateUpper;
        String pattern = "dd/MM/yyyy";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
        LocalDate startDate = startDateInput.getValue();
        LocalDate endDate = endDateInput.getValue();
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

        String timeLower = startTimeInput.getText() + ":00";
        String timeUpper = endTimeInput.getText() + ":00";
        try {
            if (":00".equals(timeLower)) {
                timeLower = null;
            } else {
                if (timeLower.matches("[0-2][0-9]:[0-5][0-9]:00") == false) {
                    throw new FilterByTimeException("Incorrect time format on start time");
                }
            }
            if (":00".equals(timeUpper)) {
                timeUpper = null;
            } else {
                if (timeUpper.matches("[0-2][0-9]:[0-5][0-9]:00") == false) {
                    throw new FilterByTimeException("Incorrect time format on end time");
                }
            }
            if ((timeLower == null && timeUpper == null) != true && (timeLower == null || timeUpper == null)) {
                throw new FilterByTimeException("Missing time field");
            }
        } catch (FilterByTimeException e) {
            String errorMessage = e.getMessage();
            if (errorMessage.equals("Missing time field")) {
                makeErrorDialogueBox("Missing time field", "Please enter both a lower and upper time limit\nto filter by.");
            } else {
                makeErrorDialogueBox(errorMessage, "Please use the format HH:SS.");
            }
            timeLower = null;
            timeUpper = null;
        }

        String startLocation = startLocationInput.getText();
        String endLocation = endLocationInput.getText();
        if ("".equals(startLocation)) {
            startLocation = null;
        }
        if ("".equals(endLocation)) {
            endLocation = null;
        }

        String bikeID = bikeIDInput.getText();
        if ("".equals(bikeID)) {
            bikeID = null;
        }

        DataFilterer filterer = new DataFilterer(Main.getDB());
        routes = filterer.filterRoutes(gender, dateLower, dateUpper,
                timeLower, timeUpper, startLocation, endLocation, bikeID);
        System.out.println("Got data");
        System.out.println(routes.size());
        for (int i = 0; i < routes.size(); i++) {
            System.out.println(routes.get(i).getBikeID());
        }
        tableView.getItems().clear();
        routeList.addAll(routes);

    }


    /**
     * Adds the route to the users favourites list if it is not already in their favourites, otherwise it
     * creates an error dialogue box telling them it has already been added to their favourites.
     */
    public void addFavouriteRoute(ActionEvent event) throws IOException {
        if (tableView.getSelectionModel().getSelectedItem() == null) {
            makeSuccessDialogueBox("Select which route to add.", "");
        } else {
            String name = Main.hu.currentCyclist.getName();
            Route routeToAdd = tableView.getSelectionModel().getSelectedItem();
            boolean alreadyInList = Main.hu.currentCyclist.routeAlreadyInList(routeToAdd);
            if (!alreadyInList) {
                System.out.println("ADDED " + routeToAdd.getBikeID() + " to cyclist favourites."); // Put this on GUI
                int rank = openRouteRankStage();
                Main.hu.currentCyclist.addRoute(routeToAdd, name, rank, Main.getDB(), Main.hu);
            } else {
                makeErrorDialogueBox("Route already in favourites", "This route has already been " +
                        "added\nto this users favourites list.");
            }
        }
    }


    /**
     * Creates a ChoiceDialog which prompts the user the input their ranking of the route.
     * @return the users ranking, 0 if exited
     */
    @FXML
    private int openRouteRankStage() {
        ArrayList<Integer> a = new ArrayList<>();
        a.add(1);
        a.add(2);
        a.add(3);
        a.add(4);
        a.add(5);
        ChoiceDialog<Integer> c = new ChoiceDialog<>(5, a);
        c.setTitle("Rank this route!");
        c.setHeaderText("Rank this route!");
        c.setContentText("Rating");
        Optional<Integer> result = c.showAndWait();
        if (result.isPresent()) {
            return result.get();
        } else {
            return 0;
        }
    }

    /**
     * Called when view all on map button is pressed. Changes the scene to the plan route with the given list of data
     * ready to be loaded in.
     * @param event Created when the method is called
     * @throws IOException Handles errors caused by an fxml not loading correctly
     */
    @FXML
    public void viewOnMap(ActionEvent event) throws IOException {
        //called when GUI button view on map button is pressed.
        changeToPlanRouteScene(event, null, null, routes);
    }

    /**
     * Called when view selected on map button is pressed. Checks that a route has been selected and if not, informs
     * the user. If so, the scene is changed to plan route and the chosen route is loaded in for view.
     * @param event Created when the method is called
     * @throws IOException Handles errors caused by an fxml not loading correctly
     */
    @FXML
    public void viewSelectedOnMap(ActionEvent event) throws IOException {
        if (tableView.getSelectionModel().getSelectedItem() == null) {
            makeErrorDialogueBox("No route selected.", "Please select a route from the table.");
        } else {
            //Get it done...
            ArrayList<Route> route = new ArrayList<>();
            route.add(tableView.getSelectionModel().getSelectedItem());
            changeToPlanRouteScene(event, null, null, route);
        }
    }

    /**
     * Called when the edit/view all details button is pressed. Checks if a route is selected and informs the user if
     * it is not. If a route is selected, the detailed route viewer is opened, ready to load in the selected routes
     * information.
     * @param event Created when the method is called
     * @throws IOException Handles errors caused by an fxml not loading correctly
     */
    @FXML
    public void editData(ActionEvent event) throws IOException {
        //called by GUI button View/edit route.

        if (tableView.getSelectionModel().getSelectedItem() == null) {
            makeErrorDialogueBox("No route selected.", "Please select a route from the table.");
        } else {
            route = tableView.getSelectionModel().getSelectedItem();
            Stage popup = new Stage();
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.initOwner(((Node) event.getSource()).getScene().getWindow());
            Parent popupParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/DataViewerFXMLs/detailedRouteInformation.fxml"));
            Scene popupScene = new Scene(popupParent);
            popup.setScene(popupScene);
            popup.show();
            DetailedRouteInformation.setMainAppEvent(event);
        }
    }
}
