package com.taskie.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EffortTest {

    @Test
    public void getEnumOfValue() {
        assertEquals("Enum for low", Effort.LOW, Effort.valueOf(1));
        assertEquals("Enum for mid", Effort.MID, Effort.valueOf(2));
        assertEquals("Enum for high", Effort.HIGH, Effort.valueOf(3));
    }

    @Test(expected = IllegalStateException.class)
    public void noEnumForValue() {
        Effort.valueOf(100);
    }
}