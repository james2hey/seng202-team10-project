package GUIControllers;


import com.jfoenix.controls.JFXDrawer;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;

import java.io.IOException;


public class homeController {

    @FXML
    private JFXDrawer drawer;

    @FXML
    void openDrawer() throws IOException {
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
    void login() {
        System.exit(0);
    }

}
