package GUIControllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.HandleUsers;
import javafx.fxml.Initializable;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController extends Controller implements Initializable{

    @FXML
    public Button logOutButton;

    @FXML
    private Text welcomeText;


    public void initialize(URL location, ResourceBundle resources) {
        String username = "";
        if (HandleUsers.currentCyclist == null) {
            username = HandleUsers.currentAnalyst.getName();
            welcomeText.setText("Welcome: " + username + "   (Analyst)");
        } else {
            username = HandleUsers.currentCyclist.getName();
            welcomeText.setText("Welcome: " + username + "   (Cyclist)");
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
