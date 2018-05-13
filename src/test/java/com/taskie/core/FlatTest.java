package com.taskie.core;

import com.taskie.AbstractEntityTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class FlatTest extends AbstractEntityTest<Flat> {

    public FlatTest() {
        super(Flat.create(1, "Test Flat"));
    }

    @Test
    public void findUserDoesNotExist() {
        assertThat(testEntity.findUser("any").isPresent()).isFalse();
    }
}