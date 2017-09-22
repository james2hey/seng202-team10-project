package GUIControllers;

import dataHandler.SQLiteDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.HandleUsers;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginController extends Controller implements Initializable {

    @FXML
    private TextField username;

    @FXML
    private Text nameInUse;

    @FXML
    private ComboBox<String> comboBox;


    /**
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("here");
        SQLiteDB db = Main.getDB();
        System.out.println("here");
        try {
            System.out.println("here");
            ResultSet rs = db.executeQuerySQL("SELECT * FROM users");
            System.out.println("here");
            while (rs.next()) {
                comboBox.getItems().add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Creates new Cyclist instance with the given user name if it doesn't already exist in the database.
     */
    @FXML
    public void createCyclist(ActionEvent event) throws IOException {
        nameInUse.setVisible(false);
        String name = username.getText();
        boolean created = HandleUsers.createNewUser(name, true);
        if (created) {
            navigateHome(event);
        } else {
            nameInUse.setVisible(true);
        }
    }


    /**
     * Creates new Analyst instance with the given user name if it doesn't already exist in the database.
     */
    @FXML
    public void createAnalyst(ActionEvent event) throws IOException {
        nameInUse.setVisible(false);
        String name = username.getText();
        boolean created = HandleUsers.createNewUser(name, false);
        if (created) {
            System.out.println("Creating analyst for " + name);
            navigateHome(event);
        } else {
            nameInUse.setVisible(true);
        }
    }


    /**
     * Logs user from the existingUser text field in, and takes them to the home screen.
     */
    @FXML
    void logIn(ActionEvent event) throws IOException {
        if (comboBox.getValue() == null) {
            makeErrorDialogueBox("Select a valid user", "No user was selected. Please select " +
                    "an existing\nuser from the drop down box.");
        } else {
            String name = comboBox.getValue();
            HandleUsers.logIn(name);
            navigateHome(event);
        }
    }


    /**
     * Navigates the user to the home screen.
     * @param event;
     * @throws IOException;
     */
    private void navigateHome(ActionEvent event) throws IOException {
        Parent homeParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/home.fxml"));
        Scene homeScene = new Scene(homeParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(homeScene);
    }

}

