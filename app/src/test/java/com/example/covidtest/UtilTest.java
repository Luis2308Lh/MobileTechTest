package com.example.covidtest;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class UtilTest extends TestCase {

    @Test
    public void testStringToInt() {
        Util util = new Util();
        String input = "1231";
        int output;
        int expected = 1231;
        output = util.stringToInt(input);
        assertEquals(expected,output);
    }
    @Test
    public void testStringToDouble() {
        Util util = new Util();
        String input = "1231";
        double output;
        double expected = 1231;
        output = util.stringToDouble(input);
        assertEquals(expected,output);
    }
    @Test
    public void testStringToDate() {
        Util util = new Util();
        String input = "2021/10/14 14:21:35+00";
        Date output;
        Date expected = new GregorianCalendar(2021, Calendar.OCTOBER,14,14,21,35).getTime();
        output = util.stringToDate(input);
        assertEquals(expected,output);
    }


    @Test
    public void testTestDateToString() {
        Util util = new Util();
        String expected = "2021/10/14 14:21:35";
        String output;
        Date input = new GregorianCalendar(2021, Calendar.OCTOBER,14,14,21,35).getTime();
        output = util.dateToString(input);
        assertEquals(expected,output);
    }
}