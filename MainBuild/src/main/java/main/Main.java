package main;

import dataHandler.SQLiteDB;
import dataHandler.RouteDataHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("startUp.fxml"));
        primaryStage.setTitle("Cyclist Tracker");
        primaryStage.setScene(new Scene(root, 1100, 650));
        primaryStage.show();
        DatabaseManager.connect();
//
//        DatabaseManager.countRows();   //function used to determine key for new records --MUST RUN ON START--
//        CSV_Importer.readcsv(getClass().getClassLoader().getResource("Lower_Manhattan_Retailers-test.csv").getFile(), 1);
//        DatabaseManager.commit();
//        CSV_Importer.readcsv(getClass().getClassLoader().getResource("NYC_Free_Public_WiFi_03292017-test.csv").getFile(), 2);
//        DatabaseManager.commit();
//        //System.out.println("Trip");
//        CSV_Importer.readcsv(getClass().getClassLoader().getResource("201601-citibike-tripdata-test.csv").getFile(), 3);
//        DatabaseManager.commit();

//        HandleUsers.fillUserList();
//        HandleUsers.createNewUser(true);
        //CSV_Importer.readcsv(getClass().getClassLoader().getResource("system_users.csv").getFile(), 4);
        //DatabaseManager.commit();

        //DatabaseManager.printTables();

    }

    /**Initializes the user and does
     */
    private static void initCyclist() {
        Cyclist cyclist = new Cyclist();
    }

//
//    public static void main(String[] args) {
//        //launch(args);
//        DatabaseManager.connect();
//        DatabaseManager.countRows();   //function used to determine key for new records --MUST RUN ON START--
//        initUser();
//        //CSV_Importer.readcsv("/home/cosc/student/jes143/Downloads/Lower_Manhattan_Retailers.csv", 1);
//        //CSV_Importer.readcsv("/home/cosc/student/jes143/Downloads/NYC_Free_Public_WiFi_03292017.csv", 2);
//        //CSV_Importer.readcsv("/home/cosc/student/jes143/Downloads/201707-citibike-tripdata.csv", 3);
//        DatabaseManager.commit();
//        DatabaseManager.printTables();
//    }
}
