package com.taskie.health;

import com.codahale.metrics.health.HealthCheck;
import com.taskie.db.TaskDao;

public class TaskServiceHealthCheck extends HealthCheck {

    private final TaskDao taskDao;

    public TaskServiceHealthCheck(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    /**
     * Checks if a task can be acquired from the resource.
     *
     * {@inheritDoc}
     */
    @Override
    protected Result check() {
        try {
            taskDao.findById(1,1);
        } catch (RuntimeException e) {
            return Result.unhealthy(e.getMessage());
        }
        return Result.healthy();
    }
}
