package dataManipulation;

import dataAnalysis.Route;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import main.DatabaseManager;


import static org.junit.Assert.*;

/**
 * Created by mki58 on 7/09/17.
 */
public class DataFiltererTest {

    DataFilterer dataFilterer = new DataFilterer();
    ArrayList<Route> routes = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void convertAges() throws Exception {
    }

    @Test
    public void clearRoutes() throws Exception {
    }

    @Test
    public void connect() throws Exception {
    }

    @Test
    public void addAndToStmt() throws Exception {
    }

    @Test
    public void generateQueryString() throws Exception {
    }

    @Test
    public void setQueryParameters() throws Exception {
    }

    @Test
    public void filter() throws Exception {
        routes = dataFilterer.filter(-1, "e", "e", -1, -1, null, null, -1, -1);
        int size = routes.size();
        for (int i = 0; i < size; i++){
            System.out.println(routes.get(i).getDuration());
        }
        assertTrue(1 == 1);
    }

//    @Test
//    public void filterByGender() throws Exception {
//        DatabaseManager.connect();
//        DataFilterer dataFilterer = new DataFilterer();
//        String gender = "M";
//        ArrayList<Route> routes = dataFilterer.filterByGender(gender);
//        System.out.println(routes.get(0).getDuration());
//        assertTrue(1 == 1);
//    }
//
//    @Test
//    public void filterByDate() throws Exception {
//        DataFilterer dataFilterer = new DataFilterer();
//        Date upperLimit = new Date(2017, 5, 20);
//        Date lowerLimit = new Date(2017, 5, 19);
//        dataFilterer.filterByDate(upperLimit, lowerLimit);
//        //assertTrue();
//    }
//
//    @Test
//    public void filterByAge() throws Exception {
//        DataFilterer dataFilterer = new DataFilterer();
//        int upperLimit = 65;
//        int lowerLimit = 20;
//        dataFilterer.filterByAge(upperLimit, lowerLimit);
//        //assertTrue();
//    }
//
//    @Test
//    public void filterByTime() throws Exception {
//        DataFilterer dataFilterer = new DataFilterer();
//        Time upperLimit = new Time(11, 0, 0);
//        Time lowerLimit = new Time(10,0,0);
//        dataFilterer.filterByTime(upperLimit, lowerLimit);
//        //assertTrue();
//    }
//
//    @Test
//    public void filterByDuration() throws Exception {
//        DataFilterer dataFilterer = new DataFilterer();
//        int upperDuration = 65;
//        int lowerDuration = 20;
//        dataFilterer.filterByDuration(upperDuration, lowerDuration);
//        //assertTrue();
//    }

}