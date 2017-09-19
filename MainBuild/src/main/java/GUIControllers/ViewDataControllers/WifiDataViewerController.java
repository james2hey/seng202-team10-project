package GUIControllers.ViewDataControllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.fxml.FXML;


/**
 * Created by bal65 on 19/09/17.
 */
public class WifiDataViewerController extends DataViewerController {

    @FXML
    private TableColumn<?, ?> Address;

    @FXML
    private TableColumn<?, ?> Suburb;

    @FXML
    private JFXTextField providerInput;

    @FXML
    private TableView<?> tableView;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXTextField boroughInput;

    @FXML
    private ComboBox<?> typeInput;

    @FXML
    private TableColumn<?, ?> Cost;

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
