package dataManipulation;

import dataAnalysis.Route;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;


import static org.junit.Assert.*;

//----------------------------------------------------------------
//--------ALL TESTS MUST BE RUN WITH THE TEST DATA FILES----------
//----------------------------------------------------------------

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
    public void filterByGenderFemale() throws Exception {
        ArrayList<Integer> bikeID = new ArrayList<>();
        bikeID.add(21997);
        bikeID.add(22794);
        bikeID.add(15721);
        bikeID.add(23650);
        bikeID.add(19804);
        bikeID.add(21624);
        bikeID.add(24232);
        bikeID.add(15713);
        bikeID.add(20945);
        bikeID.add(19039);
        bikeID.add(20258);
        routes = dataFilterer.filter(2, null, null, -1, -1, null, null, -1, -1);
        int size = routes.size();
        for (int i = 0; i < size; i++){
            assertTrue(bikeID.get(i) == routes.get(i).getBikeID());
        }
    }


    @Test
    public void filterByGenderFemaleAge0_20() throws Exception {
        ArrayList<Integer> bikeID = new ArrayList<>();
        bikeID.add(21997);
        bikeID.add(23650);
        bikeID.add(19039);
        bikeID.add(20258);
        routes = dataFilterer.filter(2, null, null, 30, 40, null, null, -1, -1);
        int size = routes.size();
        for (int i = 0; i < size; i++){
            assertTrue(bikeID.get(i) == routes.get(i).getBikeID());
        }
    }


    @Test
    public void filterByGenderFemaleAge0_20Duration1000_2000() throws Exception {
        ArrayList<Integer> bikeID = new ArrayList<>();
        routes = dataFilterer.filter(2, null, null, 30, 40, null, null, 1000, 2000);
        int size = routes.size();
        for (int i = 0; i < size; i++){
            assertTrue(bikeID.get(i) == routes.get(i).getBikeID());
        }
    }


    @Test
    public void filterByIncompleteData() throws Exception {
        ArrayList<Integer> bikeID = new ArrayList<>();
        bikeID.add(17057);
        bikeID.add(17109);
        bikeID.add(24021);
        bikeID.add(14769);
        bikeID.add(16475);
        bikeID.add(19424);
        bikeID.add(14823);
        bikeID.add(22661);
        routes = dataFilterer.filter(0, null, null, -1, -1, null, null, -1, -1);
        int size = routes.size();
        for (int i = 0; i < size; i++){
            assertTrue(bikeID.get(i) == routes.get(i).getBikeID());
        }
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