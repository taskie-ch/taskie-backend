package com.taskie.core;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RotationTest {

    @Test
    public void getCurrentUserOnRota() {

        UserPrincipal john = new UserPrincipal("John Doe");
        UserPrincipal jim = new UserPrincipal("Jim Beam");
        UserPrincipal jack = new UserPrincipal("Jack Black");

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

        UserPrincipal john = new UserPrincipal("John Doe");

        Rotation rota = Rotation.newBuilder()
                .addRotation(DateTime.now().minusDays(1), john)
                .build();

        assertEquals("Current user", john, rota.getCurrent());
        assertEquals("Next user", UserPrincipal.NONE, rota.getNext());
    }

    @Test
    public void getCurrentUserDoesNotYieldResult() {

        UserPrincipal john = new UserPrincipal("John Doe");

        Rotation rota = Rotation.newBuilder()
                .addRotation(DateTime.now().plusDays(1), john)
                .build();

        assertEquals("Current user", UserPrincipal.NONE, rota.getCurrent());
        assertEquals("Next user", john, rota.getNext());
    }
}