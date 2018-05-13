package com.taskie.resources;


import com.codahale.metrics.annotation.Timed;
import com.taskie.api.FlatService;
import com.taskie.api.User;
import com.taskie.core.Flatmate;
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

import static com.taskie.resources.ResourcePath.*;
import static java.util.Objects.requireNonNull;

/**
 * Endpoint handling login requests.
 */
@Path(FLATS)
@Api(value = "auth")
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {

    private static final Logger LOG = LoggerFactory.getLogger(LoginResource.class);

    /**
     * Provides flat and user data.
     */
    private final FlatService flatService;

    /**
     * Creates an endpoint for login requests.
     *
     * @param flatService flat service
     */
    public LoginResource(@Nonnull FlatService flatService) {
        this.flatService = requireNonNull(flatService, "FlatService is required");
    }

    /**
     * Authenticates a requesting user to access the application.
     *
     * @param flatId  id of the flat
     * @param context security context provided by the authentication module
     * @return user payload
     * @throws IllegalStateException    if the user for the given name was not found in the system
     * @throws IllegalArgumentException if the requesting user is not permitted
     */
    @POST
    @Timed
    @PermitAll
    @Path(LOGIN)
    @ApiOperation(value = "Authenticate with the service")
    public User authenticate(@PathParam(FLAT_ID) LongParam flatId, @Context SecurityContext context) {
        final String name = requireAuthenticated(context.getUserPrincipal()).getName();
        LOG.info("{} requested login for flat {}", name, flatId);
        return flatService.findById(flatId.get()).findUserByName(name).map(Flatmate::deriveUser).
                <IllegalStateException>orElseThrow(() -> {
                    throw new IllegalStateException("Could not find user for name " + name);
                });
    }

    private static Principal requireAuthenticated(Principal principal) {
        if (principal == null) {
            throw new IllegalArgumentException("User not permitted");
        }
        return principal;
    }
}
