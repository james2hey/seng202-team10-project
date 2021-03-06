package dataHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.PendingResult;
import com.google.maps.model.GeocodingResult;


/**
 * Implements the GeocodingResult callback to allow for asynchronous ApiRequests for speed
 */
public class GeocodeOutcome implements PendingResult.Callback<GeocodingResult[]> {

    private String[] record;
    private double[] latLon = {0, 0};
    private GeoCallback geoCallback;
    private Callback callback;


    /**
     * Creates a new object that awaits a result, then calls back to it's callers callback to let them know the given result
     *
     * @param record   A list of strings returned back to the callback for use.
     * @param callback The object to call back with the latlong and record on success or fail.
     */
    public GeocodeOutcome(String[] record, Callback callback, GeoCallback geoCallback) {
        this.record = record;
        this.callback = callback;
        this.geoCallback = geoCallback;
    }

    /**
     * On a successful result, processes the result to get the first valid entrys lat and long and returns them along with the string record
     * Calls back null if invalid.
     *
     * @param results The GeocodingResult
     */
    @Override
    public void onResult(GeocodingResult[] results) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        if (results[0] == null) {
            callback.result(false);
        }
        Double lat = Double.parseDouble(gson.toJson(results[0].geometry.location.lat));
        Double lon = Double.parseDouble(gson.toJson(results[0].geometry.location.lng));
        latLon[0] = lat;
        latLon[1] = lon;
        geoCallback.result(record, latLon, callback);
    }

    /**
     * If an error is thrown instead of success, returns null to the callback
     *
     * @param throwable
     */
    @Override
    public void onFailure(Throwable throwable) {
        callback.result(false);
    }
}
