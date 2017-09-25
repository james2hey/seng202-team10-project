package GUIControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the help screen.
 */

public class HelpController implements Initializable{

    @FXML
    private Text helpText;

    /**
     * Runs on start up and sets the text to helpMessage string.
     *
     * @param location Location of the fxml
     * @param resources Locale-specific data required for the method to run automatically
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        String helpMessage = "Creating and Managing Users\n" +
                "A new user can be created by entering a user name on in the sign up text box and pressing the " +
                "sign up button. Alternatively, if you have already created an account then you may log into this " +
                "where all of your favourite routes, wifi hot spots and retail stores are remembered. Select your" +
                "account from the drop down box and click sign in to do this.\n\n" +
                "Navigation\n" +
                "In order to navigate through different features, click on the three black bars in the " +
                "top right of the screen to open the navigation panel.\n\n" +
                "Home Screen\n" +
                "Here, all of your favourite routes, wifi hot spots and retail stores that you have saved on this " +
                "account are displayed. You can filter which order these are saved in. For example, if you would like " +
                "to see the order in which you have ranked your taken routes, click on the rating button from the " +
                "favourite routes box and this will sort them by rating.\n\n" +
                "Plan Route\n" +
                "Here you can plan a route from one place to another. Type in a start and end address and press " +
                "enter for it to display. You may wish to show retailers or wifi locations near your route." +
                "To do this, click the corresponding buttons below the map. If you want to add this as a route you " +
                "have completed then click Add Current Route to Database and you will be taken to a screen where" +
                "you can add additional information about your trip.\n\n" +
                "Add Data\n" +
                "Users may import existing csv files, of the correct format, to the database which allows for routes, " +
                "wifi hot spots and retail stores to be add in bulk. Alternatively, you may manually enter each of " +
                "these by entering the details of these. If you incorrectly record data, you can later change this on " +
                "the view data page.\n\n" +
                "View Data\n" +
                "This page allows you to view raw data and filter it by your choices to the left of the table and " +
                "pressing the 'filer' button. Furthermore, you can also select and add routes, wifi hot spots and " +
                "retail stores to your list of favourites here. This can be done by selecting the desired piece of data " +
                "and pressing the 'Add to favourites' button. You may also edit data by pressing" +
                "the 'View/Edit button which takes you to a screen where you can update most of the fields. Some fields" +
                ", however, cannot be changed as these uniquely identify the data. If this field must be changed, a" +
                "new piece of data must be created. Finally, you can also view any of the data in these tables on the " +
                "map by clicking wither of the view on map buttons.";
        helpText.setText(helpMessage);
    }



}
