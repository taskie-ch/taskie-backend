package com.taskie.resources;

import com.codahale.metrics.annotation.Timed;
import com.taskie.core.HallOfFame;
import io.dropwizard.jersey.params.LongParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hof")
@Api(value = "hof")
@Produces(MediaType.APPLICATION_JSON)
public class HallOfFameResource {

    @GET
    @Timed
    @Path("/{flatId}")
    @ApiOperation(value = "Get HallOfFame for a flat")
    public HallOfFame getHallOfFame(@PathParam("flatId") LongParam id) {
        return null;
    }
}
