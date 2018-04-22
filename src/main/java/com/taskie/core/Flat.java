package com.taskie.core;

import com.google.common.base.MoreObjects;
import org.joda.time.DateTime;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Flat {

    private final long id;
    private final String name;
    private final Set<User> users;
    private final HallOfFame hallOfFame;

    public Flat(long id, String name, Set<User> users, HallOfFame hallOfFame) {
        this.id = id;
        this.name = name;
        this.users = users;
        this.hallOfFame = hallOfFame;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public HallOfFame getHallOfFame() {
        return hallOfFame;
    }

    public Set<User> getAbsenceForDate(final DateTime date) {
        return users.stream().filter(user -> user.isAbsent(date)).collect(Collectors.toSet());
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
