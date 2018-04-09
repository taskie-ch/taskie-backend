package com.taskie.resources;

import com.codahale.metrics.annotation.Timed;
import com.taskie.api.TaskSchedule;
import com.taskie.core.Rotation;
import com.taskie.core.Task;
import com.taskie.core.User;
import com.taskie.db.TaskDao;
import io.dropwizard.jersey.params.LongParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;
import java.util.TreeMap;

@Path("/schedules")
@Api(value = "schedules")
@Produces(MediaType.APPLICATION_JSON)
public class TaskScheduleResource {

    private static final Logger LOG = LoggerFactory.getLogger(TaskScheduleResource.class);

    private final TaskDao taskDao;

    public TaskScheduleResource(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @GET
    @Timed
    @Path("/{taskId}")
    @ApiOperation(value = "Get the schedule for a task")
    public TaskSchedule getRotation(@PathParam("taskId") LongParam id) {
        Map<DateTime, User> rotation = taskDao.findById(id.get()).getRotation().getRotation();
        Map<DateTime, String> schedule = new TreeMap<>(DateTimeComparator.getInstance());
        rotation.forEach((dateTime, user) -> schedule.put(dateTime, user.getName()));
        return new TaskSchedule(schedule);
    }

    @POST
    @Timed
    @Path("/{taskId}")
    @ApiOperation(value = "Set a schedule for a task")
    public void setRotation(@PathParam("taskId") LongParam id, TaskSchedule taskSchedule) {
        Task task = taskDao.findById(id.get());
        Rotation.Builder builder = Rotation.newBuilder();
        taskSchedule.getDateToUser().forEach((dateTime, s) -> builder.addRotation(dateTime, new User(s)));
        Rotation rotation = builder.build();
        LOG.info("POST task schedule: {}->{}", id.get(), rotation);

        taskDao.update(Task.create(task, rotation));
    }
}
