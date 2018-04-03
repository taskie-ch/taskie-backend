package com.taskie.db;

import com.taskie.core.Rotation;
import com.taskie.core.Task;
import com.taskie.core.User;
import org.joda.time.DateTime;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class TaskDao {

    private static final Map<Long, Task> TASKS = new HashMap<>();
    private static final AtomicLong UID_GENERATOR = new AtomicLong(1);

    static {

        Rotation rota = Rotation.newBuilder()
                .addRotation(DateTime.now().minusDays(1), new User("Simon"))
                .build();
        TASKS.put(UID_GENERATOR.getAndIncrement(), Task.create(1L, "test1", rota));
        TASKS.put(UID_GENERATOR.getAndIncrement(), Task.create(2L, "test2", rota));
        TASKS.put(UID_GENERATOR.getAndIncrement(), Task.create(3L, "test3", rota));
    }

    public Task delete(long taskId) {
        return TASKS.remove(taskId);
    }

    public Task save(String taskDescription, boolean done) {
        Task task = Task.create(UID_GENERATOR.getAndIncrement(), taskDescription, done);
        TASKS.put(task.getId(), task);
        return task;
    }

    public void update(Task task) {
        TASKS.put(task.getId(), task);
    }

    public Collection<Task> findAll() {
        return TASKS.values();
    }

    public Task findById(long id) {
        Task task = TASKS.get(id);
        if (task == null) {
            throw new IllegalArgumentException("No element for id: " + id);
        }
        return task;
    }

    public Task complete(long taskId) {
        Task completed = Task.complete(TASKS.get(taskId));
        TASKS.put(taskId, completed);
        return completed;
    }

    public Task uncomplete(long taskId) {
        Task changed = Task.uncomplete(TASKS.get(taskId));
        TASKS.put(taskId, changed);
        return changed;
    }

}
