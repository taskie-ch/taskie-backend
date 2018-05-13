package com.taskie.resources;

import com.taskie.api.FlatService;
import com.taskie.core.Flat;
import com.taskie.core.UserPrincipal;
import io.dropwizard.jersey.params.LongParam;
import org.junit.Test;

import javax.ws.rs.core.SecurityContext;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class LoginResourceTest {

    private final FlatService service = mock(FlatService.class);
    private final LoginResource resource = new LoginResource(service);

    @Test(expected = IllegalArgumentException.class)
    public void authenticationFails() {
        SecurityContext context = mock(SecurityContext.class);
        when(context.getUserPrincipal()).thenReturn(null);

        resource.authenticate(mock(LongParam.class), context);

        verify(context).getUserPrincipal();
    }

    @Test(expected = IllegalStateException.class)
    public void authenticatedUserNotFound() {
        UserPrincipal userPrincipal = new UserPrincipal("x", "User");
        SecurityContext context = mock(SecurityContext.class);
        when(context.getUserPrincipal()).thenReturn(userPrincipal);

        Flat flat = mock(Flat.class);
        when(flat.findUserByName(anyString())).thenReturn(Optional.empty());
        when(service.findById(anyLong())).thenReturn(flat);

        resource.authenticate(new LongParam("1"), context);
    }
}