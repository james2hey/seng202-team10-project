package GUIControllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import dataAnalysis.RetailLocation;
import dataAnalysis.Route;
import dataAnalysis.WifiLocation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public abstract class Controller {

    @FXML
    private JFXDrawer drawer;

    @FXML
    private Button addDataButton;

    @FXML
    private Button viewDataButton;

    @FXML
    private Button planRouteButton;

    @FXML
    private Button homeButton;

    @FXML
    private JFXHamburger hamburger;

    /**
     * Should be called every time the jfoenix hamburger is clicked. It will open the side panel if it
     * is currently closed, or close it if it is currently open.
     *
     * @throws IOException Catches error if fxml does not load correctly
     */
    @FXML
    void openDrawer() throws IOException {
//
        VBox box = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/SidePanel.fxml"));
        drawer.setSidePane(box);

        if (drawer.isShown()) {
            drawer.close();
        } else {
            drawer.open();
        }
    }

    /**
     * Changes the current scene to the plan route scene. This method is used when a blank map is to be loaded.
     *
     * @param event Event Created when function called, used to identify the current stage.
     * @throws IOException Catches error if fxml does not load correctly
     */
    @FXML
    public void changeToPlanRouteScene(ActionEvent event) throws IOException {
        Parent planRouteParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/planRoute.fxml"));
        Scene planRouteScene = new Scene(planRouteParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(planRouteScene);
    }

    /**
     * Changes the current scene to the plan route scene. This method is used when preselected data is to be loaded into the map
     * when it loads.
     *
     * @param event Event created on method call
     * @param wifiLocations Array list of Wifi Locations that are to be loaded into map (can be null)
     * @param retailLocations Array list of Retailers that are to be loaded into map (can be null)
     * @param routes Array list of Routes that are to be loaded into map (can be null)
     * @throws IOException Catches error if fxml does not load correctly
     */
    @FXML
    public void changeToPlanRouteScene(ActionEvent event, ArrayList<WifiLocation> wifiLocations, ArrayList<RetailLocation> retailLocations, ArrayList<Route> routes) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/planRoute.fxml"));
        Scene planRouteScene = new Scene(loader.load());
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        PlanRouteController controller = loader.<PlanRouteController>getController();
        controller.addWifiLocations(wifiLocations);
        controller.addRetailLocations(retailLocations);
        controller.addRoutes(routes);
        currentStage.setScene(planRouteScene);
    }

    /**
     * Changes scene to the home scene.
     * @param event Created when the method is called
     * @throws IOException Handles errors caused by an fxml not loading correctly
     */
    @FXML
    public void changeToHomeScene(ActionEvent event) throws IOException {
        Parent homeParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/home.fxml"));
        Scene homeScene = new Scene(homeParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(homeScene);
    }

    /**
     * Changes scene to the add data scene.
     * @param event Created when the method is called
     * @throws IOException Handles errors caused by an fxml not loading correctly
     */
    @FXML
    public void changeToAddDataScene(ActionEvent event) throws IOException {
        Parent addDataParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/addData.fxml"));
        Scene addDataScene = new Scene(addDataParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(addDataScene);
    }

    /**
     * Changes scene to the add data scene taking parameters to preallocate some of the fields.
     * @param event Created when the method is called
     * @param startAddress Preallocated string as the start of the route to add.
     * @param endAddress Preallocated string as the end of the route to add.
     * @throws IOException Handles errors caused by an fxml not loading correctly
     */
    @FXML
    void changeToAddDataScene(ActionEvent event, String startAddress, String endAddress) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/addData.fxml"));
        Scene addDataScene = new Scene(loader.load());
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AddDataController controller = loader.<AddDataController>getController();

        AddDataController.setRouteVals(startAddress, endAddress);
        System.out.println(endAddress);
        controller.changeToRouteEntryScene(event);
    }

    /**
     * Changes the scene to view data.
     * @param event Created when the method is called
     * @throws IOException Handles errors caused by an fxml not loading correctly
     */
    @FXML
    public void changeToViewDataScene(ActionEvent event) throws IOException {
        Parent viewDataParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/DataViewerFXMLs/routeViewData.fxml"));
        Scene viewDataScene = new Scene(viewDataParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(viewDataScene);
    }

    /**
     * Creates a dialogue box over current scene with two strings that explain to the user why the dialogue box has popped up.
     *
     * @param errorMessage String providing message for top of dialogue box eg. "Error: 404"
     * @param errorDetails String providing message for bottom of dialogue box eg. "Please refresh the page"
     */
    @FXML
    public static void makeErrorDialogueBox(String errorMessage, String errorDetails) {
        Alert alert = new Alert(Alert.AlertType.ERROR, errorDetails, ButtonType.OK);
        alert.setHeaderText(errorMessage);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            System.out.println("Ok pressed");
        }
    }

    /**
     * Makes a confirmation dialogue box and returns yes as true, no as false.
     * Assumes that if dialogue is exited, result is no.
     *
     * @param errorMessage String that provides the error message for the dialogue box
     * @param errorDetails String that provides the error details for the dialogue box
     * @return boolean representing result of yes/no answer in confirmation box.
     */
    @FXML
    boolean makeConfirmationDialogueBox(String errorMessage, String errorDetails) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, errorDetails, ButtonType.NO, ButtonType.YES);
        alert.setHeaderText(errorMessage);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Makes a confirmation dialogue box and returns yes as true, no as false.
     * Assumes that if dialogue is exited, result is no.
     *
     * @param message String that provides the message for the dialogue box.
     * @param details String that provides the details for the dialogue box.
     */
    @FXML
    public static void makeSuccessDialogueBox(String message, String details) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, details, ButtonType.OK);
        alert.setHeaderText(message);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            System.out.println("");
        }
    }

    /**
     * Launches the help screen.
     *
     * @param event Event created on method call
     * @throws IOException Catches error if fxml does not load correctly
     */

    @FXML
    public void getHelp(ActionEvent event) throws IOException{
        Stage popup = new Stage();
        popup.initModality(Modality.WINDOW_MODAL);
        popup.initOwner(((Node) event.getSource()).getScene().getWindow());
        Parent popupParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/help.fxml"));
        Scene popupScene = new Scene(popupParent);
        popup.setScene(popupScene);
        popup.show();
    }
}
