package GUIControllers;


import javafx.fxml.FXML;
import javafx.scene.text.Text;
import main.HandleUsers;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController extends Controller implements Initializable{

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



}
