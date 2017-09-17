package main;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXToggleButton;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;

import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.control.SplitPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller {

    @FXML
    private JFXDrawer drawer;

    @FXML
    private Button button;
    
    @FXML
    private Button manualEntryButton;

            @FXML
    private Button routeButton, wifiButton, retailerButton;

            @FXML
    private SplitPane wifiPanel, retailerPanel, endPanel, startPanel;

        
            @FXML //This importButton reveals other buttons
    private Button importButton, importRoute, importRetailer, importWifi;

    @FXML
    void openDrawer() throws IOException {
        initializeSideDrawer();

        if (drawer.isShown()) {
            drawer.close();
        }
        else {
            drawer.open();
        }
    }

    public void initializeSideDrawer() throws IOException {
        VBox box = FXMLLoader.load(getClass().getClassLoader().getResource("SidePanel.fxml"));
        drawer.setSidePane(box);
    }


    public void login(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    void changeToPlanRouteScene(ActionEvent event) throws IOException {
        Parent planRouteParent = FXMLLoader.load(getClass().getClassLoader().getResource("planRoute.fxml"));
        Scene planRouteScene = new Scene(planRouteParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(planRouteScene);
    }

    @FXML
    void changeToHomeScene(ActionEvent event) throws IOException {
        Parent homeParent = FXMLLoader.load(getClass().getClassLoader().getResource("home.fxml"));
        Scene homeScene = new Scene(homeParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(homeScene);
    }


    @FXML
    void changeToAddDataScene(ActionEvent event) throws IOException {
        Parent addDataParent = FXMLLoader.load(getClass().getClassLoader().getResource("addData.fxml"));
        Scene addDataScene = new Scene(addDataParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(addDataScene);
    }

    @FXML
    void changeToViewDataScene(ActionEvent event) throws IOException {
        Parent viewDataParent = FXMLLoader.load(getClass().getClassLoader().getResource("viewData.fxml"));
        Scene viewDataScene = new Scene(viewDataParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(viewDataScene);
    }

            @FXML
    void changeToManualEntryScene(ActionEvent event) throws IOException {
        Parent manualEntryParent = FXMLLoader.load(getClass().getClassLoader().getResource("manualEntry.fxml"));
        Scene manualEntryScene = new Scene(manualEntryParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(manualEntryScene);
    }

    //TODO: Link with Matt for singular entries. Also make them invisible to start without losing children.
            @FXML //Relates to the manual data page
    void routeEntry(ActionEvent event) throws IOException {
                if(!startPanel.isVisible()) {
                        startPanel.setVisible(true);
                    }
                if(!endPanel.isVisible()) {
                        endPanel.setVisible(true);
                    }
                if(wifiPanel.isVisible()) {
                        wifiPanel.setVisible(false);
                    }
                if(retailerPanel.isVisible()) {
                        retailerPanel.setVisible(false);
                    }
            }

            @FXML //Relates to the manual data page
    void wifiEntry(ActionEvent event) throws IOException {
                if(startPanel.isVisible()) {
                        startPanel.setVisible(false);
                    }
                if(endPanel.isVisible()) {
                        endPanel.setVisible(false);
                    }
                if(!wifiPanel.isVisible()) {
                        wifiPanel.setVisible(true);
                    }
                if(retailerPanel.isVisible()) {
                        retailerPanel.setVisible(false);
                    }
            }
            @FXML //Relates to the manual data page
    void retailerEntry(ActionEvent event) throws IOException {
                if(startPanel.isVisible()) {
                        startPanel.setVisible(false);
                    }
                if(endPanel.isVisible()) {
                        endPanel.setVisible(false);
                    }
                if(wifiPanel.isVisible()) {
                        wifiPanel.setVisible(false);
                    }
                if(!retailerPanel.isVisible()) {
                        retailerPanel.setVisible(true);
                    }
            }



    @FXML
    void chooseFile(ActionEvent event) throws IOException {
        if(!importRoute.isVisible()){
            importRoute.setVisible(true);
            importRetailer.setVisible(true);
            importWifi.setVisible(true);
        }
    }
            @FXML // Import file -> only allows *.csv and prints location afterwards for now...
    //TODO: Link with Matt to add to database.
    void chooseRoute(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        System.out.println(file);
    }
            @FXML //Specifies file types.
    void chooseRetailer(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        System.out.println(file);
    }
            @FXML
    void chooseWifi(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        System.out.println(file);
    }


}