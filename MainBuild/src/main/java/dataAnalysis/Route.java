package dataAnalysis;

import main.helperFunctions;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Has methods for all main functionality of the routing.
 */

//Users should be able to search for trips based on search criteria. We need
//to find out what this search criteria is and find a Trip.

public class Route {
    private int duration, timesTaken=1, rank;
    private String name, startTime, stopTime, startDate, stopDate, startDay, startMonth, startYear, stopDay, stopMonth, stopYear, bikeid;
    private Location startLocation, endLocation, viaLocation;
    private double averageTime;

    //Two types of constructors as there is not always a viaLocation.

    public Route(Location start, Location end, String time, String stDay, String stMonth, String stYear) {
        startLocation = start;
        endLocation = end;
        startTime = time;
        startDay = stDay;
        startMonth = stMonth;
        startYear = stYear;
    }

    public Route(Location start, Location end, Location via, String time, String stDay, String stMonth, String stYear) {
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
    public Route(int tripDuration, String stTime, String spTime, String stDay, String stMonth, String stYear, String spDay,
                 String spMonth, String spYear, double stStationLat, double stStationLong, double endStationLat,
                 double endStationLong, int stStationID, int endStationID, String stStationAdr, String endStationAdr,
                 String bId) {
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
        startLocation = new StationLocation(stStationID, stStationLat, stStationLong, stStationAdr);
        endLocation = new StationLocation(endStationID, endStationLat, endStationLong, endStationAdr);
        calculateDistance(startLocation, endLocation);
        updateAverageTime(stTime, spTime);
        startDate = getDateString(startDay, startMonth, startYear);
        stopDate = getDateString(stopDay, stopMonth, stopYear);
    }


    public Route(int tripDuration, String stTime, String spTime, String stDay, String stMonth, String stYear, String spDay,
                 String spMonth, String spYear, double stStationLat, double stStationLong, double endStationLat,
                 double endStationLong, int stStationID, int endStationID, String stStationAdr, String endStationAdr,
                 String bId, int routeRank) {
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
        startLocation = new StationLocation(stStationID, stStationLat, stStationLong, stStationAdr);
        endLocation = new StationLocation(endStationID, endStationLat, endStationLong, endStationAdr);
        calculateDistance(startLocation, endLocation);
        updateAverageTime(stTime, spTime);
        startDate = getDateString(startDay, startMonth, startYear);
        stopDate = getDateString(stopDay, stopMonth, stopYear);
        rank = routeRank;
    }


    public Route(int stStationID, double stStationLat, double stStationLong, int endStationID, double endStationLat,
                 double endStationLong, String stStationAdr, String endStationAdr, String time, String stDay, String stMonth, String stYear) {
        startLocation = new StationLocation(stStationID, stStationLat, stStationLong, stStationAdr);
        endLocation = new StationLocation(endStationID, endStationLat, endStationLong, endStationAdr);
        startTime = time;
        startDay = stDay;
        startMonth = stMonth;
        startYear = stYear;
    }

    //Getter for primary key info

    public String getBikeID() { return bikeid;}

    public String getStartTime() { return startTime;}

    //Getters for duration and distance.

    public int getDuration() {
        return duration;
    }

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

    public String getStartDay() {return startDay;}

    public void setStartDay(String day) {startDay = day;}

    public String getStartMonth() {return startMonth;}

    public void setStartMonth(String month) {startMonth = month;}

    public String getStartYear() {return startYear;}

    public void setStartYear(String year) {startYear = year;}

    public String getStopDay() {return stopDay;}

    public void setStopDay(String day) {stopDay = day;}

    public String getStopMonth() {return stopMonth;}

    public void setStopMonth(String month) {stopMonth = month;}

    public String getStopYear() {return stopYear;}

    public void setStopYear(String year) {stopYear = year;}

    public String getStartAddress() {return startLocation.getAddress();}

    public String getEndAddress() {return endLocation.getAddress();}

    public String getViaLocation() {return viaLocation.getAddress();}

    public double getStartLatitude() {return startLocation.getLatitude();}

    public double getStartLongitude() {return startLocation.getLongitude();}

    public double getEndLatitude() {return endLocation.getLatitude();}

    public double getEndLongitude() {return endLocation.getLongitude();}

    public int getRank() {return rank;}

    public double getAverageTime() {return averageTime;}

    public double getDistance() {
        return helperFunctions.getDistance(getStartLatitude(), getStartLongitude(), getEndLatitude(), getEndLongitude());
    }




    /**
     * getDateString takes the day, month and year and separate integers and returns a more recognisable date format as
     * a string.
     *
     * @param day day is of type int. This is the day a route was started/finished on.
     * @param month month is of type int. This is the month a route was started/finished on.
     * @param year year is of type int. This is the year a route was started/finished on.
     * @return dateString, of type String. This a recognisable date format as a string.
     */
    private String getDateString(String day, String month, String year) {
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

    /**
     * Defines the hashCode function.
     * Allows for hashsets that don't collide on the same points
     * Collides if the other route has same bikeid, and start date/time
     * @return A hash value
     */
    public int hashCode() {
        return new HashCodeBuilder(137, 337)
                .append(bikeid)
                .append(startYear)
                .append(startMonth)
                .append(startDay)
                .append(startTime)
                .toHashCode();
    }

    /**
     * Defines the equals functions, allowing for easy equality testing
     * Fails if the other location has same bikeid, and start date/time
     * @param obj The object to compare
     * @return Equality boolean
     */
    public boolean equals(Object obj) {
        if (!( obj instanceof Route))
            return false;
        if (obj == this)
            return true;

        Route rhs = (Route) obj;
        return new EqualsBuilder()
                .append(bikeid, rhs.getBikeID())
                .append(startYear, rhs.getStartYear())
                .append(startMonth, rhs.getStartMonth())
                .append(startDay, rhs.getStartDay())
                .append(startTime, rhs.getStartTime())
                .isEquals();
    }

}