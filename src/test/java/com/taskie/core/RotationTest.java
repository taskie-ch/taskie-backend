package com.taskie.core;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RotationTest {

    @Test
    public void getCurrentUserOnRota() {

        User john = new User("John Doe");
        User jim = new User("Jim Beam");
        User jack = new User("Jack Black");

        Rotation rota = Rotation.newBuilder()
                .addRotation(DateTime.now().minusDays(1), john)
                .addRotation(DateTime.now().plusDays(1), jim)
                .addRotation(DateTime.now().plusDays(2), jack)
                .build();

        assertEquals("Current user", john, rota.getCurrent());
        assertEquals("Next user", jim, rota.getNext());
    }

    @Test
    public void getNextUserDoesNotYieldResult() {

        User john = new User("John Doe");

        Rotation rota = Rotation.newBuilder()
                .addRotation(DateTime.now().minusDays(1), john)
                .build();

        assertEquals("Current user", john, rota.getCurrent());
        assertEquals("Next user", User.NONE, rota.getNext());
    }

    @Test
    public void getCurrentUserDoesNotYieldResult() {

        User john = new User("John Doe");

        Rotation rota = Rotation.newBuilder()
                .addRotation(DateTime.now().plusDays(1), john)
                .build();

        assertEquals("Current user", User.NONE, rota.getCurrent());
        assertEquals("Next user", john, rota.getNext());
    }
}