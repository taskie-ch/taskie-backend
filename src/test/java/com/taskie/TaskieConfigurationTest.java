package com.taskie;

import ca.grimoire.dropwizard.cors.config.CrossOriginFilterFactory;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;


public class TaskieConfigurationTest {

    @Test
    public void corsConfigIsInitialised() {
        assertThat(new TaskieConfiguration().getCors()).isNotNull();
    }

    @Test
    public void corsConfigIsMutable() {
        TaskieConfiguration config = new TaskieConfiguration();
        CrossOriginFilterFactory factory = mock(CrossOriginFilterFactory.class);
        config.setCors(factory);
        assertThat(config.getCors()).isEqualTo(factory);
    }

}