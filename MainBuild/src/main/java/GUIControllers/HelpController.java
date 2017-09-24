package GUIControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;


public class HelpController implements Initializable{

    @FXML
    private Text helpText;


    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        String helpMessage = "Welcome to <app_name>\n\n" +
                "Creating and Managing Users\n" +
                "A new user can be created by entering a user name on in the sign up text box and pressing the " +
                "sign up button. Alternatively, if you have already created an account then you may log into this " +
                "where all of your favourite routes, wifi hot spots and retail stores are remembered.\n\n" +
                "Navigation\n" +
                "In order to navigate through different features, click on the three black bars in the " +
                "top right of the screen to draw out each feature.\n\n" +
                "Home Screen\n" +
                "Displays all of your favourite routes, wifi hot spots and retail stores that you have saved on this " +
                "account. You can filter which order these are saved in. For example, if you would like to see the order " +
                "in which you have ranked your taken routes, click on the rating button from the favourite routes box " +
                "and this will update their order.\n\n" +
                "Plan Route\n" +
                "**how this works**\n\n" +
                "Add Data\n" +
                "Users may import existing csv files to the database which allows for routes, wifi hot spots and" +
                "retail stores to be add in bulk. Alternatively, one may manually enter each of these by entering " +
                "the details of these. If you incorrectly record data, you can later change this in the view data " +
                "section below.\n\n" +
                "View Data\n" +
                "This page allows you to view raw data and filter it by your choices to the left of the table and " +
                "pressing the 'filer' button. Furthermore, you can also select and add routes, wifi hot spots and " +
                "retail stores to your list of favourites here. This can simple be done by selecting the desired field " +
                "and pressing the 'Add to favourites' button. You may also edit incorrectly entered data by pressing" +
                "the 'View/Edit <field>' button which takes you to a screen where you can update its values in the " +
                "database.";
        helpText.setText(helpMessage);
    }



}
