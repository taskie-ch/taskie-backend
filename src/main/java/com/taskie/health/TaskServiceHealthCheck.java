package com.taskie.health;

import com.codahale.metrics.health.HealthCheck;
import com.taskie.api.TaskService;

/**
 * Checks the availability of a {@link TaskService}
 */
public class TaskServiceHealthCheck extends HealthCheck {

    private final TaskService taskService;

    public TaskServiceHealthCheck(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Checks if a task can be acquired from the resource.
     * <p>
     * {@inheritDoc}
     */
    @Override
    protected Result check() {
        try {
            taskService.findById(1, 1);
        } catch (RuntimeException e) {
            return Result.unhealthy(e.getMessage());
        }
        return Result.healthy();
    }
}
