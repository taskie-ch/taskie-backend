package com.taskie.core;

import com.google.common.base.MoreObjects;

import java.util.Map;
import java.util.Objects;

public class HallOfFame {

    private final Map<UserPrincipal, Score> scores;

    public HallOfFame(Map<UserPrincipal, Score> scores) {
        this.scores = scores;
    }

    public Map<UserPrincipal, Score> getScores() {
        return scores;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HallOfFame that = (HallOfFame) o;
        return Objects.equals(scores, that.scores);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scores);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("scores", scores)
                .toString();
    }
}
