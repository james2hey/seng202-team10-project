package GUIControllers;

import com.google.maps.errors.ApiException;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXTextField;
import dataHandler.*;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.*;
import main.HelperFunctions;
import main.Main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Controller class for the add data scene.
 */
public class AddDataController extends Controller implements Initializable {

    @FXML //This importButton reveals other buttons
    private Button importRoute, importRetailer, importWifi;

    @FXML // Route Errors
    private Text sTimeError, sDateError, eTimeError, eDateError;

    @FXML //Route Fields
    private JFXTextField rSAddress, rEAddress, rSTime, rETime;

    @FXML // Retailer Fields
    private JFXTextField retailerName, retailerAddress, retailerSec;

    @FXML
    private ComboBox retailerPrim;

    @FXML
    private DatePicker rSDate, rEDate;

    @FXML // Wifi Fields
    private JFXTextField wifiName, wifiAddress, wifiPostcode, wifiComments;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private Text selectMessage;

    private SQLiteDB db;
    public static String startAddress = "";
    public static String endAddress = "";

    /**
     * Runs on successfully loading the fxml. This initialises the data handlers so that CSV's can be imported in
     * the future.
     *
     * @param location Location of the fxml
     * @param resources Locale-specific data required for the method to run automatically
     */
    public void initialize(URL location, ResourceBundle resources) {
        if (location.equals(getClass().getClassLoader().getResource("FXML/routeManualEntry.fxml"))) {
            System.out.println(rSAddress.getText());
            System.out.println(startAddress);
            rSAddress.setText(startAddress);
            rEAddress.setText(endAddress);
        }

        System.out.println(location);
        db = Main.getDB();
    }

    /**
     * Sets values of address from the PlanRoute scene.
     *
     * @param newStartAddress Starting address for route.
     * @param newEndAddress Starting address for route.
     */
    public static void setRouteVals(String newStartAddress, String newEndAddress) {
        startAddress = newStartAddress;
        endAddress = newEndAddress;
    }

    /**
     * Collects, formats and checks for valid data input by user
     * to create a valid entry into the route section of the local database.
     * Contains calls to convertDates and routeDataHandler.
     * @param event Clicking the Add to Database button on the manual entry page.
     * @throws IOException
     */
    @FXML
    void routeCSVLine(ActionEvent event) throws IOException {
        double SLatitude, SLongitude, ELatitude, ELongitude;
        boolean errorOccurred = false;
        String[] sDate = new String[3];
        String[] eDate = new String[3];

        double[] sLatLon;
        double[] eLatLon;

        try {
            if (rSAddress.getText().length() != 0 || rEAddress.getText().length() != 0) {
                sLatLon = Geocoder.addressToLatLon(rSAddress.getText());
                eLatLon = Geocoder.addressToLatLon(rEAddress.getText());
            } else {
                makeErrorDialogueBox("Something wrong with input", "Fill all required fields\nCheck entry is not already in database");
                return;
            }
        } catch (ApiException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("API Error");
            alert.setHeaderText("Google API Error");
            alert.setContentText("The application could not get the lat and lon because there was an issue with the API");
            alert.showAndWait();
            return;
        } catch (IOException | InterruptedException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("IO Error");
            alert.setHeaderText("Input Error");
            alert.setContentText("The application could not convert the address as it could not connect to the internet");
            alert.showAndWait();
            return;
        }
        SLatitude = sLatLon[0];
        SLongitude = sLatLon[1];
        ELatitude = eLatLon[0];
        ELongitude = eLatLon[1];

        String username;
        try {
            username = Main.hu.currentCyclist.getName();
        } catch (Exception e) {
            username = "_";
        }

        try { //Start Date
            sDateError.setVisible(false);
            sDate = HelperFunctions.convertDates(rSDate.getValue().toString());
            if (sDate == null) {
                errorOccurred = true;
                sDateError.setVisible(true);
            }
        } catch (Exception e) {
            sDateError.setVisible(true);
            errorOccurred = true;
        }

        try { // End Date
            eDateError.setVisible(false);
            eDate = HelperFunctions.convertDates(rEDate.getValue().toString());
            if (eDate == null) {
                errorOccurred = true;
                //eDateError.setVisible(true);
            }
        } catch (Exception e) {
            eDateError.setVisible(true);
            errorOccurred = true;
        }

        if (HelperFunctions.checkTime(rSTime.getText())) {
            sTimeError.setVisible(false);
        } else {
            errorOccurred = true;
            sTimeError.setVisible(true);
        }

        if (HelperFunctions.checkTime(rETime.getText())) {
            eTimeError.setVisible(false);
        } else {
            eTimeError.setVisible(true);
            errorOccurred = true;
        }
        if (errorOccurred) {
            makeErrorDialogueBox("Something wrong with input", "Fill all required fields\nCheck entry is not already in database");
            return;
        }
        System.out.println(rSTime.getText());
        System.out.println(rSTime.getText());
        int duration = HelperFunctions.getDuration(sDate[0], sDate[1], sDate[2], rSTime.getText(),
                eDate[0], eDate[1], eDate[2], rETime.getText());
        RouteDataHandler newRoute = new RouteDataHandler(Main.getDB());
        Boolean fromHandler = newRoute.addSingleEntry(duration, sDate[0], sDate[1], sDate[2], rSTime.getText(),
                eDate[0], eDate[1], eDate[2], rETime.getText(), "1",
                rSAddress.getText(), SLatitude, SLongitude, "2", rEAddress.getText(), ELatitude, ELongitude,
                "1", username, Main.hu.currentCyclist.getBirthYear(), Main.hu.currentCyclist.getGender());
        //get distance
        //
        if (fromHandler == false) {
            makeErrorDialogueBox("Something wrong with input", "Fill all required fields\nCheck entry is not already in database");
        } else {
            makeSuccessDialogueBox("Successfully added route to Database", "You may add more entries");
        }
    }

