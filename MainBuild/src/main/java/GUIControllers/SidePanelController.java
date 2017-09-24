package GUIControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.HandleUsers;
import main.Main;

import java.io.IOException;

public class SidePanelController extends Controller{

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
     * Sets the log out text so that the user can identify which account they are logging out of.
     * @param textToAdd text to be added to the log out button.
     */
    public void setLogOutText(String textToAdd) {
        logoutButton.setText("Log Out (" + textToAdd + ")");
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
