package com.taskie.resources;

import com.codahale.metrics.annotation.Timed;
import com.taskie.api.Saying;
import com.taskie.core.Task;
import com.taskie.db.TaskDao;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Path("/task")
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource {

    private final TaskDao taskDao;

    public TaskResource(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @GET
    @Path("/{taskId}")
    @Timed
    public Task getTask(@PathParam("taskId") LongParam id) {
        return taskDao.findById(id);
    }

    @GET
    @Timed
    public Collection<Task> getTasks() {
        return taskDao.findAll();
    }
}
