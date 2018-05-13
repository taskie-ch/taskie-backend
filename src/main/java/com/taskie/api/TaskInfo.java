package com.taskie.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import javax.annotation.concurrent.Immutable;
import java.util.List;
import java.util.Objects;

/**
 * JSON API definition of a task.
 */
@Immutable
public final class TaskInfo {

    private final long id;
    private final String title;
    private final String frequency;
    private final String start;
    private final int effort;
    private final List<String> userIds;

    @JsonCreator
    public TaskInfo(@JsonProperty("id") long id,
                    @JsonProperty("title") String title,
                    @JsonProperty("frequency") String frequency,
                    @JsonProperty("start") String start,
                    @JsonProperty("effort") int effort,
                    @JsonProperty("usersRotation") List<String> userIds) {
        this.id = id;
        this.title = title;
        this.frequency = frequency;
        this.start = start;
        this.effort = effort;
        this.userIds = userIds;
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

    @JsonProperty("usersRotation")
    public List<String> getUserIds() {
        return userIds;
    }

    public TaskCreate deriveTaskCreate() {
        return new TaskCreate(title, frequency, start, effort, userIds);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TaskInfo)) return false;
        TaskInfo taskInfo = (TaskInfo) o;
        return id == taskInfo.id &&
                effort == taskInfo.effort &&
                Objects.equals(title, taskInfo.title) &&
                Objects.equals(frequency, taskInfo.frequency) &&
                Objects.equals(start, taskInfo.start) &&
                Objects.equals(userIds, taskInfo.userIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, frequency, start, effort, userIds);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("title", title)
                .add("frequency", frequency)
                .add("start", start)
                .add("effort", effort)
                .add("userIds", userIds)
                .toString();
    }
}
