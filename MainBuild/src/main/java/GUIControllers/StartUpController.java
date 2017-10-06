package GUIControllers;

import dataHandler.SQLiteDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.HelperFunctions;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static java.lang.Character.isLetter;
import static org.apache.commons.lang3.StringUtils.isNumeric;

/**
 * Handles the logging in scene of the GUI.
 */
public class StartUpController extends Controller implements Initializable {

    @FXML
    private TextField username;

    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private TextField userday, usermonth, useryear;
    @FXML
    private ComboBox<String> usergender;

    /**
     * @param location;
     * @param resources;
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SQLiteDB db = Main.getDB();
        try {
            ResultSet rs = db.executeQuerySQL("SELECT * FROM users");
            while (rs.next()) {
                comboBox.getItems().add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Creates new Cyclist instance with the given user name if it doesn't already exist in the database. Otherwise ----needs testsssss-----
     * it creates an error dialog box informing the use this name is already taken. Also throws error dialog boxes
     * for having no entries for a log in field or any incorrectly formatted dates.
     *
     * @param event clicking the sign up button
     * @throws IOException
     */
    @FXML
    public void createCyclist(ActionEvent event) throws IOException {
        String name = username.getText();
        boolean noNullEntries = true;
        if (name.equals("") || !isLetter(name.charAt(0))) {
            makeErrorDialogueBox("Enter a valid name", "Valid names must have at least one " +
                    "character\nand start with a letter.");
            noNullEntries = false;
        }
        if (usergender.getValue() == null) {
            makeErrorDialogueBox("Select a gender", "No gender was selected. Please select " +
                    "a gender\n from the drop down box or 'other' if you would rather not specify this.");
            noNullEntries = false;
        }
        if (userday.getText().equals("") || usermonth.getText().equals("") || useryear.getText().equals("")
                || !isNumeric(userday.getText()) || !isNumeric(usermonth.getText()) || !isNumeric(useryear.getText())) {
            makeErrorDialogueBox("Enter a valid birth date", "Use the format DD/MM/YYYY");
            noNullEntries = false;
        }
        if (noNullEntries) {
            String gender = usergender.getSelectionModel().getSelectedItem();
            int day = Integer.parseInt(userday.getText());
            int month = Integer.parseInt(usermonth.getText());
            int year = Integer.parseInt(useryear.getText());

            boolean dateError = HelperFunctions.checkDateDetails(day, month, year);

            if (dateError) {
                makeErrorDialogueBox("Enter a valid birth date", "Use the format DD/MM/YYYY");
            } else {
                boolean created = Main.hu.createNewUser(name, day, month, year, gender);
                if (created) {
                    navigateHome(event);
                } else {
                    makeErrorDialogueBox("Name already in use.", "");
                }
            }
        }
    }


    /**
     * Logs user from the existingUser text field in, and takes them to the home screen.
     *
     * @param event clicking the sign in button
     * @throws IOException
     */
    @FXML
    void logIn(ActionEvent event) throws IOException {
        if (comboBox.getValue() == null) {
            makeErrorDialogueBox("Select a valid user", "No user was selected. Please select " +
                    "an existing\nuser from the drop down box.");
        } else {
            String name = comboBox.getValue();
            Main.hu.logIn(name);
            navigateHome(event);
        }
    }


    /**
     * Navigates the user to the home screen.
     *
     * @param event clicking the home button
     * @throws IOException
     */
    private void navigateHome(ActionEvent event) throws IOException {
        Parent homeParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/home.fxml"));
        Scene homeScene = new Scene(homeParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(homeScene);
    }

    /**
     * Displays the help screen.
     *
     * @param event clicking the help button
     * @throws IOException
     */
    @FXML
    private void openHelpStage(ActionEvent event) throws IOException {
        getHelp(event);
    }

}