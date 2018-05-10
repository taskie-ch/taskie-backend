package com.taskie.core;

import com.taskie.api.Id;
import com.taskie.api.TaskCreate;
import com.taskie.api.TaskInfo;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.requireNonNull;

public class Task {

    private final long id;
    private final String title;
    private final Effort effort;
    private final Frequency frequency;
    private final DateTime startDate;
    private final List<TaskOccurence> occurences;

    private Task(long id, String title, Effort effort, Frequency frequency,
                 DateTime startDate, List<TaskOccurence> occurences) {
        this.id = id;
        this.title = title;
        this.effort = effort;
        this.frequency = frequency;
        this.startDate = startDate;
        this.occurences = occurences;
    }

    public void complete() {
        occurences.stream().filter(TaskOccurence::isPending)
                .findFirst().ifPresent(TaskOccurence::complete);
    }

    public void uncomplete() {
        occurences.stream().filter(TaskOccurence::isDone)
                .findFirst().ifPresent(TaskOccurence::uncomplete);
    }

    private List<String> getRotationNames() {
        return occurences.stream()
                .map(TaskOccurence::getAssignee)
                .map(UserPrincipal::getName)
                .collect(Collectors.toList());
    }

    public Id deriveId() {
        return new Id(id);
    }

    public TaskInfo deriveInfo() {
        TaskOccurence occurence = occurences.get(0);
        return new TaskInfo(id, title, frequency.toString(), occurence.getDate().toString(),
                effort.getValue(), occurence.isDone(), getRotationNames());
    }

    public TaskCreate deriveCreate() {
        return new TaskCreate(title, frequency.toString(), occurences.get(0).getDate().toString(), effort.getValue());
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Effort getEffort() {
        return effort;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public List<TaskOccurence> getOccurences() {
        return occurences;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(TaskCreate taskCreate) {
        return new Builder(taskCreate);
    }

    public static Builder newBuilder(Task task) {
        return new Builder(task);
    }

    public static class Builder {

        private long id = -1;
        private String title;
        private Effort effort;
        private Frequency frequency;
        private DateTime startDate;
        private List<TaskOccurence> occurences = new ArrayList<>();

        private Builder() {
            // default private constructor
        }

        private Builder(Task task) {
            setId(task.getId());
            setTitle(task.getTitle());
            setEffort(task.getEffort());
            setFrequency(task.getFrequency());
            setStartDate(task.getStartDate());
            addOccurences(task.getOccurences());
        }

        public Builder(TaskCreate taskCreate) {
            setTitle(taskCreate.getTitle());
            setFrequency(Frequency.valueOf(taskCreate.getFrequency().toUpperCase()));
            setEffort(Effort.valueOf(taskCreate.getEffort()));
            setStartDate(DateTime.parse(taskCreate.getStart()));
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setEffort(Effort effort) {
            this.effort = effort;
            return this;
        }

        public Builder setFrequency(Frequency frequency) {
            this.frequency = frequency;
            return this;
        }

        public Builder setStartDate(DateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder addOccurences(List<TaskOccurence> occurences) {
            occurences.forEach(this::addOccurence);
            return this;
        }

        public Builder addOccurence(TaskOccurence occurence) {
            requireNonNull(occurence, "Task occurence is required");
            occurences.add(occurence);
            return this;
        }

        public Task build() {
            checkState(id >= 0, "Id is required");
            requireNonNull(title, "Title is required");
            requireNonNull(effort, "Effort is required");
            requireNonNull(frequency, "Frequency is required");
            requireNonNull(startDate, "Start date is required");
            return new Task(id, title, effort, frequency, startDate, occurences);
        }
    }
}
