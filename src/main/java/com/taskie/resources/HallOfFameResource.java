package com.taskie.resources;

import com.codahale.metrics.annotation.Timed;
import com.taskie.api.FlatService;
import com.taskie.api.User;
import com.taskie.core.Flat;
import com.taskie.core.Flatmate;
import io.dropwizard.jersey.params.LongParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Nonnull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.taskie.resources.ResourcePath.*;
import static java.util.Objects.requireNonNull;

/**
 * Endpoint handling user data for the hall of fame.
 */
@Path(FLATS)
@Api(value = "hof")
@Produces(MediaType.APPLICATION_JSON)
public class HallOfFameResource {

    /**
     * Provides flat and user data.
     */
    private FlatService flatService;

    /**
     * Creates an endpoint for the hall of fame.
     *
     * @param flatService flat service
     */
    public HallOfFameResource(@Nonnull FlatService flatService) {
        this.flatService = requireNonNull(flatService, "FlatService is required");
    }

    /**
     * Provides all users of a flat with their scores.
     *
     * @param flatId id of the flat
     * @return users and scores of a flat
     */
    @GET
    @Timed
    @Path(HALL_OF_FAME)
    @ApiOperation(value = "Get users and scores from the hall of fame")
    public Collection<User> getHallOfFame(@PathParam(FLAT_ID) LongParam flatId) {
        Flat flat = flatService.findById(flatId.get());
        return flat.getUsers().stream()
                .map(Flatmate::deriveUser)
                .collect(Collectors.toList());
    }
}
