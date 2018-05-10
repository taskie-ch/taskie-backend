//package com.taskie.resources;
//
//import com.taskie.api.Id;
//import com.taskie.api.TaskCreate;
//import com.taskie.api.TaskInfo;
//import com.taskie.db.TaskDao;
//import io.dropwizard.jersey.params.LongParam;
//import org.junit.Test;
//
//import java.util.Collection;
//import java.util.Collections;
//
//import static com.taskie.util.TestData.TASK;
//import static org.junit.Assert.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.*;
//
//public class TaskResourceTest {
//
//    private final TaskDao taskDao = mock(TaskDao.class);
//    private final TaskResource taskResource = new TaskResource(taskDao);
//
//    private static final String TASK_ID = String.valueOf(TASK.getId());
//
//    @Test
//    public void getCollectionOfTasks() {
//        when(taskDao.findAll()).thenReturn(Collections.singleton(TASK));
//        Collection<TaskInfo> info = taskResource.getTasks();
//        assertEquals("Collection size", 1, info.size());
//        assertEquals("Task info from resource", TASK.deriveInfo(), info.iterator().next());
//    }
//
//    @Test
//    public void getTask() {
//        when(taskDao.findById(anyLong())).thenReturn(TASK);
//        TaskInfo info = taskResource.getTask(new LongParam(TASK_ID));
//        assertEquals("Task info from resource", TASK.deriveInfo(), info);
//    }
//
//    @Test
//    public void createTask() {
//        when(taskDao.save(any(TaskCreate.class))).thenReturn(TASK);
//        Id id = taskResource.createTask(TASK.deriveCreate());
//        assertEquals("Created task id", TASK.deriveId(), id);
//    }
//
//    @Test
//    public void deleteTask() {
//        when(taskDao.delete(anyLong())).thenReturn(TASK);
//        Id id = taskResource.deleteTask(new LongParam(TASK_ID));
//        assertEquals("Deleted task id", TASK.deriveId(), id);
//    }
//
//    @Test
//    public void completeTask() {
//        doCallRealMethod().when(taskDao).complete(TASK.getId());
//        when(taskDao.findById(TASK.getId())).thenReturn(TASK);
//        taskResource.completeTask(new LongParam(TASK_ID));
//        assertEquals("Completed task", true, TASK.deriveInfo().isDone());
//    }
//
//    @Test
//    public void uncompleteTask() {
//        doCallRealMethod().when(taskDao).uncomplete(TASK.getId());
//        when(taskDao.findById(TASK.getId())).thenReturn(TASK);
//        taskResource.uncompleteTask(new LongParam(TASK_ID));
//        assertEquals("Uncompleted task", false, TASK.deriveInfo().isDone());
//    }
//}