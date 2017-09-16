package main;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
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
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable, MapComponentInitializedListener {

    @FXML
    private Button button;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private Text buttonPressedText;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private Button addDataButton;

    @FXML
    private Button viewDataButton;

    @FXML
    private Button planRouteButton;

    @FXML
    private Button manualEntryButton;
//----------------------------------- Objects below are in the manual data page.
    @FXML
    private Button homeButton;

    @FXML
    private Button routeButton;

    @FXML
    private Button wifiButton;

    @FXML
    private Button retailerButton;

    @FXML
    private SplitPane wifiPanel;

    @FXML
    private SplitPane retailerPanel;

    @FXML
    private SplitPane endPanel;

    @FXML
    private SplitPane startPanel;
    //-------------------------------------

    @FXML //This button is used for file opening from addData page.
    private Button importButton;

    @FXML
    private TextField addressTextField;

    @FXML
    private GoogleMapView mapView;

    private GeocodingService geocodingService;

    private GoogleMap map;

    private StringProperty address = new SimpleStringProperty();

    private MarkerOptions point1opt;

    private Marker point1;

    @FXML
    void login() {
        System.exit(0);
    }

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
    //TODO: Implement this from add data.
    void changeToManualEntryScene(ActionEvent event) throws IOException {
        Parent manualEntryParent = FXMLLoader.load(getClass().getClassLoader().getResource("manualEntry.fxml"));
        Scene planRouteScene = new Scene(manualEntryParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(planRouteScene);
    }

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

    @FXML // Relates to file import button on Add Data page
    void chooseFile(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        System.out.println(file);
    }

    @FXML
    void displayData(ActionEvent event) throws IOException {
        System.out.println("Display Data button pressed.");
    }


    //@Override
    public void initializeSideDrawer() throws IOException {
        VBox box = FXMLLoader.load(getClass().getClassLoader().getResource("SidePanel.fxml"));
        drawer.setSidePane(box);
    }

    @Override
    public void mapInitialized() {
        geocodingService = new GeocodingService();
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(-43.5235375, 172.5839233))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(12);

        map = mapView.createMap(mapOptions);
    }

    @Override
    public void initialize(URL locchangeToManualEntryation, ResourceBundle resources) {
        //mapView.addMapInializedListener(this);
        //address.bind(addressTextField.textProperty());
        //point1opt = new MarkerOptions();
    }

    @FXML
    public void addressTextFieldAction(ActionEvent event) {
        geocodingService.geocode(address.get(), (GeocodingResult[] results, GeocoderStatus status) -> {

            LatLong latLong = null;

            if( status == GeocoderStatus.ZERO_RESULTS) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No matching address found");
                alert.show();
                return;
            } else if( results.length > 1 ) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Multiple results found, showing the first one.");
                alert.show();
                latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
            } else {
                latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
            }

            map.setCenter(latLong);
            point1opt.position(latLong);
            point1 = new Marker(point1opt);
            map.addMarker(point1);

        });
    }
}