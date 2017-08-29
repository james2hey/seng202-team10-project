package main;

import com.opencsv.CSVReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.DatabaseManager;

public class Main {

//    @Override
//    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(Main.class.getResource("/sample.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();
//    }


    public static void main(String[] args) {
        //launch(args);
        DatabaseManager.connect();
        //CSV_Importer.readcsv("/home/cosc/student/jes143/Downloads/Lower_Manhattan_Retailers.csv", 1);
        //CSV_Importer.readcsv("/home/cosc/student/jes143/Downloads/NYC_Free_Public_WiFi_03292017.csv", 2);
        //CSV_Importer.readcsv("/home/cosc/student/jes143/Downloads/201707-citibike-tripdata.csv", 3);
        //DatabaseManager.commit();
        DatabaseManager.printTables();
    }
}
