package GUIControllers.ViewDataControllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Created by bal65 on 19/09/17.
 */
public class RetailerDataViewerController extends DataViewerController {

    @FXML
    private TableColumn<?, ?> Address;

    @FXML
    private TableColumn<?, ?> Suburb;

    @FXML
    private JFXTextField streetInput;

    @FXML
    private JFXTextField primaryInput;

    @FXML
    private TableView<?> tableView;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private TableColumn<?, ?> Cost;

    @FXML
    private JFXTextField zipInput;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private TableColumn<?, ?> Name;


    @FXML
    void displayDataOnMap(ActionEvent event) {

    }

    @FXML
    void displayData(ActionEvent event) {

    }

}
