package com.taskie.resources;

import com.taskie.core.UserPrincipal;
import com.taskie.db.UserDao;
import com.taskie.util.TestData;
import org.glassfish.jersey.internal.util.Base64;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Requests {@link LoginResource#authenticate}
 */
public class AuthenticateTest extends AbstractRequestTest {

    private static final long FLAT_ID = 1;
    private static final String PATH = ResourcePaths.withBaseAndFlatId(ResourcePath.LOGIN, FLAT_ID);

    private static final UserDao DAO = mock(UserDao.class);
    private static final UserPrincipal USER = TestData.userPrincipal();

    public AuthenticateTest() {
        super(resourceRule(new LoginResource(DAO)), PATH);
    }

    @Test
    public void requestAuthenticationUnauthorized() {
        assertThat(request().post(Entity.json(FLAT_ID)).getStatus()).isEqualTo(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Test
    public void requestAuthentication() {
        when(DAO.findByName(USER.getName())).thenReturn(USER);
        final Response response = request()
                .header("Authorization", "Basic " + Base64.encodeAsString(USER.getName() + ":secret"))
                .post(Entity.json(FLAT_ID));

        assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_OK);
    }
}
