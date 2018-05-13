package com.taskie.core;

import com.taskie.AbstractEntityTest;
import org.joda.time.DateTime;

public class HolidayAbsenceTest extends AbstractEntityTest<HolidayAbsence> {

    public HolidayAbsenceTest() {
        super(new HolidayAbsence(DateTime.now(), DateTime.now()));
    }
}