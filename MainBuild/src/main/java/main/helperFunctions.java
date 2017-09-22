package main;

import java.util.concurrent.TimeUnit;

/**
 * Created by jes143 on 22/09/17.
 * Includes some useful static helper functions that may be applicable to multiple classes and that don't fit anywhere
 */
public class helperFunctions {

    /**
     * Takes four doubles as two points and returns the distance between them. Uses the haversine formula as lat long coords are circle based.
     * @param lat1 Point 1 Lat
     * @param lng1 Point 1 Long
     * @param lat2 Point 2 Lat
     * @param lng2 Point 2 Long
     * @return Distance
     */
    public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
        int r = 6371; // average radius of the earth in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                        * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = r * c;
        return d;
    }

    public static String secondsToString(int seconds) {
        int days;
        int hours;
        int mins;
        int secs;
        String out = "";

        days = seconds / 86400;
        out += ((days != 0) ? days + "d, " : "");
        hours = (seconds % 86400) / 3600 ;
        out += ((hours != 0 || days != 0) ? hours + "h, " : "");
        mins = ((seconds % 86400) % 3600 ) / 60;
        out += ((mins != 0 || days != 0 || hours != 0) ? mins + "m, " : mins);
        secs = (seconds % 86400  % 3600 ) % 60;
        out += secs + "s";
        System.out.println(days);
        System.out.println(hours);
        System.out.println(mins);
        System.out.println(secs);
        return out;
    }

}
