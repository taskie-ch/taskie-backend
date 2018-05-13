package com.taskie.core;

import static com.google.common.base.Preconditions.checkState;

/**
 * Effort of a task
 */
public enum Effort {

    /**
     * Low effort
     */
    LOW(1),
    /**
     * High effort
     */
    HIGH(2),
    /**
     * Very high effort
     */
    HUGE(3);

    private final int value;

    Effort(int value) {
        this.value = value;
    }

    /**
     * @return numeric value
     */
    public int intValue() {
        return value;
    }

    /**
     * Parses the numeric value of an enum to it's enum representation
     *
     * @param value number value of the effort enum
     * @return effort enum
     */
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
