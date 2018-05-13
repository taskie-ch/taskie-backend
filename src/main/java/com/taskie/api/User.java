package com.taskie.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import javax.annotation.concurrent.Immutable;
import java.util.Objects;

/**
 * JSON API definition of a user.
 */
@Immutable
public class User {

    private final String id;
    private final String name;
    private final int score;

    @JsonCreator
    public User(@JsonProperty("id") String id,
                @JsonProperty("nickname") String name,
                @JsonProperty("score") int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("nickname")
    public String getName() {
        return name;
    }

    @JsonProperty("score")
    public int getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return score == user.score &&
                Objects.equals(id, user.id) &&
                Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, score);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("score", score)
                .toString();
    }
}
