package com.taskie.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Id {

    private final long id;

    @JsonCreator
    public Id(@JsonProperty("id") long id) {
        this.id = id;
    }

    @JsonProperty("id")
    public long getId() {
        return id;
    }
}
