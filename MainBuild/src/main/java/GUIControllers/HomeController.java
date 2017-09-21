package GUIControllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.HandleUsers;

import java.io.IOException;

public class HomeController extends Controller{

    @FXML
    public Button logOutButton;

    @FXML
    void login() {
        boolean answer;
        answer = makeConfirmationDialogueBox("Warning", "Are you sure you want to exit?");
        if (answer) {
            System.exit(0);
        }
    }

    @FXML
    public void logOut(ActionEvent event) throws IOException {
        Parent logInParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/startUp.fxml"));
        Scene logInScene = new Scene(logInParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(logInScene);
        HandleUsers.logOutOfUser();
    }

}
