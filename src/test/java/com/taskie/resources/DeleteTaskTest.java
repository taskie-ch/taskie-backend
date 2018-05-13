package com.taskie.resources;

import com.taskie.db.InMemoryTaskDao;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Requests {@link TaskResource#deleteTask}
 */
public class DeleteTaskTest extends AbstractRequestTest {

    private static final String PATH = "/flats/1/tasks/1";

    private static final InMemoryTaskDao DAO = mock(InMemoryTaskDao.class);

    public DeleteTaskTest() {
        super(resourceRule(new TaskResource(DAO)), PATH);
    }

    @Before
    public void setUp() {
        reset(DAO);
    }

    @Test
    public void requestUpdateTask() {
        final Response response = request().delete();
        assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_NO_CONTENT);
        verify(DAO).delete(1, 1);
    }

    @Test
    public void requestUpdateTaskFails() {
        doThrow(IllegalArgumentException.class).when(DAO).delete(1, 1);
        final Response response = request().delete();
        assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_BAD_REQUEST);
    }
}
