package GUIControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.HandleUsers;

import java.io.IOException;

public class SidePanelController extends Controller{

    @FXML
    private Button addDataButton;

    @FXML
    private Button viewDataButton;

    @FXML
    private Button planRouteButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button logoutButton;



    @FXML
    public void logOut(ActionEvent event) throws IOException {
        Parent logInParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/startUp.fxml"));
        Scene logInScene = new Scene(logInParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(logInScene);
        HandleUsers.logOutOfUser();
    }

    @FXML
    void openHelpStage(ActionEvent event) throws IOException{
        Stage popup = new Stage();
        popup.initModality(Modality.WINDOW_MODAL);
        popup.initOwner(((Node) event.getSource()).getScene().getWindow());
        Parent popupParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/help.fxml"));
        Scene popupScene = new Scene(popupParent);
        popup.setScene(popupScene);
        popup.show();
    }

}
