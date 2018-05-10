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
 * Requests {@link TaskResource#completeTask}
 */
public class CompleteTaskTest extends AbstractRequestTest {

    private static final Id ID = TestData.TASK.deriveId();
    private static final String PATH = ResourcePaths.withBaseAndIds(ResourcePath.TASK_COMPLETE, 1, ID.getId());
    private static final TaskDao DAO = mock(TaskDao.class);

    public CompleteTaskTest() {
        super(resourceRule(new TaskResource(DAO)), PATH);
    }

    @Test
    public void requestCompleteTask() {
        assertThat(request().post(Entity.json(ID.getId())).getStatus())
                .isEqualTo(HttpServletResponse.SC_NO_CONTENT);
        verify(DAO).complete(ID.getId());
    }

    @Test
    public void requestCompleteTaskWithWrongMethod() {
        assertThat(request().get().getStatus()).isEqualTo(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        verify(DAO).complete(ID.getId());
    }
}
