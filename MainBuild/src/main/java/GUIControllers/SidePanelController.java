package GUIControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.Main;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SidePanelController extends Controller implements Initializable{

    @FXML
    private Button addDataButton;

    @FXML
    private Button viewDataButton;

    @FXML
    private Button planRouteButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button logoutButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String userName = Main.hu.currentCyclist.getName();
        logoutButton.setText("Log Out (" + userName + ")");
    }

    @FXML
    public void logOut(ActionEvent event) throws IOException {
        Parent logInParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/startUp.fxml"));
        Scene logInScene = new Scene(logInParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(logInScene);
        Main.hu.logOutOfUser();
    }

    @FXML
    void openHelpStage(ActionEvent event) throws IOException{
        getHelp(event);
    }

}
