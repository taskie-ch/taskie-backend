package com.taskie.resources;

import com.taskie.db.UserDao;
import io.dropwizard.jersey.params.LongParam;
import org.junit.Test;

import javax.ws.rs.core.SecurityContext;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginResourceTest {

    private final LoginResource resource = new LoginResource(mock(UserDao.class));

    @Test(expected = IllegalArgumentException.class)
    public void authenticateFailsWithException() {
        SecurityContext context = mock(SecurityContext.class);
        when(context.getUserPrincipal()).thenReturn(null);
        resource.authenticate(mock(LongParam.class), context);
    }
}