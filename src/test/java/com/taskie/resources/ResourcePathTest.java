package com.taskie.resources;

import org.junit.Test;

import java.lang.reflect.Constructor;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourcePathTest {

    /**
     * Workaround for issues with test coverage reporting.
     */
    @Test
    public void invoke() throws Exception {
        Constructor<ResourcePath> constructor = ResourcePath.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        assertThat(constructor.newInstance()).isNotNull();
    }

    @Test
    public void verifyResourcePaths() {
        assertThat(ResourcePath.FLATS).isEqualTo("/flats");
        assertThat(ResourcePath.HALL_OF_FAME).isEqualTo("/{flatId}/hof");
        assertThat(ResourcePath.LOGIN).isEqualTo("/{flatId}/auth");
        assertThat(ResourcePath.TASKS).isEqualTo("/{flatId}/tasks");
        assertThat(ResourcePath.TASK).isEqualTo("/{flatId}/tasks/{taskId}");
        assertThat(ResourcePath.TASK_COMPLETE).isEqualTo("/{flatId}/tasks/{taskId}/complete");
        assertThat(ResourcePath.TASK_UNCOMPLETE).isEqualTo("/{flatId}/tasks/{taskId}/uncomplete");
    }
}