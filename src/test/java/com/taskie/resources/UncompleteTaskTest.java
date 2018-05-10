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
 * Requests {@link TaskResource#uncompleteTask}
 */
public class UncompleteTaskTest extends AbstractRequestTest {

    private static final Id ID = TestData.TASK.deriveId();
    private static final String PATH = "flats/1/tasks/" + ID.getId() + "/uncomplete";
    private static final TaskDao DAO = mock(TaskDao.class);

    public UncompleteTaskTest() {
        super(resourceRule(new TaskResource(DAO)), PATH);
    }

    @Test
    public void requestUncompleteTask() {
        assertThat(request().post(Entity.json(ID)).getStatus()).isEqualTo(HttpServletResponse.SC_NO_CONTENT);
        verify(DAO).uncomplete(ID.getId());
    }

    @Test
    public void requestUncompleteTaskWithWrongMethod() {
        assertThat(request().get().getStatus()).isEqualTo(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        verify(DAO).uncomplete(ID.getId());
    }
}
