package com.taskie.db;

import com.taskie.api.TaskCreate;
import com.taskie.api.TaskInfo;
import com.taskie.core.*;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class TaskDao {

    private static final Logger LOG = LoggerFactory.getLogger(Rotation.class);
    private static final AtomicLong UID_GENERATOR = new AtomicLong(1);

    private final FlatDao flatDao;

    public TaskDao(@Nonnull FlatDao flatDao) {
        this.flatDao = flatDao;
        save(1, new TaskCreate("Throw garbage", Frequency.WEEKLY.toString(),
                DateTime.parse("2018-05-09T00:00").toString(), Effort.LOW.getValue(),
                buildUserIds(Arrays.asList("Jane", "Tom", "Joe"))));
        save(1, new TaskCreate("Buy groceries", Frequency.WEEKLY.toString(),
                DateTime.parse("2018-05-16T00:00").toString(), Effort.LOW.getValue(),
                buildUserIds(Arrays.asList("Tom", "Joe", "Jane"))));
        save(1, new TaskCreate("Clean bathroom", Frequency.WEEKLY.toString(),
                DateTime.parse("2018-05-19T00:00").toString(), Effort.HIGH.getValue(),
                buildUserIds(Arrays.asList("Joe", "Tom", "Jane"))));
    }

    private static List<String> buildUserIds(List<String> userNames) {
        return userNames.stream().map(UserDao::generateId).collect(Collectors.toList());
    }

    public Task delete(long flatId, long taskId) {
        return flatDao.findById(flatId).removeTask(taskId);
    }

    private Task save(long flatId, long taskId, TaskCreate taskCreate) {

        List<Flatmate> flatmates = flatDao.findUsers(flatId, taskCreate.getUserIds());
        LOG.info("Set rotation for task [{}] as {}", taskCreate.getTitle(), flatmates);

        Task task = Task.newBuilder(taskCreate)
                .setId(taskId)
                .setRotation(flatmates)
                .build();
        flatDao.findById(flatId).addTask(task);
        return task;
    }

    public Task save(long flatId, @Nonnull TaskCreate taskCreate) {
        return save(flatId, UID_GENERATOR.getAndIncrement(), taskCreate);
    }

    public void update(long flatId, @Nonnull TaskInfo taskInfo) {
        Task task = flatDao.findById(flatId).getTask(taskInfo.getId());
        if (task == null) {
            throw new IllegalArgumentException("No task for id: " + taskInfo.getId());
        }
        save(flatId, task.getId(), taskInfo.deriveTaskCreate());
    }

    @Nonnull
    public Collection<Task> findAll(long flatId) {
        return flatDao.findById(flatId).getTasks();
    }

    @Nonnull
    public Task findById(long flatId, long taskId) {
        Task task = flatDao.findById(flatId).getTask(taskId);
        if (task == null) {
            throw new IllegalArgumentException("No element for id: " + taskId);
        }
        return task;
    }

    public void complete(long flatId, long taskId) {
        findById(flatId, taskId).markTaskAsDone();
    }

    public void skip(long flatId, long taskId) {
        findById(flatId, taskId).skipTask();
    }
}
