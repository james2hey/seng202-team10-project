package OtherFunctionTests;

import dataAnalysis.Cyclist;
import dataAnalysis.Route;
import main.HelperFunctions;
import org.junit.Test;

import static org.junit.Assert.*;

public class HelperFunctionTests {

//    Route(int tripDuration, String stTime, String spTime, String stDay, String stMonth, String stYear,
//          String spDay, String spMonth, String spYear, double stStationLat, double stStationLong,
//          double endStationLat, double endStationLong, int stStationID, int endStationID, String stStationAdr,
//          String endStationAdr, String bId, int riderGender, String riderType, int riderAge, String list,
//          float rdistance)

//    Route(rsRoute.getInt("tripduration"), rsRoute.getString("start_time"),
//            rsRoute.getString("end_time"), rsRoute.getString("start_day"),
//                                rsRoute.getString("start_month"), rsRoute.getString("start_year"),
//                                rsRoute.getString("end_day"), rsRoute.getString("end_month"),
//                                rsRoute.getString("end_year"), rsRoute.getDouble("start_latitude"),
//                                rsRoute.getDouble("start_longitude"), rsRoute.getDouble("end_latitude"),
//                                rsRoute.getDouble("end_longitude"), rsRoute.getInt("start_station_id"),
//                                rsRoute.getInt("end_station_id"), rsRoute.getString("start_station_name"),
//                                rsRoute.getString("end_station_name"), rsRoute.getString("bikeid"),
//                                rsRoute.getInt("gender"), rsRoute.getString("usertype"),
//                                rsRoute.getInt("birth_year"), rsRoute.getString("list_name"),
//                                rsFavourites.getFloat("distance"));

    public Cyclist createCyclistWithRoutes() {
        Cyclist cyclist = new Cyclist("Tester");

        Route r1 = new Route(0, "0", "0", "0", "0", "0",
                "0", "0",
                "0", 0,
                0, 0,
                0, 0,
                0, "0",
                "0", "0",
                0, "0",
                0, "0");
        Route r2 = new Route(0, "0", "0", "0", "0", "0",
                "0", "0",
                "0", 0,
                0, 0,
                0, 0,
                0, "0",
                "0", "0",
                0, "0",
                0, "0");
        return cyclist;
    }

    @Test
    public void dateConversion(){
        String date1 = "2017-12-31";
        String date2 = "25-12-2017";
        String date3 = "2017/12/25";
        String date4 = "1111111111";
        String date5 = "2017-13-32"; // Edge case
        String[] date1Arr = HelperFunctions.convertDates(date1);
        String[] date2Arr = HelperFunctions.convertDates(date2);
        String[] date3Arr = HelperFunctions.convertDates(date3);
        String[] date4Arr = HelperFunctions.convertDates(date4);
        String[] date5Arr = HelperFunctions.convertDates(date5);
        assertTrue(date1Arr[0].equals("2017") &&date1Arr[1].equals("12") || date1Arr[2].equals("25"));
        assertTrue(date2Arr == null);
        assertTrue(date3Arr == null);
        assertTrue(date4Arr == null);
        assertTrue(date5Arr == null);

    }

    @Test
    public void timeChecking() {
        String time1 = "00:00:00";
        String time2 = "25-12-2017";
        String time3 = "11111";
        String time4 = "23:59"; // Edge case
        String time5 = "-3:-4";
        assertTrue(HelperFunctions.checkTime(time1));
        assertFalse(HelperFunctions.checkTime(time2));
        assertFalse(HelperFunctions.checkTime(time3));
        assertTrue(HelperFunctions.checkTime(time4));
        assertFalse(HelperFunctions.checkTime(time5));
    }

    @Test
    public void getDurationTests() {
        int oneSecond = HelperFunctions.getDuration("2017", "01", "01", "00:00:00",
                "2017", "01", "01", "00:00:01");
        int negativeOneSecond = HelperFunctions.getDuration("2017", "01", "01", "00:00:01",
                "2017", "01", "01", "00:00:00");
        int oneDay = HelperFunctions.getDuration("2017", "01", "01", "00:00:00",
                "2017", "01", "02", "00:00:00");
        int janurary = HelperFunctions.getDuration("2017", "01", "01", "00:00:00",
                "2017", "02", "01", "00:00:00");
        int february = HelperFunctions.getDuration("2017", "02", "01", "00:00:00",
                "2017", "03", "01", "00:00:00");
        assertTrue(oneSecond == 1);
        assertTrue(negativeOneSecond == -1);
        assertTrue(oneDay == 86400); //60*60*24
        assertTrue(janurary > february);
    }

