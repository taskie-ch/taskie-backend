package com.taskie.resources;

import com.taskie.api.UserId;
import com.taskie.db.InMemoryTaskDao;
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

    private static final UserId ID = TestData.userId();
    private static final String PATH = ResourcePaths.withBaseAndIds(ResourcePath.TASK_COMPLETE, 1, 1);
    private static final InMemoryTaskDao DAO = mock(InMemoryTaskDao.class);

    public CompleteTaskTest() {
        super(resourceRule(new TaskResource(DAO)), PATH);
    }

    @Test
    public void requestCompleteTask() {
        assertThat(request().post(Entity.json(ID)).getStatus())
                .isEqualTo(HttpServletResponse.SC_NO_CONTENT);
        verify(DAO).complete(1, 1, ID.getUserId());
    }

    @Test
    public void requestCompleteTaskWithWrongMethod() {
        assertThat(request().get().getStatus()).isEqualTo(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        verify(DAO).complete(1, 1, ID.getUserId());
    }
}
