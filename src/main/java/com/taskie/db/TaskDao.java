package com.taskie.db;

import com.taskie.core.Task;
import io.dropwizard.jersey.params.LongParam;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TaskDao {

    private static final Map<Long, Task> TASKS = new HashMap<>();

    static {
        TASKS.put(1L, new Task("test1"));
        TASKS.put(2L, new Task("test2"));
        TASKS.put(3L, new Task("test3"));
    }


    public Collection<Task> findAll() {
        return TASKS.values();
    }

    public Task findById(LongParam id) {
        return TASKS.get(id.get());
    }
}