    @Test
    public void secondsToStringTests() {
        String oneSecond = HelperFunctions.secondsToString(1);
        String oneMinute = HelperFunctions.secondsToString(60);
        String oneHour = HelperFunctions.secondsToString(3600);
        String oneDay = HelperFunctions.secondsToString(86400);
        String oneOfEach = HelperFunctions.secondsToString(90061);
        assertTrue(oneSecond.equals("1s"));
        assertTrue(oneMinute.equals("1m, 0s"));
        assertTrue(oneHour.equals("1h, 0m, 0s"));
        assertTrue(oneDay.equals("1d, 0h, 0m, 0s"));
        assertTrue(oneOfEach.equals("1d, 1h, 1m, 1s"));
    }

    @Test
    public void getDistance1() {
        double lat1 = -43.698442;
        double lon1 = 172.780681;
        double lat2 = -42.424081;
        double lon2 = 146.074937;
        double dist = HelperFunctions.getDistance(lat1, lon1, lat2, lon2);
        assertEquals(2164.85, dist, 2.16485);
    }

    @Test
    public void getDistance2() {
        double lat1 = -8.581382;
        double lon1 = 68.560900;
        double lat2 = 52.679915;
        double lon2 = 68.001759;
        double dist = HelperFunctions.getDistance(lat1, lon1, lat2, lon2);
        System.out.println(dist);
        assertEquals( 6812.16, dist, 6.81216);
    }

    @Test
    public void getDistancePoleToPole() {
        double lat1 = 90.0;
        double lon1 = 0.0;
        double lat2 = -90.0;
        double lon2 = 0;
        double dist = HelperFunctions.getDistance(lat1, lon1, lat2, lon2);
        System.out.println(dist);
        assertEquals( 20035, dist, 20.035);
    }


    @Test
    public void checkDateDetailsValid1() {
        boolean invalidDate = HelperFunctions.checkDateDetails(1, 1, 2017);
        assertFalse(invalidDate);
    }

    @Test
    public void checkDateDetailsValid2() {
        boolean invalidDate = HelperFunctions.checkDateDetails(5, 9, 2012);
        assertFalse(invalidDate);
    }

    @Test
    public void checkDateDetailsInvalidYear() {
        boolean invalidDate = HelperFunctions.checkDateDetails(1, 1, 2018);
        assertTrue(invalidDate);
    }

    @Test
    public void checkDateDetailsInvalidMonth() {
        boolean invalidDate = HelperFunctions.checkDateDetails(1, 0, 2017);
        assertTrue(invalidDate);
    }

    @Test
    public void checkDateDetailsInvalidDay() {
        boolean invalidDate = HelperFunctions.checkDateDetails(32, 1, 2017);
        assertTrue(invalidDate);
    }

    @Test
    public void calculateDistanceCycled1() {
        Cyclist cyclist = new Cyclist("Tester");
        double distance = HelperFunctions.calculateDistanceCycled(cyclist);
        assertEquals(0, distance, 0.1);
    }

    @Test
    public void calculateDistanceCycled2() {
        Cyclist cyclist = new Cyclist("Tester"); //Need geocoding here.
    }

    @Test
    public void calculateDistanceCycled3() {
        Cyclist cyclist = new Cyclist("Tester");
    }


    @Test
    public void cacluateAverageDistance1() {

    }

    @Test
    public void cacluateAverageDistance2() {

    }

    @Test
    public void cacluateAverageDistance3() {

    }

    @Test
    public void calculateShortestRoute1() {

    }

    @Test
    public void calculateShortestRoute2() {

    }

    @Test
    public void calculateShortestRoute3() {

    }


    @Test
    public void calculateLongestRoute1() {

    }

    @Test
    public void calculateLongestRoute2() {

    }

    @Test
    public void calculateLongestRoute3() {

    }

}