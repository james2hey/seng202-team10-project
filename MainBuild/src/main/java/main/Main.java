package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    /**Initializes the user and does __________
     */
    public static void initUser() {
        //GUI - Find user type "Cyclist" used as example.
        User user = new User("Cyclist");

    }


    public static void main(String[] args) {
        launch(args);
        initUser();
    }
}
