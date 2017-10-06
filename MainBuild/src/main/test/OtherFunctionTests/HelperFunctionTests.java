package OtherFunctionTests;

import main.HelperFunctions;
import org.junit.Test;

import static org.junit.Assert.*;

public class HelperFunctionTests {

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
}