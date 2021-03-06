package com.taskie.resources;

import com.taskie.api.TaskInfo;
import com.taskie.core.Task;
import com.taskie.db.InMemoryTaskDao;
import com.taskie.util.TestData;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

import static com.taskie.util.GenericTypes.taskInfoList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Requests {@link TaskResource#getTasks}
 */
public class GetAllTasksTest extends AbstractRequestTest {

    private static final String PATH = "/flats/1/tasks";
    private static final InMemoryTaskDao DAO = mock(InMemoryTaskDao.class);
    private static final TaskInfo TASK_INFO = TestData.TASK_INFO;

    public GetAllTasksTest() {
        super(resourceRule(new TaskResource(DAO)), PATH);
    }

    @Before
    public void setUp() {
        reset(DAO);
    }

    @Test
    public void requestAllTask() {
        final Task task = mock(Task.class);
        when(task.deriveInfo()).thenReturn(TASK_INFO);
        when(DAO.findAll(1)).thenReturn(Collections.singletonList(task));
        assertThat(request().get(taskInfoList())).containsExactly(TASK_INFO);
        verify(DAO).findAll(1);
    }

    @Test
    public void requestAllTaskThrowsException() {
        when(DAO.findAll(1)).thenThrow(IllegalArgumentException.class);
        assertThat(request().get().getStatus()).isEqualTo(HttpServletResponse.SC_BAD_REQUEST);
        verify(DAO).findAll(1);
    }
}
