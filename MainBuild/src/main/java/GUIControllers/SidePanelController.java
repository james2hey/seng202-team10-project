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

/**
 * Controller class for the side panel.
 */

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

    /**
     * Runs on loading the side panel. Sets the username for the logout button.
     * @param location Location of the fxml
     * @param resources Locale-specific data required for the method to run automatically
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String userName = Main.hu.currentCyclist.getName();
        logoutButton.setText("Log Out (" + userName + ")");
    }

    /**
     * Runs when the log out button is pressed. Changes the scene to the log in scene and signs out the current user.
     * @param event Created when the method is called
     * @throws IOException Handles errors caused by an fxml not loading correctly
     */
    @FXML
    public void logOut(ActionEvent event) throws IOException {
        Parent logInParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/startUp.fxml"));
        Scene logInScene = new Scene(logInParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(logInScene);
        Main.hu.logOutOfUser();
    }

    /**
     * Opens the help menu.
     * @param event Created when the method is called
     * @throws IOException Handles errors caused by an fxml not loading correctly
     */
    @FXML
    void openHelpStage(ActionEvent event) throws IOException{
        getHelp(event);
    }

}
