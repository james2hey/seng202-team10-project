package GUIControllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

/**
 * Created by bal65 on 16/09/17.
 */

public class dataViewerController {

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;

    @FXML
    void openDrawer() throws IOException {
        System.out.println("I'm clicked mother fucker");

        VBox box = FXMLLoader.load(getClass().getClassLoader().getResource("SidePanel.fxml"));
        drawer.setSidePane(box);
        if (drawer.isShown()) {
            drawer.close();
        }
        else {
            drawer.open();
        }

    }

    @FXML
    void displayData(ActionEvent event) throws IOException {
        System.out.println("Display Data button pressed.");
    }

}

