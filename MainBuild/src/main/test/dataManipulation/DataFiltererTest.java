package dataManipulation;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import dataAnalysis.Route;

import static org.junit.jupiter.api.Assertions.*;

class DataFiltererTest {

    @Test
    void filterByGender() {
        DataFilterer dataFilterer = new DataFilterer();
        ArrayList<Route> routes;
        String gender = "M";
        routes = dataFilterer.filterByGender(gender);
        System.out.println(routes);
        assertTrue(1 == 1);
    }

    @Test
    void filterByDate() {
        DataFilterer dataFilterer = new DataFilterer();
        Date upperLimit = new Date(2017, 5, 20);
        Date lowerLimit = new Date(2017, 5, 19);
        dataFilterer.filterByDate(upperLimit, lowerLimit);
        assertTrue();
    }

    @Test
    void filterByAge() {
        DataFilterer dataFilterer = new DataFilterer();
        int upperLimit = 65;
        int lowerLimit = 20;
        dataFilterer.filterByAge(upperLimit, lowerLimit);
        assertTrue();
    }

    @Test
    void filterByTime() {
        DataFilterer dataFilterer = new DataFilterer();
        Time upperLimit = new Time(11, 0, 0);
        Time lowerLimit = new Time(10,0,0);
        dataFilterer.filterByTime(upperLimit, lowerLimit);
        assertTrue();
    }

    @Test
    void filterByDuration() {
        DataFilterer dataFilterer = new DataFilterer();
        int minDuration = 0;
        int maxDuration = 10;
        dataFilterer.filterByDuration(minDuration, maxDuration);
        assertTrue();
    }

}