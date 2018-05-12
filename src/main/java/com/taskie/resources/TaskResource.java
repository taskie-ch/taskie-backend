package com.taskie.resources;

import com.codahale.metrics.annotation.Timed;
import com.taskie.api.*;
import com.taskie.core.Task;
import io.dropwizard.jersey.params.LongParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.taskie.resources.ResourcePath.*;
import static java.util.Objects.requireNonNull;

/**
 * Endpoint handling task access and manipulation.
 */
@Path(FLATS)
@Api(value = "tasks")
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource {

    private static final Logger LOG = LoggerFactory.getLogger(TaskResource.class);

    /**
     * Provides task data.
     */
    private final TaskService taskService;

    /**
     * Creates an endpoint for task manipulation.
     *
     * @param taskService task service
     */
    public TaskResource(@Nonnull TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Gets all open tasks for a flat.
     *
     * @param flatId id of the flat
     * @return collection of all tasks for a flat
     */
    @GET
    @Timed
    @Path(TASKS)
    @ApiOperation(value = "Get all tasks")
    public Collection<TaskInfo> getTasks(@PathParam(FLAT_ID) LongParam flatId) {
        LOG.info("Get all tasks for flat:{}", flatId.get());
        return taskService.findAll(flatId.get()).stream()
                .map(Task::deriveInfo)
                .collect(Collectors.toList());
    }

    /**
     * Creates a new task for a flat.
     *
     * @param flatId     id of the flat
     * @param taskCreate payload for creating a task
     * @return id of the newly created task
     */
    @POST
    @Timed
    @Path(TASKS)
    @ApiOperation(value = "Create a new task")
    public Id createTask(@PathParam(FLAT_ID) LongParam flatId, TaskCreate taskCreate) {
        requireNonNull(taskCreate, "TaskCreate is required");
        LOG.info("Create new task for flat:{}, title:{}", flatId.get(), taskCreate.getTitle());
        return taskService.save(flatId.get(), taskCreate).deriveId();
    }

    /**
     * Gets an existing task for a flat.
     *
     * @param flatId id of the flat
     * @param taskId id of the task
     * @return payload of the task
     */
    @GET
    @Timed
    @Path(TASK)
    @ApiOperation(value = "Get tasks by id")
    public TaskInfo getTask(@PathParam(FLAT_ID) LongParam flatId, @PathParam(TASK_ID) LongParam taskId) {
        LOG.info("Get task for flat:{}, task:{}", flatId.get(), taskId.get());
        return taskService.findById(flatId.get(), taskId.get()).deriveInfo();
    }

    /**
     * Updates an existing task for a flat.
     *
     * @param flatId   id of the flat
     * @param taskInfo payload of the task
     */
    @POST
    @Timed
    @Path(TASK)
    @ApiOperation(value = "Update tasks for id")
    public void updateTask(@PathParam(FLAT_ID) LongParam flatId, TaskInfo taskInfo) {
        requireNonNull(taskInfo, "TaskInfo is required");
        LOG.info("Update task for flat:{}, task:{}", flatId.get(), taskInfo.getId());
        taskService.update(flatId.get(), taskInfo);
    }

    /**
     * Deletes an existing task for a flat.
     *
     * @param flatId id of the flat
     * @param taskId if of the task
     */
    @DELETE
    @Timed
    @Path(TASK)
    @ApiOperation(value = "Delete tasks by id")
    public void deleteTask(@PathParam(FLAT_ID) LongParam flatId, @PathParam(TASK_ID) LongParam taskId) {
        LOG.info("Delete task for flat:{}, task{}", flatId.get(), taskId.get());
        taskService.delete(flatId.get(), taskId.get());
    }

    /**
     * Marks an existing task as done by a user in a flat.
     *
     * @param flatId id of the flat
     * @param taskId id of the task
     * @param userId id of the user that completed the task
     */
    @POST
    @Timed
    @Path(TASK_COMPLETE)
    @ApiOperation(value = "Completes a task by id")
    public void completeTask(@PathParam(FLAT_ID) LongParam flatId, @PathParam(TASK_ID) LongParam taskId, UserId userId) {
        requireNonNull(flatId, "UserId is required");
        LOG.info("Complete task for flat:{}, task:{}", flatId.get(), taskId.get());
        taskService.complete(flatId.get(), taskId.get(), userId.getUserId());
    }

    /**
     * Skips an existing task for a user in a flat.
     *
     * @param flatId id of the flat
     * @param taskId id of the task
     * @param userId if of the user that skipped the task
     */
    @POST
    @Timed
    @Path(TASK_UNCOMPLETE)
    @ApiOperation(value = "Skips a task by id")
    public void skipTask(@PathParam(FLAT_ID) LongParam flatId, @PathParam(TASK_ID) LongParam taskId, UserId userId) {
        requireNonNull(userId, "UserId is required");
        LOG.info("Skip task for flat:{}, task:{}", flatId.get(), taskId.get());
        taskService.skip(flatId.get(), taskId.get(), userId.getUserId());
    }
}
