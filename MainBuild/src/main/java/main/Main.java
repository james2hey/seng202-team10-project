package main;

import dataHandler.*;
import dataManipulation.FindNearbyLocations;
import dataManipulation.UpdateData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class Main extends Application {
    static SQLiteDB db;
    public static HandleUsers hu;

    @Override
    public void start(Stage primaryStage) throws Exception {

        db = new SQLiteDB();
        Geocoder.init();
        DatabaseUser d = new DatabaseUser(db);
        hu = new HandleUsers();
        hu.init(db);
        UpdateData.init(db);

        FindNearbyLocations nearbyLocations = new FindNearbyLocations(db);
        FavouriteRouteData favRouteData = new FavouriteRouteData(db);
        FavouriteWifiData favWifiData = new FavouriteWifiData(db);
        FavouriteRetailData favRetailData = new FavouriteRetailData(db);

        String url = getClass().getClassLoader().getResource("Images/bicycleIcon.png").toString();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/startUp.fxml"));

        primaryStage.getIcons().add(new Image(url));

        primaryStage.setTitle("Pedals");
        primaryStage.setScene(new Scene(root, 1100, 650));
        primaryStage.show();
    }

    public static SQLiteDB getDB() {
        return db;
    }

}

