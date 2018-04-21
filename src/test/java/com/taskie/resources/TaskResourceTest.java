package com.taskie.resources;

import com.taskie.api.Id;
import com.taskie.api.TaskInfo;
import com.taskie.core.Task;
import com.taskie.db.TaskDao;
import io.dropwizard.jersey.params.LongParam;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaskResourceTest {

    private final TaskDao taskDao = mock(TaskDao.class);
    private final TaskResource taskResource = new TaskResource(taskDao);

    private static final Task TASK = Task.create(1, "Some task", false);
    private static final String TASK_ID = String.valueOf(TASK.getId());

    @Test
    public void getCollectionOfTasks() {
        when(taskDao.findAll()).thenReturn(Collections.singleton(TASK));
        Collection<TaskInfo> info = taskResource.getTasks();
        assertEquals("Collection size", 1, info.size());
        assertEquals("Task info from resource", TASK.deriveInfo(), info.iterator().next());
    }

    @Test
    public void getTask() {
        when(taskDao.findById(anyLong())).thenReturn(TASK);
        TaskInfo info = taskResource.getTask(new LongParam(TASK_ID));
        assertEquals("Task info from resource", TASK.deriveInfo(), info);
    }

    @Test
    public void createTask() {
        when(taskDao.save("Some task")).thenReturn(TASK);
        Id id = taskResource.createTask(TASK.deriveCreate());
        assertEquals("Created task id", TASK.deriveId(), id);
    }

    @Test
    public void deleteTask() {
        when(taskDao.delete(anyLong())).thenReturn(TASK);
        Id id = taskResource.deleteTask(new LongParam(TASK_ID));
        assertEquals("Deleted task id", TASK.deriveId(), id);
    }

    @Test
    public void completeTask() {
        Task completeTask = Task.create(TASK, true);
        when(taskDao.complete(anyLong())).thenReturn(completeTask);
        TaskInfo info = taskResource.completeTask(new LongParam(TASK_ID));
        assertEquals("Completed task", completeTask.deriveInfo(), info);
    }

    @Test
    public void uncompleteTask() {
        when(taskDao.uncomplete(anyLong())).thenReturn(TASK);
        TaskInfo info = taskResource.uncompleteTask(new LongParam(TASK_ID));
        assertEquals("Uncompleted task", TASK.deriveInfo(), info);
    }
}