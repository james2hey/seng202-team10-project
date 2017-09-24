package GUIControllers.ViewDataControllers;


import com.jfoenix.controls.JFXTextField;
import dataAnalysis.RetailLocation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class DetailedRetailerInformation extends DataViewerController{

    @FXML
    private JFXTextField zip;

    @FXML
    private JFXTextField secondaryType;

    @FXML
    private JFXTextField address;

    @FXML
    private JFXTextField retailerName;

    @FXML
    private JFXTextField latitude;

    @FXML
    private JFXTextField city;

    @FXML
    private JFXTextField state;

    @FXML
    private ComboBox<String> mainType;

    @FXML
    private JFXTextField longitude;

    private RetailLocation currentRetailer = null;

    static private ActionEvent mainAppEvent = null;

    static public void setMainAppEvent(ActionEvent event) {
        mainAppEvent = event;
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        currentRetailer = RetailerDataViewerController.getRetailer();
        retailerName.setText(currentRetailer.getName());
        address.setText(currentRetailer.getAddress());
        latitude.setText(Double.toString(currentRetailer.getLatitude()));
        longitude.setText(Double.toString(currentRetailer.getLongitude()));
        city.setText(currentRetailer.getCity());
        state.setText(currentRetailer.getState());
        zip.setText(Integer.toString(currentRetailer.getZip()));
        mainType.getSelectionModel().select(currentRetailer.getMainType());
        secondaryType.setText(currentRetailer.getSecondaryType());

    }

    @FXML
    void updateValues(ActionEvent event) throws IOException{
        currentRetailer.setAddress(address.getText());
        currentRetailer.setLatitude(Double.parseDouble(latitude.getText()));
        currentRetailer.setLongitude(Double.parseDouble(longitude.getText()));
        currentRetailer.setCity(city.getText());
        currentRetailer.setState(state.getText());
        currentRetailer.setZip(Integer.parseInt(zip.getText()));
        currentRetailer.setMainType(mainType.getSelectionModel().getSelectedItem());
        currentRetailer.setSecondaryType(secondaryType.getText());

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        showRetailers(mainAppEvent);
    }

    void shutDown(ActionEvent event) {

    }

}
