package com.taskie.core;

import com.google.common.base.MoreObjects;
import org.joda.time.DateTime;

import java.util.Objects;

/**
 * Holiday absence entered by the user
 */
public class Absence {

    private final DateTime from;
    private final DateTime to;

    public Absence(DateTime from, DateTime to) {
        this.from = from;
        this.to = to;
    }

    public DateTime getFrom() {
        return from;
    }

    public DateTime getTo() {
        return to;
    }

    public boolean match(final DateTime date) {
        return from.isBefore(date) && to.isAfter(date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Absence absence = (Absence) o;
        return Objects.equals(from, absence.from) &&
                Objects.equals(to, absence.to);
    }

    @Override
    public int hashCode() {
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
