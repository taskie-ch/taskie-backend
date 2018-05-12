package com.taskie.core;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class HolidayAbsenceList {

    private final List<HolidayAbsence> absences;

    public HolidayAbsenceList() {
        this.absences = new ArrayList<>();
    }

    void addIfAbsent(DateTime start, DateTime end) {
        HolidayAbsence absence = new HolidayAbsence(start, end);
        if (!absences.contains(absence)) {
            absences.add(absence);
        }
    }

    boolean containsAbsence(DateTime date) {
        return absences.stream().anyMatch(holidayAbsence -> holidayAbsence.matches(date));
    }
}
