package com.taskie.core;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FrequencyTest {

    private static final DateTime DATE = DateTime.now();

    @Test
    public void normalCaseString() {
        assertEquals("DAILY as string", "Daily", Frequency.DAILY.toString());
        assertEquals("MONTHLY as string", "Monthly", Frequency.MONTHLY.toString());
        assertEquals("WEEKLY as string", "Weekly", Frequency.WEEKLY.toString());
        assertEquals("FORTNIGHTLY as string", "Fortnightly", Frequency.FORTNIGHTLY.toString());
    }

    @Test
    public void daily() {
        assertEquals("Match day increment", DATE.plusDays(1), Frequency.DAILY.apply(DATE));
    }

    @Test
    public void weekly() {
        assertEquals("Match week increment", DATE.plusWeeks(1), Frequency.WEEKLY.apply(DATE));
    }

    @Test
    public void fortnightly() {
        assertEquals("Match week increment", DATE.plusWeeks(2), Frequency.FORTNIGHTLY.apply(DATE));
    }

    @Test
    public void monthly() {
        assertEquals("Match month increment", DATE.plusMonths(1), Frequency.MONTHLY.apply(DATE));
    }
}
