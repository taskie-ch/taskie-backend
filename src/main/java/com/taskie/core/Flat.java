package com.taskie.core;

import com.google.common.base.MoreObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
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

    public static Flat create(long id, String name) {
        return new Flat(id, name);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Flatmate> getUsers() {
        return new HashSet<>(users.values());
    }

    public void addFlatmate(Flatmate user) {
        this.users.put(user.getId(), user);
    }

    public void addAllFlatmates(Collection<Flatmate> users) {
        LOG.info("Flat:{} - Adding new flatmates {}", id, users);
        users.forEach(user -> this.users.put(user.getId(), user));
    }

    public void removeFlatmate(String id) {
        users.remove(id);
    }

    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void removeTask(long id) {
        tasks.remove(id);
    }

    public Optional<Flatmate> findUser(final String id) {
        return users.values().stream()
                .filter(user -> user.getId().equals(id))
                .reduce((first, next) -> first);
    }

    public Optional<Flatmate> findUserByName(final String name) {
        return users.values().stream()
                .filter(user -> user.getName().equals(name))
                .reduce((first, next) -> first);
    }

    public Collection<Task> getTasks() {
        return tasks.values();
    }

    public Task getTask(long id) {
        return tasks.get(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flat flat = (Flat) o;
        return id == flat.id &&
                Objects.equals(name, flat.name) &&
                Objects.equals(users, flat.users) &&
                Objects.equals(tasks, flat.tasks);
    }

    @Override
    public int hashCode() {
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
