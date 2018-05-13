package com.taskie.health;

import com.taskie.api.TaskService;
import com.taskie.core.Task;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaskServiceHealthCheckTest {

    private TaskService taskService;
    private TaskServiceHealthCheck check;

    @Before
    public void setUp() {
        taskService = mock(TaskService.class);
        check = new TaskServiceHealthCheck(taskService);
    }

    @Test
    public void healthy() {
        when(taskService.findById(anyLong(), anyLong())).thenReturn(mock(Task.class));
        assertTrue("Service health", check.check().isHealthy());
    }

    @Test
    public void unhealthy() {
        when(taskService.findById(anyLong(), anyLong())).thenThrow(new IllegalStateException("Issue"));
        assertFalse("Service health", check.check().isHealthy());
    }
}