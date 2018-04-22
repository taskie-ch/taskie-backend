package com.taskie.core;

import org.joda.time.DateTime;

import java.util.function.Function;

public enum Frequency implements Function<DateTime, DateTime> {

    DAILY("Daily") {
        @Override
        public DateTime apply(DateTime dateTime) {
            return dateTime.plusDays(1);
        }
    },
    WEEKLY("Weekly") {
        @Override
        public DateTime apply(DateTime dateTime) {
            return dateTime.plusWeeks(1);
        }
    },
    FORTNIGHTLY("Fortnightly") {
        @Override
        public DateTime apply(DateTime dateTime) {
            return dateTime.plusWeeks(2);
        }
    },
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

    @Override
    public String toString() {
        return name;
    }
}
