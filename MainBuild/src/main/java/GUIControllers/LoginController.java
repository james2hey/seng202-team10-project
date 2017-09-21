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

public class LoginController implements Initializable {

    @FXML
    public TextField username;

    @FXML
    public Text nameInUse;

    @FXML
    public static ComboBox<String> existingUsers = new ComboBox<>();

    @FXML
    public TextField existingUser;

    @FXML
    private ComboBox<String> comboBox;

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
                System.out.println(rs.getString(1));
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
            System.out.println("Creating cyclist for " + name);
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
        String name = existingUser.getText();
        HandleUsers.logIn(name, true); // Currently only creating cyclists on sign in for now.
        //HandleUsers.currentUser = name;
        navigateHome(event);
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

