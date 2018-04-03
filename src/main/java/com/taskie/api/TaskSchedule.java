package com.taskie.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import org.joda.time.DateTime;

import java.util.Map;

public class TaskSchedule {

    private final Map<DateTime, String> dateToUser;

    @JsonCreator
    public TaskSchedule(@JsonProperty("dateToUser") Map<DateTime, String> dateToUser) {
        this.dateToUser = dateToUser;
    }

    @JsonProperty("dateToUser")
    public Map<DateTime, String> getDateToUser() {
        return dateToUser;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("dateToUser", dateToUser)
                .toString();
    }
}
