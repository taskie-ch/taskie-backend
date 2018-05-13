package com.taskie.db;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
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
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Data access object for {@link Task} storing data in memory.
 * <p>
 * Use {@link InMemoryTaskDao#create(FlatService)} to create a new DAO.
 */
public class InMemoryTaskDao implements TaskService {

    private static final Logger LOG = LoggerFactory.getLogger(InMemoryTaskDao.class);

    /**
     * Unique task Id generator
     */
    private static final AtomicLong UID_GENERATOR = new AtomicLong(1);

    private final FlatService flatService;

    private InMemoryTaskDao(@Nonnull FlatService flatService, Map<Long, List<TaskCreate>> tasks) {
        this.flatService = flatService;
        // save provided tasks
        for (final Long flatId : tasks.keySet()) {
            tasks.get(flatId).forEach(taskCreate -> save(flatId, taskCreate));
        }
    }

    /**
     * Creates a new instance of {@link InMemoryTaskDao}
     * and initialises it with tasks for the existing flat (for the prototype).
     *
     * @param flatService flat service
     * @return initialised in-memory task DAO
     */
    public static InMemoryTaskDao create(FlatService flatService) {
        return new InMemoryTaskDao(flatService,
                // initialise example tasks for flatId:1
                Maps.asMap(Sets.newHashSet(1L), id -> Arrays.asList(
                        new TaskCreate("Throw garbage", Frequency.WEEKLY.toString(),
                                DateTime.parse("2018-05-09T00:00").toString(), Effort.LOW.intValue(),
                                buildUserIds(Arrays.asList("Jane", "Tom", "Joe"))),
                        new TaskCreate("Buy groceries", Frequency.WEEKLY.toString(),
                                DateTime.parse("2018-05-16T00:00").toString(), Effort.LOW.intValue(),
                                buildUserIds(Arrays.asList("Tom", "Joe", "Jane"))),
                        new TaskCreate("Clean bathroom", Frequency.WEEKLY.toString(),
                                DateTime.parse("2018-05-19T00:00").toString(), Effort.HIGH.intValue(),
                                buildUserIds(Arrays.asList("Joe", "Tom", "Jane")))
                )));
    }

    /**
     * Maps user names to ids using {@link InMemoryFlatDao#generateUserId(String)}.
     *
     * @param userNames list of user names
     * @return list of user ids
     */
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

        Flat flat = flatService.findById(flatId);

        List<Flatmate> flatmates = flatService.findUsers(flatId, taskCreate.getUserIds());
        if (LOG.isInfoEnabled()) {
            LOG.info("{{}|{}}: Set rotation to {}", flat.getName(), taskCreate.getTitle(),
                    flatmates.stream().map(Flatmate::getName).collect(Collectors.toList()));
        }

        Task task = Task.newBuilder(taskCreate)
                .setId(taskId)
                .setRotation(flatmates)
                .build();
        flat.addTask(task);
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
