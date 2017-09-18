package main;

import dataAnalysis.Route;
import dataHandler.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    static SQLiteDB db;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("startUp.fxml"));
        primaryStage.setTitle("Cyclist Tracker");
        primaryStage.setScene(new Scene(root, 1100, 650));
        primaryStage.show();
//        initCyclist();
        /*
        DatabaseManager.connect();
        //DatabaseManager.countRows();   //function used to determine key for new records --MUST RUN ON START--
        CSV_Importer.readcsv(getClass().getClassLoader().getResource("Lower_Manhattan_Retailers-test.csv").getFile(), 1);
        DatabaseManager.commit();
        CSV_Importer.readcsv(getClass().getClassLoader().getResource("NYC_Free_Public_WiFi_03292017-test.csv").getFile(), 2);
        DatabaseManager.commit();
        CSV_Importer.readcsv(getClass().getClassLoader().getResource("201601-citibike-tripdata-test.csv").getFile(), 3);
        DatabaseManager.commit();
        DatabaseManager.printTables();
        */
        //DatabaseManager.connect();

//        Geocoder.init();
//        double[] latlon = Geocoder.addressToLatLon("University of Canterbury");
//        System.out.println(latlon[0]);
//        System.out.println(latlon[1]);
//
//        db = new SQLiteDB();

//        WifiDataHandler wdh = new WifiDataHandler(db);
//        System.out.println("Made");
//        wdh.processCSV(getClass().getClassLoader().getResource("NYC_Free_Public_WiFi_03292017-test.csv").getFile());
//
//        RouteDataHandler rdh = new RouteDataHandler(db);
//        rdh.processCSV(getClass().getClassLoader().getResource("201601-citibike-tripdata-test.csv").getFile());
//
//        RetailerDataHandler retailerDataHandler = new RetailerDataHandler(db);
//        retailerDataHandler.processCSV(getClass().getClassLoader().getResource("Lower_Manhattan_Retailers-test.csv").getFile());


//
//        DatabaseManager.countRows();   //function used to determine key for new records --MUST RUN ON START--
//        AbstractDataHandler.readcsv(getClass().getClassLoader().getResource("Lower_Manhattan_Retailers-test.csv").getFile(), 1);
//        DatabaseManager.commit();
//        AbstractDataHandler.readcsv(getClass().getClassLoader().getResource("NYC_Free_Public_WiFi_03292017-test.csv").getFile(), 2);
//        DatabaseManager.commit();
//        //System.out.println("Trip");
//        AbstractDataHandler.readcsv(getClass().getClassLoader().getResource("201601-citibike-tripdata-test.csv").getFile(), 3);
//        DatabaseManager.commit();

//        HandleUsers.fillUserList();
//        HandleUsers.createNewUser(true);
        //AbstractDataHandler.readcsv(getClass().getClassLoader().getResource("system_users.csv").getFile(), 4);
        //DatabaseManager.commit();

        //DatabaseManager.printTables();

    }

    /**Initializes the user and does
     */
    private static void initCyclist() {
        Cyclist cyclist = new Cyclist();
    }

    public static SQLiteDB getDB() {
        return db;
    }

//
//    public static void main(String[] args) {
//        //launch(args);
//        DatabaseManager.connect();
//        DatabaseManager.countRows();   //function used to determine key for new records --MUST RUN ON START--
//        initUser();
//        //AbstractDataHandler.readcsv("/home/cosc/student/jes143/Downloads/Lower_Manhattan_Retailers.csv", 1);
//        //AbstractDataHandler.readcsv("/home/cosc/student/jes143/Downloads/NYC_Free_Public_WiFi_03292017.csv", 2);
//        //AbstractDataHandler.readcsv("/home/cosc/student/jes143/Downloads/201707-citibike-tripdata.csv", 3);
//        DatabaseManager.commit();
//        DatabaseManager.printTables();
//    }
}
