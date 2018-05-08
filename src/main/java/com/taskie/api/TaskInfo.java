package com.taskie.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.List;
import java.util.Objects;

public class TaskInfo {

    private final long id;
    private final String title;
    private final String frequency;
    private final String start;
    private final int effort;
    private final boolean done;
    private final List<String> users;

    @JsonCreator
    public TaskInfo(@JsonProperty("id") long id,
                    @JsonProperty("title") String title,
                    @JsonProperty("frequency") String frequency,
                    @JsonProperty("start") String start,
                    @JsonProperty("effort") int effort,
                    @JsonProperty("done") boolean done,
                    @JsonProperty("users") List<String> users) {
        this.id = id;
        this.title = title;
        this.frequency = frequency;
        this.start = start;
        this.effort = effort;
        this.done = done;
        this.users = users;
    }

    @JsonProperty("id")
    public long getId() {
        return id;
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

    @JsonProperty("done")
    public boolean isDone() {
        return done;
    }

    @JsonProperty("users")
    public List<String> getUsers() {
        return users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskInfo taskInfo = (TaskInfo) o;
        return id == taskInfo.id &&
                effort == taskInfo.effort &&
                done == taskInfo.done &&
                Objects.equals(title, taskInfo.title) &&
                Objects.equals(frequency, taskInfo.frequency) &&
                Objects.equals(start, taskInfo.start) &&
                Objects.equals(users, taskInfo.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, frequency, start, effort, done, users);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("title", title)
                .add("frequency", frequency)
                .add("start", start)
                .add("effort", effort)
                .add("done", done)
                .add("users", users)
                .toString();
    }
}
