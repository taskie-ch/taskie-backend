package com.taskie.core;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RotationTest {

    private final UserPrincipal john = new UserPrincipal("x", "John Doe");
    private final UserPrincipal jim = new UserPrincipal("y", "Jim Beam");
    private final UserPrincipal jack = new UserPrincipal("z", "Jack Black");

    @Test
    public void getCurrentUserOnRota() {


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

        Rotation rota = Rotation.newBuilder()
                .addRotation(DateTime.now().minusDays(1), john)
                .build();

        assertEquals("Current user", john, rota.getCurrent());
        assertEquals("Next user", UserPrincipal.NONE, rota.getNext());
    }

    @Test
    public void getCurrentUserDoesNotYieldResult() {

        Rotation rota = Rotation.newBuilder()
                .addRotation(DateTime.now().plusDays(1), john)
                .build();

        assertEquals("Current user", UserPrincipal.NONE, rota.getCurrent());
        assertEquals("Next user", john, rota.getNext());
    }
}