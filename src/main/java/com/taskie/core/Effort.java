package com.taskie.core;

import static com.google.common.base.Preconditions.checkState;

public enum Effort {

    LOW(1),
    MID(2),
    HIGH(3);

    private final int value;

    Effort(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Effort valueOf(int value) {
        Effort effort = null;
        for (Effort e : Effort.values()) {
            if (e.value == value) {
                effort = e;
            }
        }
        checkState(effort != null, "No effort matching value " + value);
        return effort;
    }
}
