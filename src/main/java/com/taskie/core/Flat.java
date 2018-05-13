package com.taskie.core;

import com.google.common.base.MoreObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Flat representation.
 * <p>
 * Use {@link Flat#create(long, String)} to create a new flat.
 */
public class Flat {

    private static final Logger LOG = LoggerFactory.getLogger(Flat.class);

    private final long id;
    private final String name;
    private final Map<String, Flatmate> users;
    private final Map<Long, Task> tasks;

    private Flat(long id, String name) {
        this.id = id;
        this.name = name;
        this.users = new ConcurrentHashMap<>();
        this.tasks = new ConcurrentHashMap<>();
    }

    /**
     * Creates a new instance of {@link Flat}.
     *
     * @param id   flat id
     * @param name flat name
     * @return new flat instance
     */
    public static Flat create(long id, String name) {
        return new Flat(id, name);
    }

    /**
     * @return flat id
     */
    public long getId() {
        return id;
    }

    /**
     * @return flat name
     */
    public String getName() {
        return name;
    }

    /**
     * @return set of users
     */
    public Set<Flatmate> getUsers() {
        return new HashSet<>(users.values());
    }

    /**
     * Adds a new user to the flat.
     *
     * @param user new user
     */
    public void addFlatmate(Flatmate user) {
        LOG.info("{{}}: Adding new flatmate {}", name, user.getName());
        this.users.put(user.getId(), user);
    }

    /**
     * Adds new users to the flat.
     *
     * @param users collection of new users.
     */
    public void addAllFlatmates(Collection<Flatmate> users) {
        users.forEach(this::addFlatmate);
    }

    /**
     * Removes an existing user from the flat.
     *
     * @param id id of the user
     */
    public void removeFlatmate(String id) {
        users.remove(id);
    }

    /**
     * Adds a new task to the flat.
     *
     * @param task new task
     */
    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    /**
     * Removes an existing task from the flat.
     *
     * @param id id of the task
     */
    public void removeTask(long id) {
        tasks.remove(id);
    }

    /**
     * Tries to look up a user by id and returns an optional.
     *
     * @param id id of the user
     * @return optional of user
     */
    public Optional<Flatmate> findUser(final String id) {
        return users.values().stream()
                .filter(user -> user.getId().equals(id))
                .reduce((first, next) -> first);
    }

    /**
     * Tries to look up a user by name and returns an optional.
     *
     * @param name name of the user
     * @return optional of user
     */
    public Optional<Flatmate> findUserByName(final String name) {
        return users.values().stream()
                .filter(user -> user.getName().equals(name))
                .reduce((first, next) -> first);
    }

    /**
     * @return collection of tasks
     */
    public Collection<Task> getTasks() {
        return Collections.unmodifiableCollection(tasks.values());
    }

    /**
     * Gets a task by id.
     *
     * @param id id of the task
     * @return task
     */
    public Task getTask(long id) {
        return tasks.get(id);
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Flat)) return false;
        Flat flat = (Flat) o;
        return id == flat.id &&
                Objects.equals(name, flat.name) &&
                Objects.equals(users, flat.users) &&
                Objects.equals(tasks, flat.tasks);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id, name, users, tasks);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("users", users)
                .add("tasks", tasks)
                .toString();
    }
}
