package com.taskie.core;

import com.google.common.base.MoreObjects;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;
import java.util.Objects;

/**
 * Score of a user used for the hall of fame.
 */
@ThreadSafe
public class Score {

    @GuardedBy("this")
    private int points;

    /**
     * Creates a score.
     *
     * @param points initial points
     */
    public Score(int points) {
        this.points = points;
    }

    /**
     * Get the score int representation.
     *
     * @return numeric score value
     */
    synchronized int intValue() {
        return points;
    }

    /**
     * Increments the score value.
     *
     * @param points value to add
     */
    synchronized void increment(int points) {
        this.points += points;
    }

    /**
     * Decrements the score value.
     *
     * @param points value to subtract
     */
    synchronized void decrement(int points) {
        this.points -= points;
    }

    @Override
    public synchronized final boolean equals(Object o) {
        if (!(o instanceof Score)) return false;
        Score score = (Score) o;
        return points == score.points;
    }

    @Override
    public synchronized final int hashCode() {
        return Objects.hash(points);
    }

    @Override
    public synchronized String toString() {
        return MoreObjects.toStringHelper(this)
                .add("points", points)
                .toString();
    }
}
