package dataAnalysis;

/**
 * Has methods for all main functionality of the routing.
 */

//Users should be able to search for trips based on search criteria. We need
//to find out what this search criteria is and find a Trip.

public class Route {
    private int duration, startTime, startDate, stopTime, stopDate, distance;
    private Location startLocation, endLocation, viaLocation;

    //Two types of constructors as there is not always a viaLocation.

    public Route(Location start, Location end, int time, int date) {
        startLocation = start;
        endLocation = end;
        startTime = time;
        startDate = date;
    }

    public Route(Location start, Location end, Location via, int time, int date) {
        startLocation = start;
        endLocation = end;
        viaLocation = via;
        startTime = time;
        startDate = date;
    }

    /**
     * Constructor for Route class, used by Datafilterer class when filtering records from the database.
     */
    public Route(int tripDuration, int stTime, int spTime, int stDate, int spDate, double stStationLat,
                 double stStationLong, double endStationLat, double endStationLong, int stStationID, int endStationID) {
        duration = tripDuration;
        startTime = stTime;
        startDate = stDate;
        stopTime = spTime;
        stopDate = spDate;
        startLocation = new StationLocation(stStationID, stStationLat, stStationLong);
        endLocation = new StationLocation(endStationID, endStationLat, endStationLong);
        calculateDistance(startLocation, endLocation);
    }

    //Getters for duration and distance.

    public int getDuration() {
        return duration;
    }

    public int getDistance() {

        return distance;
    }

    /**
     * Calculates the distance between two locations based on the difference between
     * their positions.
     * @param location1
     * @param location2
     * @return distance
     */
    public int calculateDistance(Location location1, Location location2) {
        return 0;
    }

    /**Calculates the routes duration based on its distance. Then updates the stop time
     * and stop date.
     * @param distance
     */
    public void calculateDuration(int distance){
    }


    /**Ranks a bike trip based on its distance
     *
     */
    public void rankRoute() {

    }

    public static void main(String[] args) {
        RetailLocation l1 = new RetailLocation("Nike", "Broadway", "Auckland");
        WifiLocation l2 = new WifiLocation(1, 2, "Vod", "66a", "11xd");
        Route r = new Route(l1, l2, 5, 6);
        r.calculateDistance(l1, l2);
        r.calculateDuration(r.getDistance());
        System.out.println(l1.getName() + " " + l2.getName());
    }

}