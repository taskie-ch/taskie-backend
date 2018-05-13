package com.taskie.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import javax.annotation.concurrent.Immutable;
import java.util.Objects;

/**
 * JSON API definition of an id.
 */
@Immutable
public final class Id {

    private final long id;

    @JsonCreator
    public Id(@JsonProperty("id") long id) {
        this.id = id;
    }

    @JsonProperty("id")
    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Id)) return false;
        Id id1 = (Id) o;
        return id == id1.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .toString();
    }
}
