package com.taskie.core;

import com.taskie.api.Id;
import com.taskie.api.TaskCreate;
import com.taskie.api.TaskInfo;
import org.joda.time.DateTime;

import java.util.Collection;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.requireNonNull;

public class Task {

    private final long id;
    private final String title;
    private final Effort effort;
    private final Frequency frequency;
    private final DueDate start;
    private final Rotation rotation;

    private Task(long id, String title, Effort effort, Frequency frequency,
                 DueDate start, Rotation rotation) {
        this.id = id;
        this.title = title;
        this.effort = effort;
        this.frequency = frequency;
        this.start = start;
        this.rotation = rotation;
    }

    public void markTaskAsDone() {
        rewardEffort();
        updateRotation();
        updateDueDate();
    }

    public void skipTask() {
        updateRotation();
        updateDueDate();
    }

    private void rewardEffort() {
        rotation.currentUser().incrementScore(effort.getValue());
    }

    private void updateDueDate() {
        start.update(frequency);
    }

    private void updateRotation() {
        rotation.update();
    }

    public Id deriveId() {
        return new Id(id);
    }

    public TaskInfo deriveInfo() {
        return new TaskInfo(id, title, frequency.name(), start.asString(),
                effort.getValue(), rotation.getRotationUserIds());
    }

    public TaskCreate deriveCreate() {
        return new TaskCreate(title, frequency.toString(), start.asString(), effort.getValue(), rotation.getRotationUserIds());
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

    public DueDate getStart() {
        return start;
    }

    public Rotation getRotation() {
        return rotation;
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
        private DueDate startDate;
        private Rotation rotation;

        private Builder() {
            // default private constructor
        }

        private Builder(Task task) {
            setId(task.getId());
            setTitle(task.getTitle());
            setEffort(task.getEffort());
            setFrequency(task.getFrequency());
            setStartDate(task.getStart());
            setRotation(task.getRotation());
        }

        private Builder(TaskCreate taskCreate) {
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
            return setStartDate(new DueDate(startDate));

        }

        public Builder setStartDate(DueDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder setRotation(Rotation rotation) {
            this.rotation = rotation;
            return this;
        }

        public Builder setRotation(Collection<Flatmate> rotation) {
            Rotation rota = new Rotation();
            rota.addAll(rotation);
            return setRotation(rota);
        }


        public Task build() {
            checkState(id >= 0, "Id is required");
            requireNonNull(title, "Title is required");
            requireNonNull(effort, "Effort is required");
            requireNonNull(frequency, "Frequency is required");
            requireNonNull(startDate, "Start date is required");
            return new Task(id, title, effort, frequency, startDate, rotation);
        }
    }
}
