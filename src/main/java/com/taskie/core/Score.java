package com.taskie.core;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class Score {

    @GuardedBy("this")
    private int points;

    public Score() {
        this(0);
    }

    public Score(int points) {
        this.points = points;
    }

    public synchronized int getPoints() {
        return points;
    }

    public synchronized void increment(int points) {
        this.points += points;
    }

    public synchronized void decrement(int points) {
        this.points -= points;
    }
}
