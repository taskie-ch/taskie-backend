package com.taskie.resources;

import com.taskie.api.Id;
import com.taskie.db.TaskDao;
import com.taskie.util.TestData;
import io.dropwizard.jersey.params.LongParam;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Requests {@link TaskResource#completeTask(LongParam)}
 */
public class CompleteTaskTest extends AbstractRequestTest {

    private static final Id ID = TestData.TASK.deriveId();
    private static final String PATH = "/tasks/" + ID.getId() + "/complete";
    private static final TaskDao DAO = mock(TaskDao.class);

    public CompleteTaskTest() {
        super(resourceRule(new TaskResource(DAO)), PATH);
    }

    @Test
    public void requestCompleteTask() {
        assertThat(request().post(Entity.json(ID)).getStatus()).isEqualTo(HttpServletResponse.SC_NO_CONTENT);
        verify(DAO).complete(ID.getId());
    }

    @Test
    public void requestCompleteTaskWithWrongMethod() {
        assertThat(request().get().getStatus()).isEqualTo(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        verify(DAO).complete(ID.getId());
    }
}
