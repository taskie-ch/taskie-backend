package com.taskie.api;

import com.taskie.core.Task;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * Service for all operations concerning tasks.
 *
 * @see com.taskie.db.InMemoryTaskDao
 */
public interface TaskService {

    /**
     * Gets all open tasks for a flat.
     *
     * @param flatId id of the flat
     * @return collection of all tasks for a flat
     */
    @Nonnull
    Collection<Task> findAll(long flatId);

    /**
     * Gets an existing task for a flat.
     *
     * @param flatId id of the flat
     * @param taskId id of the task
     * @return existing task
     */
    @Nonnull
    Task findById(long flatId, long taskId);

    /**
     * Saves a new task for a flat.
     *
     * @param flatId     id of the flat
     * @param taskCreate payload for creating a task
     * @return newly created task
     */
    @Nonnull
    Task save(long flatId, @Nonnull TaskCreate taskCreate);

    /**
     * Updates an existing task for a flat.
     *
     * @param flatId   id of the flat
     * @param taskInfo payload of the task
     */
    void update(long flatId, @Nonnull TaskInfo taskInfo);

    /**
     * Deletes an existing task for a flat.
     *
     * @param flatId id of the flat
     * @param taskId if of the task
     */
    void delete(long flatId, long taskId);

    /**
     * Marks an existing task as done by a user in a flat.
     *
     * @param flatId id of the flat
     * @param taskId id of the task
     * @param userId id of the user that completed the task
     */
    void complete(long flatId, long taskId, @Nonnull String userId);

    /**
     * Skips an existing task for a user in a flat.
     *
     * @param flatId id of the flat
     * @param taskId id of the task
     * @param userId if of the user that skipped the task
     */
    void skip(long flatId, long taskId, @Nonnull String userId);
}
