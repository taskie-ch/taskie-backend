package com.taskie.resources;

import com.taskie.api.Id;
import com.taskie.db.TaskDao;
import com.taskie.util.TestData;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Requests {@link TaskResource#skipTask}
 */
public class SkipTaskTest extends AbstractRequestTest {

    private static final Id ID = TestData.TASK.deriveId();
    private static final String PATH = "flats/1/tasks/" + ID.getId() + "/skip";
    private static final TaskDao DAO = mock(TaskDao.class);

    public SkipTaskTest() {
        super(resourceRule(new TaskResource(DAO)), PATH);
    }

    @Test
    public void requestSkipTask() {
        assertThat(request().post(Entity.json(ID)).getStatus()).isEqualTo(HttpServletResponse.SC_NO_CONTENT);
        verify(DAO).skip(1, ID.getId());
    }

    @Test
    public void requestSkipTaskWithWrongMethod() {
        assertThat(request().get().getStatus()).isEqualTo(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        verify(DAO).skip(1, ID.getId());
    }
}
