package com.taskie.core;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class FlatTest {


    @Test
    public void findUserDoesNotExist() {
        Flat flat = Flat.create(1, "Test Flat");
        assertThat(flat.findUser("any").isPresent()).isFalse();
    }
}