    /**
     * Collects, formats and checks for valid data input by user
     * to create a valid entry into the retailer section of the local database.
     * Contains calls to convertDates and retailerDataHandler.
     *
     * @param event Clicking the Add to Database button on the retailer entry page.
     * @throws IOException
     */
    @FXML
    void retailerCSVLine(ActionEvent event) throws IOException {
        Boolean errorOccured = false;
        double[] latLon;
        try {
            if (retailerAddress.getText().length() != 0) {
                latLon = Geocoder.addressToLatLon(retailerAddress.getText());
            } else {
                makeErrorDialogueBox("Something wrong with input", "Fill all required fields\nCheck entry is not already in database");
                return;
            }
        } catch (ApiException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("API Error");
            alert.setHeaderText("Google API Error");
            alert.setContentText("The application could not get the lat and lon because there was an issue with the API");
            alert.showAndWait();
            return;
        } catch (IOException | InterruptedException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("IO Error");
            alert.setHeaderText("Input Error");
            alert.setContentText("The application could not convert the address as it could not connect to the internet");
            alert.showAndWait();
            return;
        }

        if (retailerAddress.getText() == null) {
            errorOccured = true;
        }
        if (retailerName.getText() == null) {
            errorOccured = true;
        }
        if (retailerPrim.getSelectionModel().isEmpty()) {
            errorOccured = true;
        }
        if (retailerSec.getText() == null) {
            errorOccured = true;
        }
        if (!errorOccured) {
            RetailerDataHandler newRetailer = new RetailerDataHandler(Main.getDB());
            Boolean fromHandler = newRetailer.addSingleEntry(retailerName.getText(), retailerAddress.getText(), latLon[0], latLon[1], null,
                    null, null, retailerPrim.getValue().toString(), retailerSec.getText());
            if (!fromHandler) {
                makeErrorDialogueBox("Something wrong with input", "Fill all required fields\nCheck entry is not already in database");
            } else {
                makeSuccessDialogueBox("Successfully added to retailer to Database", "You may add more entries");
            }
        } else {
            makeErrorDialogueBox("Something wrong with input", "Fill all required fields\nCheck entry is not already in database");
        }
    }

