package GUIControllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXTextField;

import dataHandler.RetailerDataHandler;
import dataHandler.RouteDataHandler;
import dataHandler.SQLiteDB;
import dataHandler.WifiDataHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SplitPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.HandleUsers;
import main.Main;
import tornadofx.control.DateTimePicker;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by bal65 on 16/09/17.
 */
public class AddDataController extends Controller implements Initializable {

    @FXML //This importButton reveals other buttons
    private Button importButton, importRoute, importRetailer, importWifi, addDataButton;

    @FXML // Route Errors
    private Text sLongError, sLatError, sTimeError, sDateError, eLongError, eLatError, eTimeError, eDateError;

    @FXML //Route Fields
    private JFXTextField rSAddress, rEAddress, rSLongitude, rELongitude, rSLatitude, rELatitude, rSTime, rETime;

    @FXML // Retailer Fields
    private JFXTextField retailerName, retailerAddress, retailerLong, retailerLat, retailerPrim, retailerSec;

    @FXML
    private DateTimePicker rSDate, rEDate;

    @FXML // Wifi Fields
    private JFXTextField wifiName, wifiLong, wifiLat, wifiAddress, wifiPostcode, wifiComments;

    @FXML // Wifi & Retailer Errors
    private Text wifiLatError, wifiLongError, retailerLatError, retailerLongError;

    @FXML
    private JFXDrawer drawer;

    private SQLiteDB db;
    private RetailerDataHandler retailerDataHandler;
    private WifiDataHandler wifiDataHandler;
    private RouteDataHandler routeDataHandler;


