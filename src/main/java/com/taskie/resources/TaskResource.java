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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.taskie.resources.ResourcePath.*;

/**
 * Resource for task access and manipulation.
 */
@Path(FLATS)
@Api(value = "tasks")
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource {

    private final Logger LOG = LoggerFactory.getLogger(TaskResource.class);

    private final TaskDao taskDao;

    public TaskResource(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @GET
    @Timed
    @Path(TASKS)
    @ApiOperation(value = "Get all tasks")
    public Collection<TaskInfo> getTasks(@PathParam(FLAT_ID) LongParam flatId) {
        return taskDao.findAll(flatId.get()).stream()
                .map(Task::deriveInfo)
                .collect(Collectors.toList());
    }

    @POST
    @Timed
    @Path(TASKS)
    @ApiOperation(value = "Create a new task")
    public Id createTask(@PathParam(FLAT_ID) LongParam flatId, TaskCreate taskCreate) {
        return taskDao.save(flatId.get(), taskCreate).deriveId();
    }

    @GET
    @Timed
    @Path(TASK)
    @ApiOperation(value = "Get tasks by id")
    public TaskInfo getTask(@PathParam(FLAT_ID) LongParam flatId, @PathParam(TASK_ID) LongParam id) {
        return taskDao.findById(flatId.get(), id.get()).deriveInfo();
    }

    @POST
    @Timed
    @Path(TASK)
    @ApiOperation(value = "Update tasks for id")
    public void updateTask(@PathParam(FLAT_ID) LongParam flatId, TaskInfo taskInfo) {
        taskDao.update(flatId.get(), taskInfo);
    }

    @DELETE
    @Timed
    @Path(TASK)
    @ApiOperation(value = "Delete tasks by id")
    public Id deleteTask(@PathParam(FLAT_ID) LongParam flatId, @PathParam(TASK_ID) LongParam id) {
        return taskDao.delete(flatId.get(), id.get()).deriveId();
    }

    @POST
    @Timed
    @Path(TASK_COMPLETE)
    @ApiOperation(value = "Completes a task by id")
    public void completeTask(@PathParam(FLAT_ID) LongParam flatId, @PathParam(TASK_ID) LongParam id) {
        LOG.info("Complete task for flat:{}, task:{}", id.get(), id.get());
        taskDao.complete(flatId.get(), id.get());
    }

    @POST
    @Timed
    @Path(TASK_UNCOMPLETE)
    @ApiOperation(value = "Uncompletes a task by id")
    public void uncompleteTask(@PathParam(FLAT_ID) LongParam flatId, @PathParam(TASK_ID) LongParam id) {
        taskDao.uncomplete(flatId.get(), id.get());
    }

    @POST
    @Timed
    @Path(TASK_SKIP)
    @ApiOperation(value = "Skips a task by id")
    public void skipTask(@PathParam(FLAT_ID) LongParam flatId, @PathParam(TASK_ID) LongParam id) {
        taskDao.skip(flatId.get(), id.get());
    }
}
