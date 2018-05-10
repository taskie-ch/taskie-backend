package com.taskie.db;

import com.taskie.api.TaskCreate;
import com.taskie.core.Effort;
import com.taskie.core.Frequency;
import com.taskie.core.Task;
import com.taskie.core.TaskOccurenceFactory;
import org.joda.time.DateTime;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class TaskDao {

    private static final Map<Long, Task> TASKS = new HashMap<>();
    private static final AtomicLong UID_GENERATOR = new AtomicLong(1);

    private final TaskOccurenceFactory factory;
    private final FlatDao flatDao;


    public TaskDao(@Nonnull FlatDao flatDao, @Nonnull UserDao userDao) {
        this.flatDao = flatDao;
        this.factory = new TaskOccurenceFactory(userDao);
        save(new TaskCreate("Throw garbage", Frequency.WEEKLY.toString(),
                        DateTime.parse("2018-05-09T00:00").toString(), Effort.LOW.getValue()),
                Arrays.asList("Jane", "Tom", "Joe"));
        save(new TaskCreate("Buy groceries", Frequency.WEEKLY.toString(),
                        DateTime.parse("2018-05-16T00:00").toString(), Effort.LOW.getValue()),
                Arrays.asList("Tom", "Joe", "Jane"));
        save(new TaskCreate("Clean bathroom", Frequency.WEEKLY.toString(),
                        DateTime.parse("2018-05-19T00:00").toString(), Effort.HIGH.getValue()),
                Arrays.asList("Joe", "Tom", "Jane"));
    }

    public Task delete(long taskId) {
        return TASKS.remove(taskId);
    }

    public Task save(@Nonnull TaskCreate taskCreate, List<String> userNames) {
        Task task = Task.newBuilder(taskCreate)
                .setId(UID_GENERATOR.getAndIncrement())
                .addOccurences(factory.applyOrdered(taskCreate, userNames))
                .build();
        TASKS.put(task.getId(), task);
        return task;
    }

    public Task save(@Nonnull TaskCreate taskCreate) {
        Task task = Task.newBuilder(taskCreate)
                .setId(UID_GENERATOR.getAndIncrement())
                .addOccurences(factory.apply(taskCreate, flatDao.findById(1).getUsers()))
                .build();
        TASKS.put(task.getId(), task);
        return task;
    }

    public void update(@Nonnull Task task) {
        TASKS.put(task.getId(), task);
    }

    @Nonnull
    public Collection<Task> findAll() {
        return TASKS.values();
    }

    @Nonnull
    public Task findById(long id) {
        Task task = TASKS.get(id);
        if (task == null) {
            throw new IllegalArgumentException("No element for id: " + id);
        }
        return task;
    }

    public void complete(long taskId) {
        findById(taskId).complete();
    }

    public void uncomplete(long taskId) {
        findById(taskId).uncomplete();
    }
}