    @FXML
    void makeErrorDialogueBox(String errorMessage, String errorDetails) {
        Alert alert = new Alert(Alert.AlertType.ERROR, errorDetails, ButtonType.OK);
        alert.setHeaderText(errorMessage);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            System.out.println("Added entry to database.");
        }
    }
    @FXML
    private void makeSuccessDialogueBox(String errorMessage, String errorDetails) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, errorDetails, ButtonType.OK);
        alert.setHeaderText(errorMessage);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            System.out.println("");
        }
    }

    //Converts Dates to the right format for CSV insertion.
    private String[] convertDates(String date) {
        String dateInts[] = new String[3];
        if (date == null || date.length() != 10) {
            return null;
        }
        dateInts[0] = date.substring(0, 4);
        dateInts[1] = date.substring(5, 7);
        dateInts[2] = date.substring(8);

        return dateInts;
    }




    @FXML
    void routeCSVLine(ActionEvent event) throws IOException {
        double SLatitude = 0, SLongitude = 0, ELatitude = 0, ELongitude = 0;
        boolean errorOccurred = false;
        String[] sDate = new String[3];
        String[] eDate = new String[3];
        String username;
        try {
            username = HandleUsers.currentCyclist.getName();
        } catch(Exception e){
            username = "_";
        }

        try { //Start Date
            sDateError.setVisible(false);
            sDate = convertDates(rSDate.getValue().toString());
            if(sDate == null){
                errorOccurred = true;
                sDateError.setVisible(true);
            }
        }
        catch(Exception e) {
            sDateError.setVisible(true);
            errorOccurred = true;
        }
        try { // Start Latitude
            sLatError.setVisible(false);
            SLatitude = Double.parseDouble(rSLatitude.getText());
        } catch (Exception e) {
            sLatError.setVisible(true);
            errorOccurred = true;

        }
        try { // Start Longitude
            sLongError.setVisible(false);
            SLongitude = Double.parseDouble(rSLongitude.getText());
        } catch (Exception e) {
            errorOccurred = true;
            sLongError.setVisible(true);

        }

        try { // End Date
            eDateError.setVisible(false);
            eDate = convertDates(rEDate.getValue().toString());
            if(eDate == null){
                errorOccurred = true;
                //eDateError.setVisible(true);
            }
        } catch(Exception e){
            eDateError.setVisible(true);
            errorOccurred = true;
        }

        try { //End Latitude
            eLatError.setVisible(false);
            ELatitude = Double.parseDouble(rELatitude.getText());
        } catch (Exception e) {
            errorOccurred = true;
            eLatError.setVisible(true);
        }


        try {// End Longitude
            eLongError.setVisible(false);
            ELongitude = Double.parseDouble(rELongitude.getText());
        } catch (Exception e) {
            eLongError.setVisible(true);
            errorOccurred = true;
        }

        if(rSTime.getText().length() < 1) {
            errorOccurred = true;
            sTimeError.setVisible(true);
        } else {
            sTimeError.setVisible(false);
        }
        if(errorOccurred == true){
            return;
        }
        RouteDataHandler newRoute = new RouteDataHandler(Main.getDB());
        Boolean fromHandler = newRoute.addSingleEntry(0, sDate[0], sDate[1], sDate[2], rSTime.getText(),
                eDate[0], eDate[1], eDate[2], rETime.getText(), "1",
                "Start", SLatitude, SLongitude, "2", "End", ELatitude, ELongitude,
                "1", username, 2017, 1);
        if(fromHandler == false) {
            makeErrorDialogueBox("Something wrong with input", "Check for nulls and already existing entrys");
        } else {
            makeSuccessDialogueBox("Successfully added route to Database", "You may add more entries");
        }
    }


    @FXML
    void retailerCSVLine(ActionEvent event) throws IOException {
        Boolean errorOccured = false;
        Double retLat = 0.0, retLong = 0.0;
        try {
            retailerLongError.setVisible(false);
            retLong = Double.parseDouble(retailerLong.getText());
        } catch (Exception e) {
            retailerLongError.setVisible(true);
            errorOccured = true;
        }

        try {
            retailerLatError.setVisible(false);
            retLat = Double.parseDouble(retailerLat.getText());
        } catch (Exception e) {
            errorOccured = true;
            retailerLatError.setVisible(true);
        }

        if(errorOccured == true){
            return;
        }
        RetailerDataHandler newRetailer = new RetailerDataHandler(Main.getDB());
        Boolean fromHandler = newRetailer.addSingleEntry(retailerName.getText(), retailerAddress.getText(), retLat, retLong, null,
                null, null, retailerPrim.getText(), retailerSec.getText());
        if(fromHandler == false) {
            makeErrorDialogueBox("Something wrong with input", "Check for nulls and already existing entrys");
        } else {
            makeSuccessDialogueBox("Successfully added to retailer to Database", "You may add more entries");
        }
    }

    @FXML
    void wifiCSVLine(ActionEvent event) throws IOException {
        Boolean errorOccured = false;
        Double wLat = 0.0, wLong = 0.0;

        try {
            wifiLongError.setVisible(false);
            wLat = Double.parseDouble(wifiLong.getText());
        } catch (Exception e) {
            wifiLongError.setVisible(true);
            errorOccured = true;
        }

        try {
            wifiLatError.setVisible(false);
            wLong = Double.parseDouble(wifiLat.getText());
        } catch (Exception e) {
            errorOccured = true;
            wifiLatError.setVisible(true);
        }

        if(errorOccured == true){
            return;
        }
        WifiDataHandler newWifi = new WifiDataHandler(Main.getDB());
        Boolean fromHandler = newWifi.addSingleEntry(wifiName.getText(), "", "", wifiAddress.getText(), wLat, wLong,
        wifiComments.getText(), "", wifiName.getText(), "", wifiPostcode.getText());
        if(fromHandler == false) {
            makeErrorDialogueBox("Something wrong with input", "Check for nulls and already existing entrys");
        } else {
            makeSuccessDialogueBox("Successfully added Wifi to Database", "You may add more entries");
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
    void chooseRoute(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        System.out.println(file);
        if(file == null) {
            return;
        }
        routeDataHandler.processCSV(file.toString());
    }
    @FXML //Specifies file types.
    void chooseRetailer(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        System.out.println(file);
        if(file == null) {
            return;
        }
        retailerDataHandler.processCSV(file.toString());
    }
    @FXML
    void chooseWifi(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        System.out.println(file);
        if(file == null) {
            return;
        }
        wifiDataHandler.processCSV(file.toString());
    }
    @FXML
    void changeToRouteEntryScene(ActionEvent event) throws IOException {
        Parent manualEntryParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/manualEntry.fxml"));
        Scene manualEntryScene = new Scene(manualEntryParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(manualEntryScene);
    }

    @FXML
    void changeToWifiEntryScene(ActionEvent event) throws IOException {
        Parent manualEntryParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/wifiEntry.fxml"));
        Scene manualEntryScene = new Scene(manualEntryParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(manualEntryScene);
    }

    @FXML
    void changeToRetailerEntryScene(ActionEvent event) throws IOException {
        Parent manualEntryParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/retailerEntry.fxml"));
        Scene manualEntryScene = new Scene(manualEntryParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(manualEntryScene);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        db = Main.getDB();
        retailerDataHandler = new RetailerDataHandler(db);
        wifiDataHandler = new WifiDataHandler(db);
        routeDataHandler = new RouteDataHandler(db);
    }
}
