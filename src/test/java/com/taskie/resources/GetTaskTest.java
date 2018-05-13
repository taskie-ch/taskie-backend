package com.taskie.resources;

import com.taskie.api.TaskInfo;
import com.taskie.core.Task;
import com.taskie.db.InMemoryTaskDao;
import com.taskie.util.TestData;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Requests {@link TaskResource#getTask}
 */
public class GetTaskTest extends AbstractRequestTest {

    private static final long ID = 1;
    private static final String PATH = "flats/1/tasks/" + ID;
    private static final InMemoryTaskDao DAO = mock(InMemoryTaskDao.class);
    private static final TaskInfo TASK_INFO = TestData.TASK_INFO;

    public GetTaskTest() {
        super(resourceRule(new TaskResource(DAO)), PATH);
    }

    @Before
    public void setUp() {
        final Task task = mock(Task.class);
        when(task.deriveInfo()).thenReturn(TASK_INFO);
        when(DAO.findById(ID, ID)).thenReturn(task);
    }

    @Test
    public void requestTask() {
        assertThat(request().get(TaskInfo.class)).isEqualTo(TASK_INFO);
        verify(DAO).findById(ID, ID);
    }
}
