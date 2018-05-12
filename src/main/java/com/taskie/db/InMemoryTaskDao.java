package com.taskie.db;

import com.taskie.api.FlatService;
import com.taskie.api.TaskCreate;
import com.taskie.api.TaskInfo;
import com.taskie.api.TaskService;
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

/**
 * Data access object for {@link Task} storing data in memory.
 */
public class InMemoryTaskDao implements TaskService {

    private static final Logger LOG = LoggerFactory.getLogger(Rotation.class);

    /**
     * Unique task Id generator
     */
    private static final AtomicLong UID_GENERATOR = new AtomicLong(1);

    private final FlatService flatService;

    public InMemoryTaskDao(@Nonnull FlatService flatService) {
        this.flatService = flatService;
        save(1, new TaskCreate("Throw garbage", Frequency.WEEKLY.toString(),
                DateTime.parse("2018-05-09T00:00").toString(), Effort.LOW.intValue(),
                buildUserIds(Arrays.asList("Jane", "Tom", "Joe"))));
        save(1, new TaskCreate("Buy groceries", Frequency.WEEKLY.toString(),
                DateTime.parse("2018-05-16T00:00").toString(), Effort.LOW.intValue(),
                buildUserIds(Arrays.asList("Tom", "Joe", "Jane"))));
        save(1, new TaskCreate("Clean bathroom", Frequency.WEEKLY.toString(),
                DateTime.parse("2018-05-19T00:00").toString(), Effort.HIGH.intValue(),
                buildUserIds(Arrays.asList("Joe", "Tom", "Jane"))));
    }

    private static List<String> buildUserIds(List<String> userNames) {
        return userNames.stream().map(InMemoryFlatDao::generateUserId).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public Collection<Task> findAll(long flatId) {
        return flatService.findById(flatId).getTasks();
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public Task findById(long flatId, long taskId) {
        Task task = flatService.findById(flatId).getTask(taskId);
        if (task == null) {
            throw new IllegalArgumentException("No element for id: " + taskId);
        }
        return task;
    }


    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public Task save(long flatId, @Nonnull TaskCreate taskCreate) {
        return save(flatId, UID_GENERATOR.getAndIncrement(), taskCreate);
    }

    private Task save(long flatId, long taskId, TaskCreate taskCreate) {

        List<Flatmate> flatmates = flatService.findUsers(flatId, taskCreate.getUserIds());
        LOG.info("Set rotation for task [{}] as {}", taskCreate.getTitle(), flatmates);

        Task task = Task.newBuilder(taskCreate)
                .setId(taskId)
                .setRotation(flatmates)
                .build();
        flatService.findById(flatId).addTask(task);
        return task;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(long flatId, @Nonnull TaskInfo taskInfo) {
        Task task = flatService.findById(flatId).getTask(taskInfo.getId());
        if (task == null) {
            throw new IllegalArgumentException("No task for id: " + taskInfo.getId());
        }
        save(flatId, task.getId(), taskInfo.deriveTaskCreate());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(long flatId, long taskId) {
        flatService.findById(flatId).removeTask(taskId);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void complete(long flatId, long taskId, @Nonnull String userId) {
        findById(flatId, taskId).markTaskAsDone(userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void skip(long flatId, long taskId, @Nonnull String userId) {
        findById(flatId, taskId).skipTask(userId);
    }
}
