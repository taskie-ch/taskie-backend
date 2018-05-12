package com.taskie.core;

import com.google.common.base.MoreObjects;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Flat {

    private final long id;
    private final String name;
    private final Map<String, Flatmate> users;
    private final Map<Long, Task> tasks;
    private final HallOfFame hallOfFame;

    public Flat(long id, String name) {
        this.id = id;
        this.name = name;
        this.users = new ConcurrentHashMap<>();
        this.tasks = new ConcurrentHashMap<>();
        this.hallOfFame = new HallOfFame(new HashMap<>());
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

    public HallOfFame getHallOfFame() {
        return hallOfFame;
    }

    public void addFlatmate(Flatmate user) {
        this.users.put(user.getId(), user);
    }

    public void addAllFlatmates(Collection<Flatmate> users) {
        users.forEach(user -> this.users.put(user.getId(), user));
    }

    public void removeFlatmate(String id) {
        users.remove(id);
    }

    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public Task removeTask(long id) {
        return tasks.remove(id);
    }

    public Optional<Flatmate> findUser(String name) {
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
        return Objects.equals(name, flat.name) &&
                Objects.equals(users, flat.users) &&
                Objects.equals(hallOfFame, flat.hallOfFame);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, users, hallOfFame);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("users", users)
                .add("hallOfFame", hallOfFame)
                .toString();
    }
}
