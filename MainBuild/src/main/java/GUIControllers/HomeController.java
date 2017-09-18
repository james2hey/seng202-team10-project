package GUIControllers;


import javafx.fxml.FXML;

public class HomeController extends Controller{

    @FXML
    void login() {
        boolean answer;
        answer = makeConfirmationDialogueBox("Warning", "Are you sure you want to exit?");
        if (answer) {
            System.exit(0);
        }
    }

}
