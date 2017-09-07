package main;

import com.opencsv.CSVReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.DatabaseManager;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        DatabaseManager.connect();
        initUser();
        CSV_Importer.readcsv(getClass().getClassLoader().getResource("Lower_Manhattan_Retailers.csv").getFile(), 1);
        CSV_Importer.readcsv(getClass().getClassLoader().getResource("NYC_Free_Public_WiFi_03292017.csv").getFile(), 2);
        CSV_Importer.readcsv(getClass().getClassLoader().getResource("201601-citibike-tripdata.csv").getFile(), 3);
        DatabaseManager.commit();
        DatabaseManager.printTables();
    }

    /**Initializes the user and does __________
     */
    public static void initUser() {
        //GUI - Find user type "Cyclist" used as example.
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
