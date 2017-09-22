package main;

import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.shapes.Polyline;
import dataAnalysis.RetailLocation;
import dataAnalysis.Route;
import dataAnalysis.WifiLocation;
import dataHandler.*;
import dataManipulation.FindNearbyLocations;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.ArrayList;

public class Main extends Application {
    static SQLiteDB db;
    public static ArrayList<WifiLocation> wifiLocations = new ArrayList<WifiLocation>();
    public static ArrayList<RetailLocation> retailLocations = new ArrayList<RetailLocation>();
    public static ArrayList<Route> routes = new ArrayList<Route>();

    @Override
    public void start(Stage primaryStage) throws Exception {

//        initCyclist();
        /*
        DatabaseManager.connect();
        //DatabaseManager.countRows();   //function used to determine key for new records --MUST RUN ON START--
        CSV_Importer.readcsv(getClass().getClassLoader().getResource("Lower_Manhattan_Retailers-test.csv").getFile(), 1);
        DatabaseManager.commitstartUp();
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
        db = new SQLiteDB();
        Geocoder.init();
        DatabaseUser.init();
        HandleUsers.init();
        HandleUsers.fillUserList();
        FindNearbyLocations.init(db);

        FavouriteRouteData favRouteData = new FavouriteRouteData(db);
        FavouriteWifiData favWifiData = new FavouriteWifiData(db);
        FavouriteRetailData favRetailData = new FavouriteRetailData(db);


//        System.out.println("before");
//        WifiDataHandler wdh = new WifiDataHandler(db);
//        System.out.println("adter");
//        System.out.println(wdh);
//        wdh.processCSV(getClass().getClassLoader().getResource("CSV/NYC_Free_Public_WiFi_03292017-test.csv").getFile());
//
//        RouteDataHandler rdh = new RouteDataHandler(db);
//        rdh.processCSV(getClass().getClassLoader().getResource("CSV/201601-citibike-tripdata-test.csv").getFile());
//
//
//        RetailerDataHandler retailerDataHandler = new RetailerDataHandler(db);
//        System.out.println("Made");
//        retailerDataHandler.processCSV(getClass().getClassLoader().getResource("CSV/Lower_Manhattan_Retailers-test.csv").getFile());

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/startUp.fxml"));

        primaryStage.setTitle("Cyclist Tracker");
        primaryStage.setScene(new Scene(root, 1100, 650));
        primaryStage.show();


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

}

