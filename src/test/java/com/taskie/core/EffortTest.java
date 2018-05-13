package com.taskie.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EffortTest {

    @Test
    public void getEnumOfValue() {
        assertEquals("Enum for low", Effort.LOW, Effort.valueOf(1));
        assertEquals("Enum for high", Effort.HIGH, Effort.valueOf(2));
        assertEquals("Enum for huge", Effort.HUGE, Effort.valueOf(4));
    }

    @Test
    public void enumIntValue() {
        assertEquals("Value for low", 1, Effort.LOW.intValue());
        assertEquals("Value for high", 2, Effort.HIGH.intValue());
        assertEquals("Value for huge", 4, Effort.HUGE.intValue());
    }

    @Test(expected = IllegalStateException.class)
    public void noEnumForValue() {
        Effort.valueOf(100);
    }
}