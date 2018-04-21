package com.taskie.core;

import com.google.common.base.MoreObjects;
import org.joda.time.DateTime;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User implements Principal {

    public static final User NONE = new User("NONE");

    private final String name;
    private final List<Absence> absences = new ArrayList<>();

    public User(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void addAbsence(Absence absence) {
        absences.add(absence);
    }

    public boolean isAbsent(final DateTime date) {
        return absences.stream().anyMatch(absence -> absence.match(date));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .toString();
    }
}
