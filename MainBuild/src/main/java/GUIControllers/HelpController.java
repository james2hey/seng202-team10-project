package GUIControllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;


public class HelpController implements Initializable{

    @FXML
    private Text helpText;


    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        String helpMessage = "Welcome to <app_name>!\n\n" +
                "Creating and Managing Users\n" +
                "Up to 6 users may be created on one system of any type from the sign up field. When creating a " +
                "user, note that there are two types of users: Cyclists - Can store all their favourite routes, " +
                "wifi locations and retail stores. They may also plan their own trips in which they can stop off " +
                "at a retail store or wifi hot spot on the way. Analysts - ~~nothing really~~." +
                "Navigation\n" +
                "In order to navigate through different features, click on the three black bars in the " +
                "top right of the screen to draw out each option.\n\n" +
                "Home Screen\n" +
                "What it does" +
                "Plan Route\n" +
                "**how this works**\n\n" +
                "Add Data\n" +
                "**how this works**\n\n" +
                "View Data\n" +
                "**how this works\n\n";
        helpText.setText(helpMessage);
    }



}
