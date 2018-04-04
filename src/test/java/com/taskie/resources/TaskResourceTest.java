package com.taskie.resources;

import com.taskie.api.Id;
import com.taskie.api.TaskInfo;
import com.taskie.core.Task;
import com.taskie.db.TaskDao;
import io.dropwizard.jersey.params.LongParam;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaskResourceTest {

    private final TaskDao taskDao = mock(TaskDao.class);
    private final TaskResource taskResource = new TaskResource(taskDao);

    private static final Task TASK = Task.create(1, "Some task", false);


    @Test
    public void getCollectionOfTasks() {


    }

    @Test
    public void getTask() {
        when(taskDao.findById(anyLong())).thenReturn(TASK);
        TaskInfo info = taskResource.getTask(new LongParam("1"));
        assertEquals("Task info from resource", TASK.deriveInfo(), info);
    }

    @Test
    public void createTask() {
        when(taskDao.save("Some task", false)).thenReturn(TASK);
        Id id = taskResource.createTask(TASK.deriveInfo());
        assertEquals("Created task id", TASK.deriveId(), id);
    }

    @Test
    public void deleteTask() {
        when(taskDao.delete(anyLong())).thenReturn(TASK);
        Id id = taskResource.deleteTask(new LongParam("1"));
        assertEquals("Deleted task id", TASK.deriveId(), id);
    }

    @Test
    public void completeTask() {
    }

    @Test
    public void uncompleteTask() {
    }
}