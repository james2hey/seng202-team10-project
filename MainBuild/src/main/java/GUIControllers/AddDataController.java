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
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.HandleUsers;
import main.Main;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by bal65 on 16/09/17.
 */
public class AddDataController extends Controller implements Initializable {

    @FXML
    private Button manualEntryButton;

    @FXML
    private Button routeButton, wifiButton, retailerButton;


    @FXML //This importButton reveals other buttons
    private Button importButton, importRoute, importRetailer, importWifi, addDataButton;

    @FXML // Route Errors
    private Text sLongError, sLatError, sTimeError, sDateError, eLongError, eLatError, eTimeError, eDateError;

    @FXML //Route Fields
    private JFXTextField rSAddress, rEAddress, rSLongitude, rELongitude, rSLatitude, rELatitude, rSTime, rETime, rSDate, rEDate;

    @FXML // Retailer Fields
    private JFXTextField retailerName, retailerAddress, retailerLong, retailerLat, retailerPrim, retailerSec;

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


    //Converts Dates to the right format for CSV insertion.
    private int[] convertDates(String date) {
        int dateInts[] = new int[3];
        if (date == null || date.length() != 10) {
            return null;
        }
        dateInts[0] = Integer.parseInt(date.substring(0, 2));
        dateInts[1] = Integer.parseInt(date.substring(3, 5));
        dateInts[2] = Integer.parseInt(date.substring(6));

        return dateInts;
    }


    @FXML
        //TODO: Add more error types? idfk
    void routeCSVLine(ActionEvent event) throws IOException {
        double SLatitude = 0, SLongitude = 0, ELatitude = 0, ELongitude = 0;
        boolean errorOccured = false;
        int[] sDate = new int[3];
        int[] eDate = new int[3];
        String username = HandleUsers.currentCyclist.getName();

        try { //Start Date
            sDateError.setVisible(false);
            sDate = convertDates(rSDate.getText());
            if(sDate == null){
                errorOccured = true;
                sDateError.setVisible(true);
            }
        }
        catch(Exception e) {
            sDateError.setVisible(true);
            errorOccured = true;
        }
        try { // Start Latitude
            sLatError.setVisible(false);
            SLatitude = Double.parseDouble(rSLatitude.getText());
        } catch (Exception e) {
            sLatError.setVisible(true);
            errorOccured = true;

        }
        try { // Start Longitude
            sLongError.setVisible(false);
            SLongitude = Double.parseDouble(rSLongitude.getText());
        } catch (Exception e) {
            errorOccured = true;
            sLongError.setVisible(true);

        }

        try { // End Date
            eDateError.setVisible(false);
            eDate = convertDates(rSDate.getText());
            if(eDate == null){
                errorOccured = true;
                //eDateError.setVisible(true);
            }
        } catch(Exception e){
            eDateError.setVisible(true);
            errorOccured = true;
        }

        try { //End Latitude
            eLatError.setVisible(false);
            ELatitude = Double.parseDouble(rELatitude.getText());
        } catch (Exception e) {
            errorOccured = true;
            eLatError.setVisible(true);
        }


        try {// End Longitude
            eLongError.setVisible(false);
            ELongitude = Double.parseDouble(rELongitude.getText());
        } catch (Exception e) {
            eLongError.setVisible(true);
            errorOccured = true;
        }




        if(errorOccured == true){
            return;
        }

        /*RouteDataHandler newRoute = new RouteDataHandler();
        Boolean fromHandler = newRoute.addSingleEntry(0, sDate[2], sDate[1], sDate[0], rSTime.getText(),
                eDate[2], eDate[1], eDate[0], rETime.getText(), "1",
                "Start", SLatitude, SLongitude, "2", "End", ELatitude, ELongitude,
                "1", username, 2017, 1);*/

    }


    @FXML
        //TODO: Add more error types? idfk
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
        /*RetailerDataHandler newRetailer = new RetailerDataHandler();
        Boolean fromHandler = newRetailer.addSingleEntry(retailerName.getText(), retailerAddress.getText(), retLat, retLong, null,
                null, null, retailerPrim.getText(), retailerSec.getText());*/
    }

    @FXML
        //TODO: Add more error types? idfk
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
        /*WifiDataHandler newWifi = new RetailerDataHandler();
        Boolean fromHandler = newWifi.addSingleEntry(wifiName.getText(), "", "", wifiAddress.getText(), wLat, wLong,
        wifiComments.getText(), "", "", "", wifiPostcode.getText());*/
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
