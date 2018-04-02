package com.taskie.core;

import com.google.common.base.MoreObjects;

import java.util.Objects;

public class Score {

    private final int completions;
    private final int blames;
    private final int overall;

    public static Score create() {
        return new Score(0, 0);
    }

    public static Score complete(Score score) {
        return new Score(score.getCompletions() + 1, score.getBlames());
    }

    public static Score blame(Score score) {
        return new Score(score.getCompletions(), score.getBlames() + 1);
    }

    private Score(int completions, int blames) {
        this.completions = completions;
        this.blames = blames;
        this.overall = completions - blames;
    }

    public int getCompletions() {
        return completions;
    }

    public int getBlames() {
        return blames;
    }

    public int getOverall() {
        return overall;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score = (Score) o;
        return completions == score.completions &&
                blames == score.blames &&
                overall == score.overall;
    }

    @Override
    public int hashCode() {
        return Objects.hash(completions, blames, overall);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("completions", completions)
                .add("blames", blames)
                .add("overall", overall)
                .toString();
    }
}
