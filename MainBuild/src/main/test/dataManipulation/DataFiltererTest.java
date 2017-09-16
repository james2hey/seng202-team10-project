package dataManipulation;

import dataAnalysis.Route;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

//----------------------------------------------------------------
//--------ALL TESTS MUST BE RUN WITH THE TEST DATA FILES----------
//----------------------------------------------------------------
//To create database using test data, delete the current database. Then add '-test' onto the end of the string of each
// data file name in the main class. Run the program to create a new database using the data in these test files.

public class DataFiltererTest {

    private DataFilterer dataFilterer = new DataFilterer();
    private ArrayList<Route> routes = new ArrayList<>();

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
    public void filterByGenderFemaleAge50_100() throws Exception {
        ArrayList<Integer> bikeID = new ArrayList<>();
        bikeID.add(22794);
        bikeID.add(19804);
        bikeID.add(24232);
        routes = dataFilterer.filter(2, null, null, 50, 100, null, null, -1, -1);
        int size = routes.size();
        for (int i = 0; i < size; i++){
            assertTrue(bikeID.get(i) == routes.get(i).getBikeID());
        }
    }

    @Test
    public void filterByGenderFemaleDuration1000_2000() throws Exception {
        ArrayList<Integer> bikeID = new ArrayList<>();
        bikeID.add(24232);
        bikeID.add(15713);
        bikeID.add(20945);
        routes = dataFilterer.filter(2, null, null, -1, -1, null, null, 1000, 2000);
        int size = routes.size();
        for (int i = 0; i < size; i++){
            assertTrue(bikeID.get(i) == routes.get(i).getBikeID());
        }
    }

    @Test
    public void filterByGenderFemaleDuration0_400() throws Exception {
        ArrayList<Integer> bikeID = new ArrayList<>();
        bikeID.add(15721);
        routes = dataFilterer.filter(2, null, null, -1, -1, null, null, 0, 400);
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

    @Test
    public void filterByGenderMale() throws Exception {
        //Only testing first and last 10 records from test database with this filter applied
        ArrayList<Integer> bikeID = new ArrayList<>();
        bikeID.add(22285);
        bikeID.add(17827);
        bikeID.add(14562);
        bikeID.add(15788);
        bikeID.add(24183);
        bikeID.add(15747);
        bikeID.add(23933);
        bikeID.add(23993);
        bikeID.add(22541);
        bikeID.add(22193);

        bikeID.add(22965);
        bikeID.add(21223);
        bikeID.add(17569);
        bikeID.add(21619);
        bikeID.add(24119);
        bikeID.add(14785);
        bikeID.add(18591);
        bikeID.add(22478);
        bikeID.add(23386);
        bikeID.add(15861);

        routes = dataFilterer.filter(1, null, null, -1, -1, null, null, -1, -1);
        for (int i = 0; i < 10; i++){
            assertTrue(bikeID.get(i) == routes.get(i).getBikeID());
        }
        int size = routes.size();
        int j = 10;
        for (int i = size - 10; i < size; i++){
            assertTrue(bikeID.get(j) == routes.get(i).getBikeID());
            j++;
        }
    }


    @Test
    public void filterByDate() throws Exception {
//        ArrayList<Integer> bikeID = new ArrayList<>();
//        bikeID.add(15721);
        routes = dataFilterer.filter(-1, "00/00/0000", "01/01/2016", -1, -1, null, null, -1, -1);
        int size = routes.size();
        System.out.println(size);
        assertTrue(1 == 1);
    }


    @Test
    public void filterByTime000000_000100() throws Exception {
        ArrayList<Integer> bikeID = new ArrayList<>();
        bikeID.add(22285);
        bikeID.add(17827);
        bikeID.add(21997);
        routes = dataFilterer.filter(-1, null, null, -1, -1, "00:00:00", "00:01:00", -1, -1);
        int size = routes.size();
        for (int i = 0; i < size; i++) {
            assertTrue(bikeID.get(i) == routes.get(i).getBikeID());
        }
    }


    @Test
    public void filterByTime001130_001200() throws Exception {
        ArrayList<Integer> bikeID = new ArrayList<>();
        bikeID.add(21624);
        routes = dataFilterer.filter(-1, null, null, -1, -1, "00:11:30", "00:12:00", -1, -1);
        int size = routes.size();
        for (int i = 0; i < size; i++) {
            assertTrue(bikeID.get(i) == routes.get(i).getBikeID());
        }
    }


    @Test
    public void filterByTime000000_000000() throws Exception {
        ArrayList<Integer> bikeID = new ArrayList<>();
        bikeID.add(21624);
        routes = dataFilterer.filter(-1, null, null, -1, -1, "00:00:00", "00:00:00", -1, -1);
        int size = routes.size();
        for (int i = 0; i < size; i++) {
            assertTrue(bikeID.get(i) == routes.get(i).getBikeID());
        }
    }


    @Test
    public void filterByTime001500_245959() throws Exception {
        ArrayList<Integer> bikeID = new ArrayList<>();
        bikeID.add(22478);
        bikeID.add(15713);
        bikeID.add(20945);
        bikeID.add(19039);
        bikeID.add(20258);
        bikeID.add(23386);
        bikeID.add(15861);
        routes = dataFilterer.filter(-1, null, null, -1, -1, "00:15:00", "24:59:59", -1, -1);
        int size = routes.size();
        for (int i = 0; i < size; i++) {
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