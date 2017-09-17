package dataAnalysis;

/**
 * Has methods for all main functionality of the routing.
 */

//Users should be able to search for trips based on search criteria. We need
//to find out what this search criteria is and find a Trip.

public class Route {
    private int duration, distance, startDay, startMonth, startYear, stopDay, stopMonth, stopYear, bikeid, timesTaken=1;
    private String name, startTime, stopTime, startDate, stopDate;
    private Location startLocation, endLocation, viaLocation;
    private double averageTime;

    //Two types of constructors as there is not always a viaLocation.

    public Route(Location start, Location end, String time, int stDay, int stMonth, int stYear) {
        startLocation = start;
        endLocation = end;
        startTime = time;
        startDay = stDay;
        startMonth = stMonth;
        startYear = stYear;
    }

    public Route(Location start, Location end, Location via, String time, int stDay, int stMonth, int stYear) {
        startLocation = start;
        endLocation = end;
        viaLocation = via;
        startTime = time;
        startDay = stDay;
        startMonth = stMonth;
        startYear = stYear;
    }

    /**
     * Constructor for Route class, used by Datafilterer class when filtering records from the database.
     */
    public Route(int tripDuration, String stTime, String spTime, int stDay, int stMonth, int stYear, int spDay,
                 int spMonth, int spYear, double stStationLat, double stStationLong, double endStationLat,
                 double endStationLong, int stStationID, int endStationID, int bId) {
        duration = tripDuration;
        startTime = stTime;
        startDay = stDay;
        startMonth = stMonth;
        startYear = stYear;
        stopTime = spTime;
        stopDay = spDay;
        stopMonth = spMonth;
        stopYear = spYear;
        bikeid = bId;
        startLocation = new StationLocation(stStationID, stStationLat, stStationLong);
        endLocation = new StationLocation(endStationID, endStationLat, endStationLong);
        calculateDistance(startLocation, endLocation);
        updateAverageTime(stTime, spTime);
        startDate = getDateString(startDay, startMonth, startYear);
        stopDate = getDateString(stopDay, stopMonth, stopYear);
    }

    //Getter for primary key info

    public int getBikeID() { return bikeid;}

    public String getStartTime() { return startTime;}

    //Getters for duration and distance.

    public int getDuration() {
        return duration;
    }

    public int getDistance() {return distance;}

    public String getName() {return name;}

    public void setName(String input) {name = input;}

    public int getTimesTaken() {return timesTaken;}

    //More getters and setters.

    public void setStartTime(String time) {startTime = time;}

    public String getStopTime() {return stopTime;}

    public void setStopTime(String time) {stopTime = time;}

    public String getStartDate() {return startDate;}

    public void setStartDate() {startDate = getDateString(startDay, startMonth, startYear);}

    public String getStopDate() {return stopDate;}

    public void setStopDate() {stopDate = getDateString(stopDay, stopMonth, stopYear);}

    public int getStartDay() {return startDay;}

    public void setStartDay(int day) {startDay = day;}

    public int getStartMonth() {return startMonth;}

    public void setStartMonth(int month) {startMonth = month;}

    public int getStartYear() {return startYear;}

    public void setStartYear(int year) {startYear = year;}

    public int getStopDay() {return stopDay;}

    public void setStopDay(int day) {stopDay = day;}

    public int getStopMonth() {return stopMonth;}

    public void setStopMonth(int month) {stopMonth = month;}

    public int getStopYear() {return stopYear;}

    public void setStopYear(int year) {stopYear = year;}

    public Location getStartLocation() {return startLocation;}

    public Location getEndLocation() {return endLocation;}

    public Location getViaLocation() {return viaLocation;}

    public double getAverageTime() {return averageTime;}


    /**
     * getDateString takes the day, month and year and separate integers and returns a more recognisable date format as
     * a string.
     *
     * @param day day is of type int. This is the day a route was started/finished on.
     * @param month month is of type int. This is the month a route was started/finished on.
     * @param year year is of type int. This is the year a route was started/finished on.
     * @return dateString, of type String. This a recognisable date format as a string.
     */
    private String getDateString(int day, int month, int year) {
        String dateString = day + "/" + month + "/" + year;
        return dateString;
    }


    /**
     * Calculates the distance between two locations based on the difference between
     * their positions.
     * @param location1;
     * @param location2;
     * @return distance
     */
    public int calculateDistance(Location location1, Location location2) {
        return 0;
    }

    /**Calculates the routes duration based on its distance. Then updates the stop time
     * and stop date.
     * @param distance;
     */
    public void calculateDuration(int distance){
    }


    /**Ranks a bike trip based on its distance
     *
     */
    public void rankRoute() {

    }

    /**
     * Updates the average route time.
     * @param stTime;
     * @param spTime;
     */
    public void updateAverageTime(String stTime, String spTime) {
        timesTaken++;
        // convert stTime and spTime to doubles;
        // double newTime = spTime - stTime;
        // averageTime = (averageTime + newTime) / timesTaken
    }

//    public static void main(String[] args) {
//        RetailLocation l1 = new RetailLocation("Nike", "Broadway", "Auckland");
//        WifiLocation l2 = new WifiLocation(1, 2, "Vod", "66a", "11xd");
//        Route r = new Route(l1, l2, "5", "6");
//        r.calculateDistance(l1, l2);
//        r.calculateDuration(r.getDistance());
//        System.out.println(l1.getName() + " " + l2.getName());
//    }

}