package GUIControllers.ViewDataControllers;

import GUIControllers.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Abstract parent controller class for all controllers in the data viewer section of the application.
 * Here, scene changing methods are defined.
 */


public abstract class DataViewerController extends Controller implements Initializable {


    @FXML
    private AnchorPane content;

    /**
     * Changes the current scene to view route data.
     *
     * @param event Created when the method is called.
     * @throws IOException Handles errors caused by an fxml not loading correctly.
     */
    @FXML
    void showRoutes(ActionEvent event) throws IOException {
        Parent routeViewerParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/DataViewerFXMLs/routeViewData.fxml"));
        Scene routeViewerScene = new Scene(routeViewerParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(routeViewerScene);
    }




    /**
     * Changes the current scene to wifi location viewer.
     *
     * @param event Created when the method is called
     * @throws IOException Handles errors caused by an fxml not loading correctly
     */
    @FXML
    void showWifiLocations(ActionEvent event) throws IOException {
        Parent wifiViewerParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/DataViewerFXMLs/wifiViewData.fxml"));
        Scene wifiViewerScene = new Scene(wifiViewerParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(wifiViewerScene);
    }

    /**
     * Changes the current scene to the the retailer data viewer.
     *
     * @param event Created when the method is called.
     * @throws IOException Handles errors caused by an fxml not loading correctly.
     */
    @FXML
    void showRetailers(ActionEvent event) throws IOException {
        Parent retailerViewerParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/DataViewerFXMLs/retailerViewData.fxml"));
        Scene retailerViewerScene = new Scene(retailerViewerParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(retailerViewerScene);
    }

}

