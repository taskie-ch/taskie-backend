package com.taskie.health;

import com.taskie.core.Task;
import com.taskie.db.TaskDao;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaskServiceHealthCheckTest {

    private TaskDao taskDao;
    private TaskServiceHealthCheck check;

    @Before
    public void setUp() {
        taskDao = mock(TaskDao.class);
        check = new TaskServiceHealthCheck(taskDao);
    }

    @Test
    public void healthy() {
        when(taskDao.findById(anyLong(), anyLong())).thenReturn(mock(Task.class));
        assertTrue("Service health", check.check().isHealthy());
    }

    @Test
    public void unhealthy() {
        when(taskDao.findById(anyLong(), anyLong())).thenThrow(new IllegalStateException("Issue"));
        assertFalse("Service health", check.check().isHealthy());
    }
}