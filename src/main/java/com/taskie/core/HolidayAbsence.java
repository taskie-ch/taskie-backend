package com.taskie.core;

import com.google.common.base.MoreObjects;
import org.joda.time.DateTime;

import java.util.Objects;

/**
 * Holiday absence entered by the user
 */
public class HolidayAbsence {

    private final DateTime from;
    private final DateTime to;

    HolidayAbsence(DateTime from, DateTime to) {
        this.from = from;
        this.to = to;
    }

    /**
     * @return from date
     */
    public DateTime getFrom() {
        return from;
    }

    /**
     * @return to date
     */
    public DateTime getTo() {
        return to;
    }

    /**
     * Checks if there is an absence for a given date.
     *
     * @param date date to check
     * @return {@code true} if there is an absence for the date
     */
    boolean matches(final DateTime date) {
        return from.isBefore(date) && to.isAfter(date);
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof HolidayAbsence)) return false;
        HolidayAbsence holidayAbsence = (HolidayAbsence) o;
        return Objects.equals(from, holidayAbsence.from) &&
                Objects.equals(to, holidayAbsence.to);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("from", from)
                .add("to", to)
                .toString();
    }
}
