package GUIControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by jto59 on 22/09/17.
 */
public class RankRoute {

    @FXML
    private Slider rating;

    @FXML
    public double rankHit() {
        return (int) rating.getValue();
    }

//    @FXML
//    void openRouteRankStage(ActionEvent event) throws IOException {
//        Stage popup = new Stage();
//        popup.initModality(Modality.WINDOW_MODAL);
//        popup.initOwner(((Node) event.getSource()).getScene().getWindow());
//        Parent popupParent = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/routeRank.fxml"));
//        Scene popupScene = new Scene(popupParent);
//        popup.setScene(popupScene);
//        popup.show();
//    }


}
