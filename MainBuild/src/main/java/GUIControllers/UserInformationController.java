package GUIControllers;

import GUIControllers.Controller;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Cyclist;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static main.Cyclist.*;
import static main.Cyclist.getBirthYear;


/**
 * Controller for the user information scene.
 */
public class UserInformationController extends Controller implements Initializable {

    @FXML
    private Text longestRoute;

    @FXML
    private ComboBox<String> gender;

    @FXML
    private DatePicker dob;

    @FXML
    private Text mostVisitedRetailer;

    @FXML
    private JFXTextField name;

    @FXML
    private Text shortestRoute;

    @FXML
    private Text totalDistance;

    @FXML
    private JFXHamburger hamburger;



    /**
     * Runs on start up. Sets the values of the user profile to what is currently saved in the database.
     * Also, calculates the User statistics and displays them for the user.
     * @param location Location of the fxml
     * @param resources Locale-specific data required for the method to run automatically
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        name.setText(getName());
        dob.setValue(LocalDate.of(getBirthYear(), getBmonth(), getBDay()));
        gender.getSelectionModel().select(getGender());
    }


    /**
     * Called when the update profile button is pressed. Gets the information currently in the GUI fields and sets the
     * current users information to them. Alerts user of success/failure with dialogue boxes.
     * @param event Created on pressing the button. Not used.
     */
    @FXML
    void updateProfile(ActionEvent event) {
        try {
            LocalDate newDOB = dob.getValue();
            String newDOBString = newDOB.toString();
            int newYear = Integer.parseInt(newDOBString.split("-")[0]);
            int newMonth = Integer.parseInt(newDOBString.split("-")[1]);
            int newDay = Integer.parseInt(newDOBString.split("-")[2]);
            setBirthday(newDay, newMonth, newYear);
            if (gender.getSelectionModel().getSelectedItem() == "Male") {
                setGender(1);
            } else if (gender.getSelectionModel().getSelectedItem() == "Female") {
                setGender(2);
            } else if (gender.getSelectionModel().getSelectedItem() == "Other") {
                setGender(3);
            } else {
                setGender(0);
            }
            setName(name.getText());
            makeSuccessDialogueBox("Success!", "Your profile has been successfully updated.");
        } catch (Exception e) {
            makeErrorDialogueBox("Failed", "An error occurred while updating your profile.");
        }
    }


    /**
     * Runs when the log out button is pressed. Changes the scene to the log in scene and signs out the current user.
     * @param event Created when the method is called
     * @throws IOException Handles errors caused by an fxml not loading correctly
     */
    @FXML
    public void logout(ActionEvent event) throws IOException {
        Parent logInParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/startUp.fxml"));
        Scene logInScene = new Scene(logInParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(logInScene);
        Main.hu.logOutOfUser();
    }

}
