package com.taskie.core;

import org.joda.time.DateTime;

public class TaskOccurence {

    private final DateTime date;
    private final User assignee;
    private boolean done = false;

    public TaskOccurence(DateTime date, User assignee) {
        this.date = date;
        this.assignee = assignee;
    }

    public void complete() {
        done = true;
    }

    public void uncomplete() {
        done = false;
    }

    public DateTime getDate() {
        return date;
    }

    public boolean isDone() {
        return done;
    }

    public boolean isPending() {
        return !done;
    }

    public User getAssignee() {
        return assignee;
    }
}
