package dataHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import javafx.scene.control.Alert;

import java.io.IOException;
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
                .apiKey("AIzaSyAZaugbSVStPn9AsAvYaA-JiE9YJ89NYHo")
                .connectTimeout((long) 300, TimeUnit.MILLISECONDS)
                .maxRetries(0)
                .queryRateLimit(40)
                .build();
    }

    /**
     * Takes an address and uses Google Geocoding API to get a latitude and longitude.
     * @param address A string specifying the address to search
     * @return The first result from the geocode search
     * @throws ApiException
     * @throws IOException
     * @throws InterruptedException
     * @throws ArrayIndexOutOfBoundsException
     */
    public static double[] addressToLatLon(String address) throws ApiException, IOException, InterruptedException, ArrayIndexOutOfBoundsException {
        GeocodingResult[] results;
        results = GeocodingApi.geocode(context, address).await();
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
        try {
            return (Geocoder.addressToLatLon("123 Fake St") != null);
        } catch (ApiException | IOException | InterruptedException e) {
            return false;
        }
    }
}
