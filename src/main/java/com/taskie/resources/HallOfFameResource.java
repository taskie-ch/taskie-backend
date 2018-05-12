package com.taskie.resources;

import com.codahale.metrics.annotation.Timed;
import com.taskie.api.User;
import com.taskie.core.Flat;
import com.taskie.core.Flatmate;
import com.taskie.db.FlatDao;
import io.dropwizard.jersey.params.LongParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.taskie.resources.ResourcePath.*;

@Path(FLATS)
@Api(value = "hof")
@Produces(MediaType.APPLICATION_JSON)
public class HallOfFameResource {

    private FlatDao flatDao;

    public HallOfFameResource(FlatDao flatDao) {
        this.flatDao = flatDao;
    }

    @GET
    @Timed
    @Path(HALL_OF_FAME)
    @ApiOperation(value = "Get users and scores from the hall of fame")
    public Collection<User> getHallOfFame(@PathParam(FLAT_ID) LongParam flatId) {
        Flat flat = flatDao.findById(flatId.get());
        return flat.getUsers().stream()
                .map(Flatmate::deriveUser)
                .collect(Collectors.toList());
    }
}
