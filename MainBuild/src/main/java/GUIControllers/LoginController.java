package GUIControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.HandleUsers;

import java.io.IOException;

public class LoginController {

    @FXML
    public TextField username;

    @FXML
    public void createCyclist() {
        //nameInUse.setVisible(false);
        String name = username.getText();
        boolean created = HandleUsers.createNewUser(name, true);
//        if (created) {
//            System.out.println("Creating cyclist for " + name);
//            // Take user to main screen.
//        } else {
//            nameInUse.setVisible(true);
//        }
    }

    @FXML
    public void createAnalyst() {
        //nameInUse.setVisible(false);
        String name = username.getText();
        HandleUsers.createNewUser(name,false);
        boolean created = HandleUsers.createNewUser(name, false);
//        if (created) {
//            System.out.println("Creating analyst for " + name);
//            // Take user to main screen.
//        } else {
//            nameInUse.setVisible(true);
//        }
    }

    @FXML
    void changeToHomeScene(ActionEvent event) throws IOException {
        Parent homeParent = FXMLLoader.load(getClass().getClassLoader().getResource("home.fxml"));
        Scene homeScene = new Scene(homeParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(homeScene);
    }
}
