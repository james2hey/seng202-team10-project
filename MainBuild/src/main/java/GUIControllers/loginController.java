package GUIControllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import main.HandleUsers;

public class loginController {

    @FXML
    public TextField username;

    @FXML
    public void createCyclist() {
        //nameInUse.setVisible(false);
        String name = username.getText();
        boolean created = HandleUsers.createNewUser(name, true);
//        if (created) {
//            System.out.println("Creating cyclist for " + name);
//            // Take user to main screen.
//        } else {
//            nameInUse.setVisible(true);
//        }
    }

    @FXML
    public void createAnalyst() {
        //nameInUse.setVisible(false);
        String name = username.getText();
        HandleUsers.createNewUser(name,false);
        boolean created = HandleUsers.createNewUser(name, false);
//        if (created) {
//            System.out.println("Creating analyst for " + name);
//            // Take user to main screen.
//        } else {
//            nameInUse.setVisible(true);
//        }
    }
}
