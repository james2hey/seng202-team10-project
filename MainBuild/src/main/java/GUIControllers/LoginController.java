package GUIControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.HandleUsers;
import main.Main;

import java.io.IOException;
import java.util.ArrayList;

public class LoginController {

    @FXML
    public TextField username;

    @FXML
    public Text nameInUse;

    @FXML
    public static ComboBox<String> existingUsers = new ComboBox<>();

    @FXML
    public TextField existingUser;

    public static void main(String[] args) {
        existingUsers.getItems().addAll("1", "2", "3");
        existingUsers.setEditable(true);
    }

//    public static void fillComboBox(ArrayList<String> userList) {
//
//        existingUsers.getItems().addAll(
//                "FOUR",
//                "FIVE"
//        );
//
//        ObservableList<String> options = FXCollections.observableArrayList(
//                "ONE",
//                "TWO",
//                "THREE"
//        );
//        System.out.println("Here");
//
//        existingUsers = new ComboBox(options);
//
//    }

    @FXML
    public void createCyclist() {
        nameInUse.setVisible(false);
        String name = username.getText();
        boolean created = HandleUsers.createNewUser(name, true);
        if (created) {
            System.out.println("Creating cyclist for " + name);
            // Take user to main screen.
        } else {
            nameInUse.setVisible(true);
            username.setText("");
        }
    }

    @FXML
    public void createAnalyst() throws IOException {
        nameInUse.setVisible(false);
        String name = username.getText();
        System.out.println(name);
        HandleUsers.createNewUser(name,false);
        boolean created = HandleUsers.createNewUser(name, false);
        if (created) {
            System.out.println("Creating analyst for " + name);
            //changeToHomeScene(event);
        } else {
            nameInUse.setVisible(true);
            username.setText("");
        }
    }

    @FXML
    void changeToHomeScene(ActionEvent event) throws IOException {
        Parent homeParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/home.fxml"));
        Scene homeScene = new Scene(homeParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(homeScene);

        String name = existingUser.getText();
        HandleUsers.logIn(name, true); // Currently only creating cyclists on sign in.
    }
}
