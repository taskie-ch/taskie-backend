package com.taskie.core;

import org.joda.time.DateTime;

import java.util.function.Function;

/**
 * Frequency of a task
 */
public enum Frequency implements Function<DateTime, DateTime> {

    /**
     * One day
     */
    DAILY("Daily") {
        @Override
        public DateTime apply(DateTime dateTime) {
            return dateTime.plusDays(1);
        }
    },
    /**
     * Seven days
     */
    WEEKLY("Weekly") {
        @Override
        public DateTime apply(DateTime dateTime) {
            return dateTime.plusWeeks(1);
        }
    },
    /**
     * Fourteen days
     */
    FORTNIGHTLY("Fortnightly") {
        @Override
        public DateTime apply(DateTime dateTime) {
            return dateTime.plusWeeks(2);
        }
    },
    /**
     * One month
     */
    MONTHLY("Monthly") {
        @Override
        public DateTime apply(DateTime dateTime) {
            return dateTime.plusMonths(1);
        }
    };

    private final String name;

    Frequency(String name) {
        this.name = name;
    }

    /**
     * @return sprint representation of enum
     */
    @Override
    public String toString() {
        return name;
    }
}
