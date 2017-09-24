package dataEntryTest;

import GUIControllers.AddDataController;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ImportTest {

    @Test
    public void dateConversion(){
        String date1 = "2017-12-31";
        String date2 = "25-12-2017";
        String date3 = "2017/12/25";
        String date4 = "1111111111";
        String date5 = "2017-13-32"; // Edge case
        String[] date1Arr = AddDataController.convertDates(date1);
        String[] date2Arr = AddDataController.convertDates(date2);
        String[] date3Arr = AddDataController.convertDates(date3);
        String[] date4Arr = AddDataController.convertDates(date4);
        String[] date5Arr = AddDataController.convertDates(date5);
        assertTrue(date1Arr[0].equals("2017") &&date1Arr[1].equals("12") || date1Arr[2].equals("25"));
        assertTrue(date2Arr == null);
        assertTrue(date3Arr == null);
        assertTrue(date4Arr == null);
        assertTrue(date5Arr == null);

    }

    @Test
    public void timeChecking() {
        String time1 = "11:22:33";
        String time2 = "25-12-2017";
        String time3 = "11111111";
        String time4 = "23:59:59";// Edge case*/
        String time5 = "-3:-4:-5";
        assertTrue(AddDataController.checkTime(time1));
        assertFalse(AddDataController.checkTime(time2));
        assertFalse(AddDataController.checkTime(time3));
        assertTrue(AddDataController.checkTime(time4));
        assertFalse(AddDataController.checkTime(time5));
    }
}