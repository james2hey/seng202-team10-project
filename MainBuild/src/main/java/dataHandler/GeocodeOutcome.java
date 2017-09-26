package dataHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.PendingResult;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

/**
 * Implements the GeocodingResult callback to allow for asynchronous ApiRequests for speed
 */
public class GeocodeOutcome implements PendingResult.Callback<GeocodingResult[]> {

    private String[] record;
    private double[] latLon = {0, 0};
    private Callback callback;


    /**
     * Creates a new object that awaits a result, then calls back to it's callers callback to let them know the given result
     * @param record A list of strings returned back to the callback for use.
     * @param callback The object to call back with the latlong and record on success or fail.
     */
    public GeocodeOutcome(String[] record, Callback callback) {
        this.record = record;
        this.callback = callback;
    }

    /**
     * On a successful result, processes the result to get the first valid entrys lat and long and returns them along with the string record
     * Calls back null if invalid.
     * @param results The GeocodingResult
     */
    @Override
    public void onResult(GeocodingResult[] results) {
        System.out.println("Called back");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        if (results[0] == null) {
            callback.response(null, null);
        }
        Double lat = Double.parseDouble(gson.toJson(results[0].geometry.location.lat));
        Double lon = Double.parseDouble(gson.toJson(results[0].geometry.location.lng));
        latLon[0] = lat;
        latLon[1] = lon;
        callback.response(record, latLon);

    }

    /**
     * If an error is thrown instead of success, returns null to the callback
     * @param throwable
     */
    @Override
    public void onFailure(Throwable throwable) {
        record = null;
        throwable.printStackTrace();
        callback.response(null, null);
    }
}
