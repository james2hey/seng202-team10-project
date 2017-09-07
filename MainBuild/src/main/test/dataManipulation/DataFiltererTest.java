package dataManipulation;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Time;

import static org.junit.jupiter.api.Assertions.*;

class DataFiltererTest {

    @Test
    void filterByGender() {
        DataFilterer dataFilterer = new DataFilterer();
        String gender = "M";
        dataFilterer.filterByGender(gender);
        //assertTrue();
    }

    @Test
    void filterByDate() {
        DataFilterer dataFilterer = new DataFilterer();
        Date upperLimit = new Date(2017, 5, 20);
        Date lowerLimit = new Date(2017, 5, 19);
        dataFilterer.filterByDate(upperLimit, lowerLimit);
        //assertTrue();
    }

    @Test
    void filterByAge() {
        DataFilterer dataFilterer = new DataFilterer();
        int upperLimit = 65;
        int lowerLimit = 20;
        dataFilterer.filterByAge(upperLimit, lowerLimit);
        //assertTrue();
    }

    @Test
    void filterByTime() {
        DataFilterer dataFilterer = new DataFilterer();
        Time upperLimit = new Time(11, 0, 0);
        Time lowerLimit = new Time(10,0,0);
        dataFilterer.filterByTime(upperLimit, lowerLimit);
        //assertTrue();
    }

}