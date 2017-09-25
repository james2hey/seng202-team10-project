package GUIControllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXTextField;
import dataHandler.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
    private DatePicker rSDate, rEDate;

    @FXML // Wifi Fields
    private JFXTextField wifiName, wifiLong, wifiLat, wifiAddress, wifiPostcode, wifiComments;

    @FXML // Wifi & Retailer Errors
    private Text wifiLatError, wifiLongError, retailerLatError, retailerLongError;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private Text selectMessage;

    private SQLiteDB db;
    private RetailerDataHandler retailerDataHandler;
    private WifiDataHandler wifiDataHandler;
    private RouteDataHandler routeDataHandler;
    public static String startAddress = "";
    public static String endAddress = "";


    public static void setRouteVals(String newStartAddress, String newEndAddress) {
        startAddress = newStartAddress;
        endAddress = newEndAddress;
    }

    /**
     * Turns the date input into a three part String array where
     * the values are year, month, day respectively.
     *
     * @param date String of the form yyyy-MM-dd
     */
    public static String[] convertDates(String date) {
        String dateInts[] = new String[3];
        if (date == null || date.length() != 10 || date.charAt(4) != '-'  || date.charAt(7) != '-') {
            return null;
        }
        dateInts[0] = date.substring(0, 4);
        dateInts[1] = date.substring(5, 7);
        dateInts[2] = date.substring(8);
        if(Integer.parseInt(dateInts[1]) > 12 || Integer.parseInt(dateInts[2]) > 31){
            return null;
        }

        return dateInts;
    }

    public static Boolean checkTime(String time){
        if (time == null || time.length() != 8 || time.charAt(2) != ':'  || time.charAt(5) != ':') {
            return false;
        }
        if(Integer.parseInt(time.substring(0, 2)) > 23 || Integer.parseInt(time.substring(3, 5)) > 59 || Integer.parseInt(time.substring(6, 8)) > 59
        || Integer.parseInt(time.substring(0, 2)) < 0 || Integer.parseInt(time.substring(3, 5)) < 0 || Integer.parseInt(time.substring(6, 8)) < 0){
            return false;
        }
        return true;
    }

    /**
     * Collects, formats and checks for valid data input by user
     *  to create a valid entry into the route section of the local database.
     * Contains calls to convertDates and routeDataHandler.
     * @param event Clicking the Add to Database button on the manual entry page.
     * @throws IOException
     */
    @FXML
    void routeCSVLine(ActionEvent event) throws IOException {
        double SLatitude = 0, SLongitude = 0, ELatitude = 0, ELongitude = 0;
        boolean errorOccurred = false;
        String[] sDate = new String[3];
        String[] eDate = new String[3];

        String username;
        try {
            username = Main.hu.currentCyclist.getName();
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

        if(checkTime(rSTime.getText())) {
            sTimeError.setVisible(false);
        } else {
            errorOccurred = true;
            sTimeError.setVisible(true);
        }

        if(checkTime(rETime.getText())) {
            eTimeError.setVisible(false);
        } else {
            eTimeError.setVisible(true);
            errorOccurred = true;
        }
        if(errorOccurred == true){
            return;
        }
        System.out.println(rSTime.getText());
        System.out.println(rSTime.getText());
        int duration = routeDataHandler.getDuration(sDate[0], sDate[1], sDate[2], rSTime.getText(),
                eDate[0], eDate[1], eDate[2], rETime.getText());
        RouteDataHandler newRoute = new RouteDataHandler(Main.getDB());
        Boolean fromHandler = newRoute.addSingleEntry(duration, sDate[0], sDate[1], sDate[2], rSTime.getText(),
                eDate[0], eDate[1], eDate[2], rETime.getText(), "1",
                rSAddress.getText(), SLatitude, SLongitude, "2", rEAddress.getText(), ELatitude, ELongitude,
                "1", username, 2017, 3);
        if(fromHandler == false) {
            makeErrorDialogueBox("Something wrong with input", "Check for nulls and already existing entrys");
        } else {
            makeSuccessDialogueBox("Successfully added route to Database", "You may add more entries");
        }
    }

    /**
     * Collects, formats and checks for valid data input by user
     *  to create a valid entry into the retailer section of the local database.
     * Contains calls to convertDates and retailerDataHandler.
     *
     * @param event Clicking the Add to Database button on the retailer entry page.
     * @throws IOException
     */
    @FXML
    void retailerCSVLine(ActionEvent event) throws IOException {
        Boolean errorOccured = false;
        double[] latLon;
        double retLat = 0;
        double retLon = 0;

        if ((latLon = Geocoder.addressToLatLon(retailerAddress.getText())) == null) {
            return;
        }

        if(errorOccured == true){
            return;
        }
        RetailerDataHandler newRetailer = new RetailerDataHandler(Main.getDB());
        Boolean fromHandler = newRetailer.addSingleEntry(retailerName.getText(), retailerAddress.getText(), retLat, retLon, null,
                null, null, retailerPrim.getText(), retailerSec.getText());
        if(fromHandler == false) {
            makeErrorDialogueBox("Something wrong with input", "Check for nulls and already existing entrys");
        } else {
            makeSuccessDialogueBox("Successfully added to retailer to Database", "You may add more entries");
        }
    }

    /**
     * Collects, formats and checks for valid data input by user
     *  to create a valid entry into the wifi section of the local database.
     * Contains calls to convertDates and wifiDataHandler.
     *
     * @param event Clicking the Add to Database button on the wifi entry page.
     * @throws IOException
     */
    @FXML
    void wifiCSVLine(ActionEvent event) throws IOException {
        Boolean errorOccured = false;
        double[] latLon;

        if ((latLon = Geocoder.addressToLatLon(wifiAddress.getText())) == null) {
            return;
        }

        double wLat = 0.0, wLong = 0.0;

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


    /**
     * Simple function to make the separate import buttons
     * (Routes, Retailers and Wifi Hot-spots)
     * visible to the user.
     *
     * @param event Clicking the File Import Button.
     * @throws IOException
     */
    @FXML
    void chooseFile(ActionEvent event) throws IOException {
        if(!importRoute.isVisible()){
            selectMessage.setVisible(true);
            importRoute.setVisible(true);
            importRetailer.setVisible(true);
            importWifi.setVisible(true);
        }
    }



    /**
     * Opens system file viewer and accepts only *.csv type files,
     * will print the file string to system and send it to the
     * routeDataHandler.
     *
     * @param event Clicking the Routes button after Import File.
     * @throws IOException
     */
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

    /**
     * Opens system file viewer and accepts only *.csv type files,
     * will print the file string to system and send it to the
     * retailerDataHandler.
     *
     * @param event Clicking the Retailers button after Import File.
     * @throws IOException
     */
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
    /**
     * Opens system file viewer and accepts only *.csv type files,
     * will print the file string to system and send it to the
     * wifiDataHandler.
     *
     * @param event Clicking the Wifi button after Import File.
     * @throws IOException
     */
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
    void changeToRouteEntryScene(ActionEvent event, Stage stage) throws IOException {
        Parent manualEntryParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/manualEntry.fxml"));
        Scene manualEntryScene = new Scene(manualEntryParent);
        stage.setScene(manualEntryScene);
    }
//
//    @FXML
//    void changeToRouteEntryScene(ActionEvent event, String startAddress, String endAddress, Stage stage) throws IOException {
////        Parent manualEntryParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/manualEntry.fxml"));
////        Scene manualEntryScene = new Scene(manualEntryParent);
////        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
////        currentStage.setScene(manualEntryScene);
//
//        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/manualEntry.fxml"));
//        Scene manualEntryScene = new Scene(loader.load());
//
//        AddDataController controller = loader.<AddDataController>getController();
//        controller.setRouteVals(startAddress, endAddress);
//        controller.rSAddress.setText(startAddress);
//        stage.setScene(manualEntryScene);
//        //controller.changeToRouteEntryScene(event, startAddress, endAddress);
//
//    }

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
        if (location.equals(getClass().getClassLoader().getResource("FXML/manualEntry.fxml"))) {
            System.out.println(rSAddress.getText());
            System.out.println(startAddress);
            rSAddress.setText(startAddress);
            rEAddress.setText(endAddress);
        }

        System.out.println(location);
        db = Main.getDB();
        retailerDataHandler = new RetailerDataHandler(db);
        wifiDataHandler = new WifiDataHandler(db);
        routeDataHandler = new RouteDataHandler(db);
    }
}
