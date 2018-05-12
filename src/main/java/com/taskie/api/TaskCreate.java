package com.taskie.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import javax.annotation.concurrent.Immutable;
import java.util.List;
import java.util.Objects;

/**
 * JSON API definition of data required to create a task.
 */
@Immutable
public class TaskCreate {

    private final String title;
    private final String frequency;
    private final String start;
    private final int effort;
    private final List<String> userIds;

    @JsonCreator
    public TaskCreate(@JsonProperty("title") String title,
                      @JsonProperty("frequency") String frequency,
                      @JsonProperty("start") String start,
                      @JsonProperty("effort") int effort,
                      @JsonProperty("usersRotation") List<String> userIds) {
        this.title = title;
        this.frequency = frequency;
        this.start = start;
        this.effort = effort;
        this.userIds = userIds;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("frequency")
    public String getFrequency() {
        return frequency;
    }

    @JsonProperty("start")
    public String getStart() {
        return start;
    }

    @JsonProperty("effort")
    public int getEffort() {
        return effort;
    }

    @JsonProperty("usersRotation")
    public List<String> getUserIds() {
        return userIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskCreate that = (TaskCreate) o;
        return effort == that.effort &&
                Objects.equals(title, that.title) &&
                Objects.equals(frequency, that.frequency) &&
                Objects.equals(start, that.start) &&
                Objects.equals(userIds, that.userIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, frequency, start, effort, userIds);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("title", title)
                .add("frequency", frequency)
                .add("start", start)
                .add("effort", effort)
                .add("userIds", userIds)
                .toString();
    }
}
