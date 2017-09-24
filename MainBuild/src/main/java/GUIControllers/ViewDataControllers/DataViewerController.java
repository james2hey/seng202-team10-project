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


public abstract class DataViewerController extends Controller implements Initializable{


    @FXML
    private AnchorPane content;


    @FXML
    void showRoutes(ActionEvent event) throws IOException{
        Parent routeViewerParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/routeViewData.fxml"));
        Scene routeViewerScene = new Scene(routeViewerParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(routeViewerScene);
    }

    @FXML
    void showWifiLocations(ActionEvent event) throws IOException{
        Parent wifiViewerParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/wifiViewData.fxml"));
        Scene wifiViewerScene = new Scene(wifiViewerParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(wifiViewerScene);
    }

    @FXML
    void showRetailers(ActionEvent event) throws IOException{
        Parent retailerViewerParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/retailerViewData.fxml"));
        Scene retailerViewerScene = new Scene(retailerViewerParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(retailerViewerScene);
    }

}

