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
        DatabaseManager.printTables();
        CSV_Importer.readcsv("/home/cosc/student/jes143/Downloads/Lower_Manhattan_Retailers.csv");
    }
}
