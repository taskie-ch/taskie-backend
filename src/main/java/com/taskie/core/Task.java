package com.taskie.core;

import com.taskie.api.Id;
import com.taskie.api.TaskInfo;

public class Task {

    private final long id;
    private final String description;
    private final Rotation rotation;
    private final boolean done;

    public static Task complete(Task task) {
        return new Task(task, true);
    }

    public static Task uncomplete(Task task) {
        return new Task(task, false);
    }

    public static Task create(long id, String description, boolean done) {
        return new Task(id, description, null, done);
    }

    public static Task create(long id, String description, Rotation rotation) {
        return new Task(id, description, rotation, false);
    }

    public static Task create(Task task, Rotation rotation) {
        return new Task(task.getId(), task.getDescription(), rotation, task.isDone());
    }

    private Task(Task task, boolean done) {
        this.id = task.id;
        this.description = task.description;
        this.rotation = task.rotation;
        this.done = done;
    }

    private Task(long id, String description, Rotation rotation, boolean done) {
        this.id = id;
        this.description = description;
        this.rotation = rotation;
        this.done = done;
    }

    public Id deriveId() {
        return new Id(id);
    }

    public TaskInfo deriveInfo() {
        return new TaskInfo(description, done);
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Rotation getRotation() {
        return rotation;
    }

    public boolean isDone() {
        return done;
    }
}
