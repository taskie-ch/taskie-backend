package com.taskie.resources;

import com.taskie.api.TaskInfo;
import com.taskie.core.Task;
import com.taskie.db.TaskDao;
import com.taskie.util.TestData;
import io.dropwizard.jersey.params.LongParam;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Requests {@link TaskResource#getTask(LongParam)}
 */
public class GetTaskTest extends AbstractRequestTest {

    private static final long ID = 1;
    private static final String PATH = "/tasks/" + ID;
    private static final TaskDao DAO = mock(TaskDao.class);
    private static final TaskInfo TASK_INFO = TestData.TASK_INFO;

    public GetTaskTest() {
        super(resourceRule(new TaskResource(DAO)), PATH);
    }

    @Before
    public void setUp() {
        final Task task = mock(Task.class);
        when(task.deriveInfo()).thenReturn(TASK_INFO);
        when(DAO.findById(ID)).thenReturn(task);
    }

    @Test
    public void requestTask() {
        assertThat(request().get(TaskInfo.class)).isEqualTo(TASK_INFO);
        verify(DAO).findById(ID);
    }
}
