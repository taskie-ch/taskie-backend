package com.taskie.core;

import com.google.common.base.MoreObjects;

import java.util.Objects;
import java.util.Set;

public class Flat {

    private final Set<User> users;

    public Flat(Set<User> users) {
        this.users = users;
    }

    public Set<User> getUsers() {
        return users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flat flat = (Flat) o;
        return Objects.equals(users, flat.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(users);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("users", users)
                .toString();
    }
}
