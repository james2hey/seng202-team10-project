package GUIControllers;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by jes143 on 30/09/17.
 */
public class ProgressPopupController extends Controller implements Initializable {

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Text progressText;

    @FXML
    private Button ok;

    @FXML
    private Button cancel;

    private Task task;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void activateProgressBar(final Task<?> task) {
        progressBar.progressProperty().bind(task.progressProperty());
        progressText.textProperty().bind(task.messageProperty());
        ok.disableProperty().bind(task.runningProperty());
        this.task = task;
    }

    public void cancelPressed(ActionEvent event) {
        task.cancel();
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public void okPressed(ActionEvent event) {
        Stage stage = (Stage) ok.getScene().getWindow();
        stage.close();
    }
}
