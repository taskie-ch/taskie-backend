package com.taskie.db;

import com.taskie.api.TaskCreate;
import com.taskie.core.*;
import org.joda.time.DateTime;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class TaskDao {

    private static final Map<Long, Task> TASKS = new HashMap<>();
    private static final AtomicLong UID_GENERATOR = new AtomicLong(1);

    private final TaskOccurenceFactory factory = new TaskOccurenceFactory();
    private final FlatDao flatDao;


    public TaskDao(@Nonnull FlatDao flatDao) {
        this.flatDao = flatDao;
        save(new TaskCreate("Do something", Frequency.DAILY.toString(),
                DateTime.now().toString(), Effort.LOW.getValue()));
        save(new TaskCreate("Do something else", Frequency.MONTHLY.toString(),
                DateTime.now().toString(), Effort.HUGE.getValue()));
        save(new TaskCreate("Be awesome", Frequency.WEEKLY.toString(),
                DateTime.now().toString(), Effort.HIGH.getValue()));
    }

    public Task delete(long taskId) {
        return TASKS.remove(taskId);
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
