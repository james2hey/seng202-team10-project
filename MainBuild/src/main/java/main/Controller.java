package main;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;

import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import dataManipulation.DataFilterer;
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
    private Button importButton, importRoute, importRetailer, importWifi, addDataButton;

    @FXML
    private JFXTextField rSAddress, rEAddress, rSLongitude, rELongitude, rSLatitude, rELatitude, rSTime, rETime, rSDate, rEDate;

    private int singleLineType = 0;


    @FXML
    public TextField username;

//    @FXML
//    public Text nameInUse;

    @FXML
    public void createCyclist() {
        //nameInUse.setVisible(false);
        String name = username.getText();
        boolean created = HandleUsers.createNewUser(name, true);
//        if (created) {
//            System.out.println("Creating cyclist for " + name);
//            // Take user to main screen.
//        } else {
//            nameInUse.setVisible(true);
//        }
    }

    @FXML
    public void createAnalyst() {
        //nameInUse.setVisible(false);
        String name = username.getText();
        HandleUsers.createNewUser(name,false);
        boolean created = HandleUsers.createNewUser(name, false);
//        if (created) {
//            System.out.println("Creating analyst for " + name);
//            // Take user to main screen.
//        } else {
//            nameInUse.setVisible(true);
//        }
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

    public void initializeSideDrawer() throws IOException {
        VBox box = FXMLLoader.load(getClass().getClassLoader().getResource("SidePanel.fxml"));
        drawer.setSidePane(box);
    }

    @FXML
    void displayData(ActionEvent event) throws IOException {
        System.out.println("Display Data button pressed.");
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
    //Converts Dates to the right format for CSV insertion.
    private int[] convertDates(String dateLower, String dateUpper) {
        int dateInts[] = new int[6];
        dateInts[0] = Integer.parseInt(dateLower.substring(0, 2));
        dateInts[1] = Integer.parseInt(dateLower.substring(3, 5));
        dateInts[2] = Integer.parseInt(dateLower.substring(6));
        dateInts[3] = Integer.parseInt(dateUpper.substring(0, 2));
        dateInts[4] = Integer.parseInt(dateUpper.substring(3, 5));
        dateInts[5] = Integer.parseInt(dateUpper.substring(6));
        return dateInts;
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
        singleLineType = 1;
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
        singleLineType = 2;
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
        singleLineType = 3;
    }

    @FXML //TODO: Trip duration to not be NULL
    void singleCSVLine(ActionEvent event) throws IOException {
        if(singleLineType == 0) {
            System.out.print("We are here");
            return;
        }

        /*if(singleLineType == 1){
            int[] dateInts = convertDates(rSDate.getAccessibleText(), rEDate.getAccessibleText());

            DatabaseManager.addTrip(0, dateInts[2], dateInts[1], dateInts[0], rSTime.getAccessibleText(),
            dateInts[6], dateInts[5], dateInts[4], rETime.getAccessibleText(), "1",
                    "Start", Double.parseDouble(rSLatitude.getAccessibleText()),
                    Double.parseDouble(rSLongitude.getAccessibleText()), "2", "End",
                    Double.parseDouble(rELatitude.getAccessibleText()), Double.parseDouble(rELongitude.getAccessibleText()),
                    "1", "User", 2017, 1);
        }*/
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
        //TODO: Link with Matt to add to database. Fix efficiency of three blocks (try make it 1 block/method).
    void chooseRoute(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        System.out.println(file);
        CSV_Importer.readcsv(file.toString(), 3);
    }
    @FXML //Specifies file types.
    void chooseRetailer(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        System.out.println(file);
        CSV_Importer.readcsv(file.toString(), 1);
    }
    @FXML
    void chooseWifi(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        System.out.println(file);
        CSV_Importer.readcsv(file.toString(), 2);
    }


}