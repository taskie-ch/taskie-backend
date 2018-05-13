package com.taskie.core;

import com.taskie.api.Id;
import com.taskie.api.TaskCreate;
import com.taskie.api.TaskInfo;
import org.joda.time.DateTime;

import java.util.Collection;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.requireNonNull;

/**
 * Task representation.
 */
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

    /**
     * Updates the user rotation and due date and rewards the user
     * that has marked the task as done with the value of the effort.
     *
     * @param userId id of the user that has completed the task
     */
    public void markTaskAsDone(String userId) {
        rewardEffort(userId);
        updateRotation();
        updateDueDate();
    }

    /**
     * Updates the user rotation and due date and fines the user
     * that has skipped the task with the value of the effort.
     *
     * @param userId id of the user that has skipped the task
     */
    public void skipTask(String userId) {
        fineEffort(userId);
        updateRotation();
        updateDueDate();
    }

    private void rewardEffort(String userId) {
        rotation.applyToUser(userId, user -> user.incrementScore(effort.intValue()));
    }

    private void fineEffort(String userId) {
        rotation.applyToUser(userId, user -> user.decrementScore(effort.intValue()));
    }

    private void updateDueDate() {
        start.update(frequency);
    }

    private void updateRotation() {
        rotation.update();
    }


    /**
     * Derives the JSON API representation of the task id.
     *
     * @return JSON API id representation
     */
    public Id deriveId() {
        return new Id(id);
    }

    /**
     * Derives the JSON API representation of the task.
     *
     * @return JSON API task representation
     */
    public TaskInfo deriveInfo() {
        return new TaskInfo(id, title, frequency.name(), start.asString(),
                effort.intValue(), rotation.getRotationUserIds());
    }

    /**
     * @return task id
     */
    public long getId() {
        return id;
    }

    /**
     * @return task title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return task effort
     */
    public Effort getEffort() {
        return effort;
    }

    /**
     * @return task frequency
     */
    public Frequency getFrequency() {
        return frequency;
    }

    /**
     * @return task start date
     */
    public DueDate getStart() {
        return start;
    }

    /**
     * @return task user rotation
     */
    public Rotation getRotation() {
        return rotation;
    }

    /**
     * Creates a new task builder.
     *
     * @return task builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Creates a new task builder with data.
     *
     * @param taskCreate new task data
     * @return task builder
     */
    public static Builder newBuilder(TaskCreate taskCreate) {
        return new Builder(taskCreate);
    }

    /**
     * Static builder for task instances.
     */
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

        private Builder setStartDate(DueDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder setRotation(Collection<Flatmate> rotation) {
            Rotation rota = new Rotation();
            rota.addAll(rotation);
            return setRotation(rota);
        }

        private Builder setRotation(Rotation rotation) {
            this.rotation = rotation;
            return this;
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
