package GUIControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the help screen.
 */

public class HelpController implements Initializable {

    @FXML
    private Text userSection, navSection, homeSection, planSection, addDataSection, viewSection, aboutSection, legalSection, completedRoutesText;

    /**
     * Runs on start up and sets the text to helpMessage string.
     *
     * @param location  Location of the fxml
     * @param resources Locale-specific data required for the method to run automatically
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        String completedRoutesMessage = "\nCompleted Routes\n\n\nThis screen shows routes that the user has defined as completed. These are " +
                "routes that the user has biked along and would like to track. This page also comes with some statistics. " +
                "Above the table of all completed routes, statistics about all of the completed routes are displayed. This " +
                "includes the longest route, the shortest route, the average route distance and the total distance traveled. " +
                "To the right of this table is a graph of the 5 most recently taken routes compared with distance. \n\n" +
                "To remove a route from the completed routes, select it in the table and press the ‘Remove Selected From " +
                "Completed Routes’ button. The statistics and graph will automatically update.\n";
        completedRoutesText.setText(completedRoutesMessage);
        String userMessage = "\nCreating and Managing Users\n\n\n" +
                "If this is your first time using the application, you are required to sign up. Fill the fields on " +
                "the left and press the ‘Sign up’ button at the bottom. Note that all fields must be filled, before " +
                "you can proceed.\n\n" +
                "If you have already used the application, then you can sign in to your account using the drop down box " +
                "on the right. Simply select your username and click ‘Sign in’ below. By signing into your old account, " +
                "you can sort data based on lists you create, view personally defined favourite routes, retailers, and " +
                "wifi locations, as well as track your own progress via the completed routes section.\n\n\n";
        userSection.setText(userMessage);
        String navMessage = "\nNavigation\n\n\n" +
                "To navigate to different screens, click on the three black bars in the " +
                "top right of the screen to open the navigation panel. The buttons within the navigational panel will " +
                "take you to the different scenes of the application.\n\n";
        navSection.setText(navMessage);
        String homeMessage = "\nHome Screen\n\n\n" +
                "From this screen, a user’s favourite routes, wifi locations and retailers can be viewed. Favourites are " +
                "a way of storing a user’s most used/interesting data so that it can be located and viewed on the map " +
                "quickly and easily. To view all of them on the map, the button ‘View Favourites on Map’ should be pressed. \n\n" +
                "To remove a piece of data from the favourites list, select it and press the button ‘Remove from Favourites’.\n";
        homeSection.setText(homeMessage);
        String planMessage = "\nPlan Route\n\n\n" +
                "From this screen, you can plan a route from one address to another. \n" +
                "\n" +
                "Simply type in both a valid address into both the start and end address fields and either hit the " +
                "enter key or click the search button. Your route will then display on the map. \n\n" +
                "Once wifi locations and retailers have been imported, you may also click the ‘Show Closest Wifi " +
                "Location/Retailer’ buttons. (Note: These buttons are disabled until a route has been searched/selected.) " +
                "A click of these buttons will display the single closest wifi location or retailer to the currently " +
                "selected map element. Clicking these buttons again will show the next closest Wifi Location or Retailer on the map.\n\n" +
                "The ‘Save Route’ button will take you to the Data Adder where you can save the route into the application.\n\n\n";
        planSection.setText(planMessage);
        String addDataMessage = "\nAdd Data\n\n\n" +
                "From this page, you can manually enter information required for a route, wifi location or retailer. " +
                "To select which type you are adding, use the top three buttons to navigate to the appropriate screen. " +
                "Each data type will require different information. For routes, it is required that the left and center " +
                "field sets are filled. For wifi locations, and retailers both a name and an address are required. Data " +
                "can also be added to a user's favourites/completed routes by selecting the appropriate check boxes on " +
                "the right. Finally, data can also be imported into a particular list (which can make sorting and finding " +
                "data easier in the future). For this, either write the name of a new list or select a previously created " +
                "list from the drop down box. \n\n" +
                "Data can also be imported from a correctly formatted CSV file. \n" +
                "For routes, the supported CSV format is taken from Citi Bike. Information on CSV formatting can be " +
                "found at the following website. Here, route data can also be downloaded for use in Pedals. \n\n" +
                "https://www.citibikenyc.com/system-data\n\n" +
                "For retailers and wifi locations, the supported CSV format is taken from Data Catalog. These can be " +
                "viewed and downloaded from the following two websites: \n\n" +
                "https://catalog.data.gov/dataset/lower-manhattan-retailers-53d81 \n" +
                "https://catalog.data.gov/dataset/nyc-wi-fi-hotspot-locations-9a8e0\n\n" +
                "To import a CSV the button ‘Import CSV’ should be clicked at the bottom of the screen. If the data " +
                "from the CSV is to be added to a list for additional filtering then a list should be named/selected " +
                "from the combo box above the button. After clicking the button, you should select the correct file " +
                "from within the file chooser popup. A progress bar will then show the import progress as it completes. " +
                "Pressing cancel on this at any time will stop all of the data from being imported.\n\n\n";
        addDataSection.setText(addDataMessage);
        String viewMessage = "\nView Data\n\n\n" +
                "From this screen, previously added routes, wifi locations and retailers can be viewed, edited, removed, " +
                "shown on the map, and added to favourites/completed routes. \n\n" +
                "The three buttons at the top of the screen will switch the view to show the relevant data type.\n" +
                "Clicking the tabs at the top of the table will sort the table to order data by the selected tab in " +
                "alphabetical order/reverse order depending on how many times it is clicked.\n\n" +
                "To filter the data in the table, the fields on the left can be used. Once entered, the ‘Filter’ button " +
                "will update the table given the filtering input. Note that text fields will filter data that contains, " +
                "not that starts with, the entered text.\n\n" +
                "Double-clicking on an item within the table will open the advanced view of a given piece of data.\n" +
                "From this view, most fields can be edited and updated using the ‘Update Changes’ button. Non-editable " +
                "fields are those used to keep the data distinguishable from others so cannot be changed. \n" +
                "Data can also be deleted using the ‘Delete XXXX’ button. This will remove the item permanently and cannot " +
                "be undone.\n" +
                "\n" +
                "\n" +
                "From the data viewer screen, data can be viewed on the map. Selecting a chosen piece of data " +
                "and clicking the ‘View Selected’ button will show only that item on the map. The ‘View All’ button will " +
                "show all data from the current table on the map.\n" +
                "Finally, after selecting a piece of data, you may also add this to the user’s favourites list or their " +
                "completed routes.\n";
        viewSection.setText(viewMessage);
        String aboutMessage = "\nAbout\n\n\n" +
                "Pedals is an open source cyclist mapping and route analysis software tool for cyclists across the world. " +
                "A user can add data to track, and manipulate it using a GUI. " +
                "The application is currently optimised for New York city cyclists. " +
                "Being open source, the code is open publicly available for further development.\n\n" +
                "Created by: Braden, Lewis, Jack, James and Matt\n\n\n\n\n" +
                "Current Version: 1.0.0";
        aboutSection.setText(aboutMessage);
        String legalMessage = "\nDisclaimer\n\n\n" +
                "This software is licenced under the Eclipse Public License - v 1.0. (https://www.eclipse.org/legal/epl-v10.html)\n\n" +
                "Any third party libraries can be accessed and installed within the LICENCE and NOTICE files.(http://www.github.com/jackodsteel/pedals)\n\n" +
                "By using this software you agree to abide by the Google Maps TOS. (https://developers.google.com/maps/terms)\n\n" +
                "Feel free to modify and distribute this code as you please, just remember to reference us, and abide by the respective dependency licences.";
        legalSection.setText(legalMessage);
    }
}