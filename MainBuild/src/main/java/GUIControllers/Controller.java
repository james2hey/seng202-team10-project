package GUIControllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import com.lynden.gmapsfx.javascript.object.LatLong;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import java.io.IOException;

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

    @FXML
    public void changeToPlanRouteScene(ActionEvent event) throws IOException {
        Parent planRouteParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/planRoute.fxml"));
        Scene planRouteScene = new Scene(planRouteParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(planRouteScene);
    }

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


    @FXML
    public void changeToHomeScene(ActionEvent event) throws IOException {
        Parent homeParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/home.fxml"));
        Scene homeScene = new Scene(homeParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(homeScene);
    }


    @FXML
    public void changeToAddDataScene(ActionEvent event) throws IOException {
        Parent addDataParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/addData.fxml"));
        Scene addDataScene = new Scene(addDataParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(addDataScene);
    }

    @FXML
    void changeToAddDataScene(ActionEvent event, String startAddress, String endAddress) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/addData.fxml"));
        Scene addDataScene = new Scene(loader.load());
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AddDataController controller = loader.<AddDataController>getController();

        AddDataController.setRouteVals(startAddress, endAddress);
        System.out.println(endAddress);
        controller.changeToRouteEntryScene(event, currentStage);
    }

    @FXML
    public void changeToViewDataScene(ActionEvent event) throws IOException {
        Parent viewDataParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/routeViewData.fxml"));
        Scene viewDataScene = new Scene(viewDataParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(viewDataScene);
    }

    @FXML
    public void makeErrorDialogueBox(String errorMessage, String errorDetails) {
        Alert alert = new Alert(Alert.AlertType.ERROR, errorDetails, ButtonType.OK);
        alert.setHeaderText(errorMessage);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            System.out.println("Ok pressed");
        }
    }
//
//    /**
//     * Makes a confirmation dialogue box and returns yes as true, no as false.
//     * Assumes that if dialogue is exited, result is no.
//     *
//     * @param errorMessage String that provides the error message for the dialogue box.
//     * @param errorDetails String that provides the error details for the dialogue box.
//     * @return boolean representing result of yes/no answer in confirmation box.
//     */
//    @FXML
//    boolean makeConfirmationDialogueBox(String errorMessage, String errorDetails) {
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, errorDetails, ButtonType.NO, ButtonType.YES);
//        alert.setHeaderText(errorMessage);
//        alert.showAndWait();
//
//        if (alert.getResult() == ButtonType.YES) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    /**
     * Makes a confirmation dialogue box and returns yes as true, no as false.
     * Assumes that if dialogue is exited, result is no.
     *
     * @param message String that provides the message for the dialogue box.
     * @param details String that provides the details for the dialogue box.
     */
    @FXML
    public void makeSuccessDialogueBox(String message, String details) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, details, ButtonType.OK);
        alert.setHeaderText(message);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            System.out.println("");
        }
    }

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
