package mapElements;

/**
 * Has methods for all main functionality of the routing.
 */

//TODO: Users should be able to search for trips based on search criteria. We need
//      to find out what this search critera is and find a Trip.

public class Route {
    private int tripDuration, startTime, startDate, stopTime, stopDate, distance;
    private Station startStation, endStation;


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



}