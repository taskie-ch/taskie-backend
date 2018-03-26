package com.taskie.resources;

import com.codahale.metrics.annotation.Timed;
import com.taskie.core.Task;
import com.taskie.db.TaskDao;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

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
