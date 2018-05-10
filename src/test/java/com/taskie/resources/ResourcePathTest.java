package com.taskie.resources;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourcePathTest {

    @Test
    public void verifyResourcePaths() {
        assertThat(ResourcePath.FLATS).isEqualTo("/flats");
        assertThat(ResourcePath.LOGIN).isEqualTo("/{flatId}/auth");
        assertThat(ResourcePath.TASKS).isEqualTo("/{flatId}/tasks");
        assertThat(ResourcePath.TASK).isEqualTo("/{flatId}/tasks/{taskId}");
        assertThat(ResourcePath.TASK_COMPLETE).isEqualTo("/{flatId}/tasks/{taskId}/complete");
        assertThat(ResourcePath.TASK_UNCOMPLETE).isEqualTo("/{flatId}/tasks/{taskId}/uncomplete");
    }
}