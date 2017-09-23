package dataHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * Created by jes143 on 18/09/17.
 */
public class Geocoder {

    private static GeoApiContext context;

    /**
     * Creates a GeoApiContext object to be used for searching.
     * Currently uses a static API key
     */
    public static void init() {
        context = new GeoApiContext.Builder()
                .apiKey("AIzaSyDEPEUGnJw2WaQ0cKzu8TVuKHeC3gYKnKc")
                .connectTimeout((long) 300, TimeUnit.MILLISECONDS)
                .maxRetries(0)
                .build();
    }

    /**
     * Takes an address and uses Google Geocoding API to get a latitude and longitude.
     * @param address A string specifying the address to search
     * @return The first result from the geocode search
     */
    public static double[] addressToLatLon(String address) {
        GeocodingResult[] results;
        try {
            results = GeocodingApi.geocode(context, address).await();
        } catch (ApiException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("API Error");
            alert.setHeaderText("Google API Error");
            alert.setContentText("The application could not get the lat and lon because there was an issue with the API");
            alert.showAndWait();
            return null;
        } catch (IOException | InterruptedException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("IO Error");
            alert.setHeaderText("Input Error");
            alert.setContentText("The application could not convert the address as it could not connect to the internet");
            alert.showAndWait();
            return null;
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Double lat = Double.parseDouble(gson.toJson(results[0].geometry.location.lat));
        Double lon = Double.parseDouble(gson.toJson(results[0].geometry.location.lng));
        return new double[]{lat, lon};
    }

    /**
     * Checks the database by testing a random address. If the test fails, the error popup will show.
     * @return If a value was successfully returned
     */
    public static boolean testConnection() {
        return (Geocoder.addressToLatLon("123 Fake St") != null);
    }
}
