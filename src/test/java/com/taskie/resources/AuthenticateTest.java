package com.taskie.resources;

import com.taskie.core.Flat;
import com.taskie.core.Flatmate;
import com.taskie.db.InMemoryFlatDao;
import com.taskie.util.TestData;
import org.glassfish.jersey.internal.util.Base64;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Requests {@link LoginResource#authenticate}
 */
public class AuthenticateTest extends AbstractRequestTest {

    private static final long FLAT_ID = 1;
    private static final String PATH = ResourcePaths.withBaseAndFlatId(ResourcePath.LOGIN, FLAT_ID);

    private static final Flat FLAT = mock(Flat.class);
    private static final InMemoryFlatDao DAO = mock(InMemoryFlatDao.class);
    private static final Flatmate FLATMATE = TestData.flatmate();

    public AuthenticateTest() {
        super(resourceRule(new LoginResource(DAO), DAO), PATH);
    }

    @Test
    public void requestAuthenticationUnauthorized() {
        assertThat(request().post(Entity.json(FLAT_ID)).getStatus()).isEqualTo(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Test
    public void requestAuthentication() {

        when(DAO.findById(1)).thenReturn(FLAT);
        when(FLAT.findUserByName(FLATMATE.getName())).thenReturn(Optional.of(FLATMATE));
        final Response response = request()
                .header("Authorization", "Basic " + Base64.encodeAsString(FLATMATE.getName() + ":secret"))
                .post(Entity.json(FLAT_ID));

        assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_OK);
    }
}
