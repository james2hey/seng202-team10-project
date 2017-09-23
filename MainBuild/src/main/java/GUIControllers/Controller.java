package GUIControllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

import java.io.IOException;

public abstract class Controller {

    @FXML
    private JFXDrawer drawer;

    @FXML
    private Button addDataButton;

    @FXML
    private Button viewDataButton;

    @FXML
    private Button planRouteButton;

    @FXML
    private Button homeButton;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    void openDrawer() throws IOException {
//
        VBox box = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/SidePanel.fxml"));
        drawer.setSidePane(box);

//        HamburgerBasicCloseTransition transition = new HamburgerBasicCloseTransition(hamburger);
//
//        transition.setRate(-1);
//        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED,(e)->{
//            transition.setRate(transition.getRate()*-1);
//            transition.play();
//        });

        if (drawer.isShown()) {
//            HamburgerBasicCloseTransition transition2 = new HamburgerBasicCloseTransition(hamburger);
//            transition2.setRate(-1);
//            transition2.play();
            drawer.close();


        }
        else {
            drawer.open();
//            HamburgerBasicCloseTransition transition2 = new HamburgerBasicCloseTransition(hamburger);
//            transition2.setRate(1);
//            transition2.play();

        }
    }

    @FXML
    void changeToPlanRouteScene(ActionEvent event) throws IOException {
        Parent planRouteParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/planRoute.fxml"));
        Scene planRouteScene = new Scene(planRouteParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(planRouteScene);
    }

    @FXML
    void changeToHomeScene(ActionEvent event) throws IOException {
        Parent homeParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/home.fxml"));
        Scene homeScene = new Scene(homeParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(homeScene);
    }


    @FXML
    void changeToAddDataScene(ActionEvent event) throws IOException {
        Parent addDataParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/addData.fxml"));
        Scene addDataScene = new Scene(addDataParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(addDataScene);
    }

    @FXML
    void changeToViewDataScene(ActionEvent event) throws IOException {
        Parent viewDataParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/routeViewData.fxml"));
        Scene viewDataScene = new Scene(viewDataParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(viewDataScene);
    }

    @FXML
    public void makeErrorDialogueBox(String errorMessage, String errorDetails) {
        Alert alert = new Alert(Alert.AlertType.ERROR, errorDetails, ButtonType.OK);
        alert.setHeaderText(errorMessage);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            System.out.println("Ok pressed");
        }
    }
//
//    /**
//     * Makes a confirmation dialogue box and returns yes as true, no as false.
//     * Assumes that if dialogue is exited, result is no.
//     *
//     * @param errorMessage String that provides the error message for the dialogue box.
//     * @param errorDetails String that provides the error details for the dialogue box.
//     * @return boolean representing result of yes/no answer in confirmation box.
//     */
//    @FXML
//    boolean makeConfirmationDialogueBox(String errorMessage, String errorDetails) {
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, errorDetails, ButtonType.NO, ButtonType.YES);
//        alert.setHeaderText(errorMessage);
//        alert.showAndWait();
//
//        if (alert.getResult() == ButtonType.YES) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    /**
     * Makes a confirmation dialogue box and returns yes as true, no as false.
     * Assumes that if dialogue is exited, result is no.
     *
     * @param message String that provides the message for the dialogue box.
     * @param details String that provides the details for the dialogue box.
     */
    @FXML
    public void makeSuccessDialogueBox(String message, String details) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, details, ButtonType.OK);
        alert.setHeaderText(message);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            System.out.println("");
        }
    }





}
