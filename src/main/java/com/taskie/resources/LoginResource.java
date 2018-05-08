package com.taskie.resources;


import com.codahale.metrics.annotation.Timed;
import com.taskie.api.User;
import com.taskie.db.UserDao;
import io.dropwizard.jersey.params.LongParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.security.PermitAll;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

import static java.util.Objects.requireNonNull;

@Path("/flats")
@Api(value = "auth")
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {

    private final Logger LOG = LoggerFactory.getLogger(LoginResource.class);

    private final UserDao userDao;

    public LoginResource(@Nonnull UserDao userDao) {
        this.userDao = requireNonNull(userDao, "UserDao is required");
    }

    @POST
    @Timed
    @PermitAll
    @Path("/{flatId}/auth")
    @ApiOperation(value = "Authenticate with the service")
    public User authenticate(@PathParam("flatId") LongParam flatId, @Context SecurityContext context) {
        final String name = requireAuthenticated(context.getUserPrincipal()).getName();
        LOG.info("{} requested login for flat {}", name, flatId);
        return userDao.findByName(name).deriveUser();
    }

    private static Principal requireAuthenticated(Principal principal) {
        if (principal == null) {
            // TODO replace with suitable exception
            throw new IllegalArgumentException("User not permitted");
        }
        return principal;
    }
}
