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
        String helpMessage = "This is helpful\n\nLike really helpful...";
        helpText.setText(helpMessage);

    }



}
