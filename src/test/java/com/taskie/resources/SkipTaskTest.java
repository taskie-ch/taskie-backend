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
 * Requests {@link TaskResource#skipTask}
 */
public class SkipTaskTest extends AbstractRequestTest {

    private static final UserId ID = TestData.userId();
    private static final String PATH = "flats/1/tasks/1/uncomplete";
    private static final InMemoryTaskDao DAO = mock(InMemoryTaskDao.class);

    public SkipTaskTest() {
        super(resourceRule(new TaskResource(DAO)), PATH);
    }

    @Test
    public void requestSkipTask() {
        assertThat(request().post(Entity.json(ID)).getStatus()).isEqualTo(HttpServletResponse.SC_NO_CONTENT);
        verify(DAO).skip(1, 1, ID.getUserId());
    }

    @Test
    public void requestSkipTaskWithWrongMethod() {
        assertThat(request().get().getStatus()).isEqualTo(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        verify(DAO).skip(1, 1, ID.getUserId());
    }
}
