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

    @FXML
    private SplitPane wifiPanel, retailerPanel, endPanel, startPanel;


    @FXML //This importButton reveals other buttons
    private Button importButton, importRoute, importRetailer, importWifi, addDataButton;

    @FXML
    private Text inputError;

    @FXML
    private JFXTextField rSAddress, rEAddress, rSLongitude, rELongitude, rSLatitude, rELatitude, rSTime, rETime, rSDate, rEDate;

    @FXML
    private JFXDrawer drawer;

    private int singleLineType = 0;
    private SQLiteDB db;
    private RetailerDataHandler retailerDataHandler;
    private WifiDataHandler wifiDataHandler;
    private RouteDataHandler routeDataHandler;

    @FXML
    void openDrawer() throws IOException {
        VBox box = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/SidePanel.fxml"));
        drawer.setSidePane(box);
        if (drawer.isShown()) {
            drawer.close();
        }
        else {
            drawer.open();
        }
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

    //Converts Dates to the right format for CSV insertion.
    private int[] convertDates(String dateLower, String dateUpper) {
        int dateInts[] = new int[6];
        if(dateLower == null || dateUpper == null || dateLower.length() != 10 || dateUpper.length() != 10) {

            System.out.print(dateLower);
            return null;
        }
        dateInts[0] = Integer.parseInt(dateLower.substring(0, 2));
        dateInts[1] = Integer.parseInt(dateLower.substring(3, 5));
        dateInts[2] = Integer.parseInt(dateLower.substring(6));
        dateInts[3] = Integer.parseInt(dateUpper.substring(0, 2));
        dateInts[4] = Integer.parseInt(dateUpper.substring(3, 5));
        dateInts[5] = Integer.parseInt(dateUpper.substring(6));
        return dateInts;
    }


    @FXML //TODO: Add more error types? idfk
    void singleCSVLine(ActionEvent event) throws IOException {
        if(singleLineType == 0) {
            System.out.print("We are here");
            return;
        }

        if(singleLineType == 1){
            double SLatitude, SLongitude, ELatitude, ELongitude;


            int[] dateInts = convertDates(rSDate.getText(), rEDate.getText());
            System.out.println(rSDate.getText() + "accessible date");
            if(dateInts == null) {
                System.out.print("here");
                inputError.setVisible(true);
                return;
            }
            try {
                SLatitude = Double.parseDouble(rSLatitude.getText());
                SLongitude = Double.parseDouble(rSLongitude.getText());
                ELatitude = Double.parseDouble(rELatitude.getText());
                ELongitude = Double.parseDouble(rELongitude.getText());


            } catch(Error e) {
                inputError.setVisible(true);
                return;
            }

            routeDataHandler.addSingleEntry(0, dateInts[2], dateInts[1], dateInts[0], rSTime.getText(),
                    dateInts[6], dateInts[5], dateInts[4], rETime.getText(), "1",
                    "Start", SLatitude, SLongitude, "2", "End", ELatitude, ELongitude,
                    "1", "User", 2017, 1);
        }
        inputError.setVisible(false);
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
