package com.taskie.resources;

import com.codahale.metrics.annotation.Timed;
import com.taskie.api.Id;
import com.taskie.api.TaskCreate;
import com.taskie.api.TaskInfo;
import com.taskie.core.Task;
import com.taskie.db.TaskDao;
import io.dropwizard.jersey.params.LongParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Resource for task access and manipulation.
 */
@Path("/tasks")
@Api(value = "tasks")
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource {

    private final TaskDao taskDao;

    public TaskResource(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @GET
    @Timed
    @ApiOperation(value = "Get all tasks")
    public Collection<TaskInfo> getTasks() {
        return taskDao.findAll().stream()
                .map(Task::deriveInfo)
                .collect(Collectors.toList());
    }

    @POST
    @Timed
    @ApiOperation(value = "Create a new task")
    public Id createTask(TaskCreate taskCreate) {
        return taskDao.save(taskCreate).deriveId();
    }

    @GET
    @Timed
    @Path("/{taskId}")
    @ApiOperation(value = "Get tasks by id")
    public TaskInfo getTask(@PathParam("taskId") LongParam id) {
        return taskDao.findById(id.get()).deriveInfo();
    }


    @DELETE
    @Timed
    @Path("/{taskId}")
    @ApiOperation(value = "Delete tasks by id")
    public Id deleteTask(@PathParam("taskId") LongParam id) {
        return taskDao.delete(id.get()).deriveId();
    }

    @POST
    @Timed
    @Path("/{taskId}/complete")
    @ApiOperation(value = "Completes a task by id")
    public void completeTask(@PathParam("taskId") LongParam id) {
        taskDao.complete(id.get());
    }

    @POST
    @Timed
    @Path("/{taskId}/uncomplete")
    @ApiOperation(value = "Uncompletes a task by id")
    public void uncompleteTask(@PathParam("taskId") LongParam id) {
        taskDao.uncomplete(id.get());
    }
}
