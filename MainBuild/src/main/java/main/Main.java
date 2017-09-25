package main;

import dataHandler.*;
import dataManipulation.FindNearbyLocations;
import dataManipulation.UpdateData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    static SQLiteDB db;
    public static HandleUsers hu;

    @Override
    public void start(Stage primaryStage) throws Exception {

        db = new SQLiteDB();
        Geocoder.init();
        DatabaseUser d = new DatabaseUser(db);
        hu = new HandleUsers();
        hu.init();
        hu.fillUserList();
        UpdateData.init(db);

        FindNearbyLocations nearbyLocations = new FindNearbyLocations(db);
        FavouriteRouteData favRouteData = new FavouriteRouteData(db);
        FavouriteWifiData favWifiData = new FavouriteWifiData(db);
        FavouriteRetailData favRetailData = new FavouriteRetailData(db);


        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/startUp.fxml"));

        primaryStage.setTitle("Pedals");
        primaryStage.setScene(new Scene(root, 1100, 650));
        primaryStage.show();
    }

    /**Initializes the user and does
     */
    private static void initCyclist() {
        Cyclist cyclist = new Cyclist();
    }

    public static SQLiteDB getDB() {
        return db;
    }

}

