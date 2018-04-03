package com.taskie.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

public class TaskInfo {

    private final String text;
    private final boolean done;

    @JsonCreator
    public TaskInfo(@JsonProperty("text") String text,
                    @JsonProperty("done") boolean done) {
        this.text = text;
        this.done = done;
    }

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    @JsonProperty("done")
    public boolean isDone() {
        return done;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("text", text)
                .add("done", done)
                .toString();
    }
}
