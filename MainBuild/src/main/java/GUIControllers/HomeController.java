package GUIControllers;


import dataAnalysis.RetailLocation;
import dataAnalysis.Route;
import dataAnalysis.WifiLocation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import main.HandleUsers;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController extends Controller implements Initializable{

    @FXML
    private TableColumn<Route, String> FavRoutes;

    @FXML
    private TableColumn<WifiLocation, String> FavWifi;

    @FXML
    private TableColumn<RetailLocation, String> FavRetailers;

    @FXML
    private TableView<Route> tableViewRoutes;

    @FXML
    private TableView<WifiLocation> tableViewWifi;

    @FXML
    private TableView<RetailLocation> tableViewRetailers;

    @FXML
    private Text welcomeText;

    private ObservableList<Route> routeList = FXCollections.observableArrayList();
    private ObservableList<WifiLocation> wifiList = FXCollections.observableArrayList();
    private ObservableList<RetailLocation> retailerList = FXCollections.observableArrayList();


    public void initialize(URL location, ResourceBundle resources) {
        String username = "";
        welcomeText.setText("Welcome: " + username);

        routeList.addAll(HandleUsers.currentCyclist.getFavouriteRouteList());
        wifiList.addAll(HandleUsers.currentCyclist.getFavouriteWifiLocations());
        retailerList.addAll(HandleUsers.currentCyclist.getFavouriteRetailLocations());

        FavRoutes.setCellValueFactory(new PropertyValueFactory<>("StartAddress"));
        tableViewRoutes.setItems(routeList);
        tableViewRoutes.getColumns().setAll(FavRoutes);

        FavWifi.setCellValueFactory(new PropertyValueFactory<>("SSID"));
        tableViewWifi.setItems(wifiList);
        tableViewWifi.getColumns().setAll(FavWifi);

        FavRetailers.setCellValueFactory(new PropertyValueFactory<>("Name"));
        tableViewRetailers.setItems(retailerList);
        tableViewRetailers.getColumns().setAll(FavRetailers);


    }



}
