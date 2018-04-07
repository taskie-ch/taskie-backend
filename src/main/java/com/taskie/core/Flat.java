package com.taskie.core;

import com.google.common.base.MoreObjects;

import java.util.Objects;
import java.util.Set;

public class Flat {

    private final String name;
    private final Set<User> users;
    private final HallOfFame hallOfFame;

    public Flat(String name, Set<User> users, HallOfFame hallOfFame) {
        this.name = name;
        this.users = users;
        this.hallOfFame = hallOfFame;
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
