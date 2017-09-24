package GUIControllers.ViewDataControllers;

import com.jfoenix.controls.JFXTextField;
import dataAnalysis.Route;
import dataAnalysis.WifiLocation;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;


public class DetailedWifiInformation extends RouteDataViewerController {

    @FXML
    private JFXTextField Zip;

    @FXML
    private JFXTextField address;

    @FXML
    private JFXTextField cost;

    @FXML
    private JFXTextField provider;

    @FXML
    private JFXTextField city;

    @FXML
    private JFXTextField wifiID;

    @FXML
    private JFXTextField longitude;

    @FXML
    private JFXTextField suburb;

    @FXML
    private JFXTextField lattitude;

    @FXML
    private JFXTextField SSID;

    @FXML
    private JFXTextField remarks;

    private WifiLocation currentWifi = null;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        currentWifi = WifiDataViewerController.getWifi();
        wifiID.setText(Double.toString(currentWifi.getWifiID()));
        address.setText(currentWifi.getAddress());
        provider.setText(currentWifi.getProvider());
        cost.setText(currentWifi.getCost());
        lattitude.setText(Double.toString(currentWifi.getLatitude()));
        longitude.setText(Double.toString(currentWifi.getLongitude()));
        remarks.setText(currentWifi.getRemarks());
        city.setText(currentWifi.getCity());
        SSID.setText(currentWifi.getSSID());
        suburb.setText(currentWifi.getSuburb());
        Zip.setText(Integer.toString(currentWifi.getZip()));
    }


    @FXML
    void updateValues(ActionEvent event) {
        currentWifi.setAddress(address.getText());
        currentWifi.setProvider(provider.getText());
        currentWifi.setCost(cost.getText());
        currentWifi.setLatitude(Double.parseDouble(lattitude.getText()));
        currentWifi.setLongitude(Double.parseDouble(longitude.getText()));
        currentWifi.setRemarks(remarks.getText());
        currentWifi.setCity(city.getText());
        currentWifi.setSSID(SSID.getText());
        currentWifi.setSuburb(suburb.getText());
        currentWifi.setZip(Integer.parseInt(Zip.getText()));
    }


}