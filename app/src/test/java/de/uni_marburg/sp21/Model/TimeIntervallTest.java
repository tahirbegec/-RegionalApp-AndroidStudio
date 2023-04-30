package de.uni_marburg.sp21.Model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TimeIntervallTest {

    String start;
    String end;
    int startHour;
    int startMinute;
    int endHour;
    int endMinute;
    TimeInterval timeInterval;

    @Before
    public void setUp() {
        start = "00:01";
        end = "23:59";
        startHour = 1;
        startMinute = 1;
        endHour = 5;
        endMinute = 5;
        timeInterval = new TimeInterval(start, end);
    }

    @Test
    public void createTimeIntervallTest() { Assert.assertFalse(timeInterval.toString().isEmpty()); }

    @Test
    public void inInBetweenTest() {
        Assert.assertTrue(timeInterval.isInbetween(2,50));
    }

    @Test
    public void toStringTest() {
        Assert.assertEquals("00:01 - 23:59", timeInterval.toString());
    }
}
