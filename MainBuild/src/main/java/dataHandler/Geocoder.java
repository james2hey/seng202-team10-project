package dataHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;

/**
 * Created by jes143 on 18/09/17.
 */
public class Geocoder {

    private static GeoApiContext context;

    public static void init() {
        context = new GeoApiContext.Builder()
                .apiKey("AIzaSyDEPEUGnJw2WaQ0cKzu8TVuKHeC3gYKnKc ")
                .build();
    }


    public static double[] addressToLatLon(String address) {
        try {
            GeocodingResult[] results = GeocodingApi.geocode(context, address).await();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Double lat = Double.parseDouble(gson.toJson(results[0].geometry.location.lat));
            Double lon = Double.parseDouble(gson.toJson(results[0].geometry.location.lng));
            return new double[]{lat, lon};
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
