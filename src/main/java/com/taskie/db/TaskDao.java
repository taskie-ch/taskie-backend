package com.taskie.db;

import com.taskie.api.TaskService;
import com.taskie.core.Rotation;
import com.taskie.core.Task;
import com.taskie.core.User;
import org.joda.time.DateTime;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TaskDao implements TaskService {

    private static final Map<Long, Task> TASKS = new HashMap<>();

    static {

        Rotation rota = Rotation.newBuilder()
                .addRotation(DateTime.now().minusDays(1), new User("Simon"))
                .build();
        TASKS.put(1L, Task.create(1L, "test1", rota));
        TASKS.put(2L, Task.create(2L, "test2", rota));
        TASKS.put(3L, Task.create(3L, "test3", rota));
    }


    public Collection<Task> findAll() {
        return TASKS.values();
    }

    public Task findById(long id) {
        return TASKS.get(id);
    }

    @Override
    public Task complete(long taskId) {
        Task completed = Task.complete(TASKS.get(taskId));
        TASKS.put(taskId, completed);
        return completed;
    }
}