    /**
     * Collects, formats and checks for valid data input by user
     * to create a valid entry into the wifi section of the local database.
     * Contains calls to convertDates and wifiDataHandler.
     *
     * @param event Clicking the Add to Database button on the wifi entry page.
     * @throws IOException
     */
    @FXML
    void wifiCSVLine(ActionEvent event) throws IOException {
        Boolean errorOccured = false;
        double[] latLon;

        try {
            if (wifiAddress.getText().length() != 0) {
                latLon = Geocoder.addressToLatLon(wifiAddress.getText());
            } else {
                makeErrorDialogueBox("Something wrong with input", "Fill all required fields\nCheck entry is not already in database");
                return;
            }
        } catch (ApiException e) {
            makeErrorDialogueBox("Google API Error", "The application could not get the lat and lon\nThere was an issue with the API");
            return;
        } catch (IOException | InterruptedException e) {
            makeErrorDialogueBox("Input Error", "The application could not convert the address \nIt could not connect to the internet");
            return;
        } catch (ArrayIndexOutOfBoundsException e) {
            makeErrorDialogueBox("Input Error", "Invalid address.");
            return;

        }

        if (wifiName.getText() == null) {
            errorOccured = true;
        }
        if (wifiAddress.getText() == null) {
            errorOccured = true;
        }

        if (!errorOccured) {

            WifiDataHandler newWifi = new WifiDataHandler(Main.getDB());
            Boolean fromHandler = newWifi.addSingleEntry(wifiName.getText(), "", "", wifiAddress.getText(), latLon[0], latLon[1],
                    wifiComments.getText(), "", wifiName.getText(), "", wifiPostcode.getText());
            if (!fromHandler) {
                makeErrorDialogueBox("Something wrong with input", "Fill all required fields\nCheck entry is not already in database");
            } else {
                makeSuccessDialogueBox("Successfully added Wifi to Database", "You may add more entries");
            }
        } else {
            makeErrorDialogueBox("Something wrong with input", "Fill all required fields\nCheck entry is not already in database");
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
        if (!importRoute.isVisible()) {
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
    @FXML
    // Import file -> only allows *.csv and prints location afterwards for now...
    void chooseRoute(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        System.out.println(file);
        if (file == null) {
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/progressPopup.fxml"));
        Parent progressParent = loader.load();
        ProgressPopupController controller = loader.getController();
        Scene progressScene = new Scene(progressParent);
        Stage dialogStage = new Stage();
        dialogStage.initStyle(StageStyle.UTILITY);
        dialogStage.setResizable(false);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setScene(progressScene);
        dialogStage.show();

        RouteDataHandler handler = new RouteDataHandler(db);
        Task<Void> task = new CSVImporter(db, file.toString(), handler);
        controller.activateProgressBar(task);
        Thread thread = new Thread(task);
        thread.start();
        dialogStage.setOnCloseRequest(e -> task.cancel());
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
        boolean result = makeConfirmationDialogueBox("Warning! Depending on file size, this may take a few minutes.", "Are you sure you want to continue?");
        if (result) {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV (*.csv)", "*.csv");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
            System.out.println(file);
            if (file == null) {
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/progressPopup.fxml"));
            Parent progressParent = loader.load();
            ProgressPopupController controller = loader.getController();
            Scene progressScene = new Scene(progressParent);
            Stage dialogStage = new Stage();
            dialogStage.initStyle(StageStyle.UTILITY);
            dialogStage.setResizable(false);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(progressScene);
            dialogStage.show();

            RetailerDataHandler handler = new RetailerDataHandler(db);
            Task<Void> task = new CSVImporter(db, file.toString(), handler);
            controller.activateProgressBar(task);
            Thread thread = new Thread(task);
            thread.start();
            dialogStage.setOnCloseRequest(e -> task.cancel());
        }
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
        if (file == null) {
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/progressPopup.fxml"));
        Parent progressParent = loader.load();
        ProgressPopupController controller = loader.getController();
        Scene progressScene = new Scene(progressParent);
        Stage dialogStage = new Stage();
        dialogStage.initStyle(StageStyle.UTILITY);
        dialogStage.setResizable(false);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setScene(progressScene);
        dialogStage.show();

        WifiDataHandler handler = new WifiDataHandler(db);
        Task<Void> task = new CSVImporter(db, file.toString(), handler);
        controller.activateProgressBar(task);
        Thread thread = new Thread(task);
        thread.start();
        dialogStage.setOnCloseRequest(e -> task.cancel());
    }

    /**
     * Changes the current scene to the Route entry scene.
     *
     * @param event Created when the method is called
     * @throws IOException Handles errors caused by an fxml not loading correctly
     */
    @FXML
    void changeToRouteEntryScene(ActionEvent event) throws IOException {
        Parent manualEntryParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/routeManualEntry.fxml"));
        Scene manualEntryScene = new Scene(manualEntryParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(manualEntryScene);
    }

    /**
     * Changes the current scene to the wifi entry scene.
     * @param event Created when the method is called
     * @throws IOException Handles errors caused by an fxml not loading correctly
     */
    @FXML
    void changeToWifiEntryScene(ActionEvent event) throws IOException {
        Parent manualEntryParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/wifiManualEntry.fxml"));
        Scene manualEntryScene = new Scene(manualEntryParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(manualEntryScene);
    }

    /**
     * Changes the current scene to the Retailer entry scene
     *
     * @param event Created when the method is called
     * @throws IOException Handles errors caused by an fxml not loading correctly
     */
    @FXML
    void changeToRetailerEntryScene(ActionEvent event) throws IOException {
        Parent manualEntryParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/retailerManualEntry.fxml"));
        Scene manualEntryScene = new Scene(manualEntryParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(manualEntryScene);
    }
}
