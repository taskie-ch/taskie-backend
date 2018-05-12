package com.taskie.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskie.api.Id;
import com.taskie.api.TaskCreate;
import com.taskie.core.Task;
import com.taskie.db.TaskDao;
import com.taskie.util.TestData;
import io.dropwizard.jackson.Jackson;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Requests {@link TaskResource#createTask}
 */
public class CreateTaskTest extends AbstractRequestTest {

    private static final String PATH = "/flats/1/tasks";

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private static final TaskDao DAO = mock(TaskDao.class);
    private static final Task TASK = TestData.TASK;
    private static final TaskCreate TASK_CREATE = TestData.taskCreate();

    public CreateTaskTest() {
        super(resourceRule(new TaskResource(DAO)), PATH);
    }

    @Before
    public void setUp() {
        reset(DAO);
    }

    @Test
    public void requestCreateTask() throws IOException {
        when(DAO.save(1, TASK_CREATE)).thenReturn(TASK);
        final Response response = request().post(Entity.json(TASK_CREATE));
        final Id receivedId = MAPPER.readValue((ByteArrayInputStream) response.getEntity(), Id.class);
        assertThat(receivedId).isEqualTo(TASK.deriveId());
        verify(DAO).save(1, TASK_CREATE);
    }

    @Test
    public void requestCreateTaskFails() {
        when(DAO.save(1, TASK_CREATE)).thenThrow(IllegalArgumentException.class);
        final Response response = request().post(Entity.json(TASK_CREATE));
        assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_BAD_REQUEST);
    }
}
