package GUIControllers.ViewDataControllers;
    import GUIControllers.Controller;
    import dataManipulation.DataFilterer;
    import com.jfoenix.controls.JFXDrawer;
    import com.jfoenix.controls.JFXHamburger;
    import com.jfoenix.controls.JFXTextField;
    import com.jfoenix.controls.JFXToggleButton;
    import dataAnalysis.Route;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.scene.Node;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.control.*;
    import javafx.scene.control.cell.PropertyValueFactory;
    import javafx.scene.layout.VBox;
    import javafx.fxml.FXMLLoader;
    import javafx.stage.Stage;
    import main.Main;

    import java.io.IOException;
    import java.net.URL;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.ResourceBundle;

public abstract class DataViewerController extends Controller {

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

    @FXML
    void openAdvancedView(ActionEvent event) {

    }

}

