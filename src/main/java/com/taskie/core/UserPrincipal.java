package com.taskie.core;

import com.google.common.base.MoreObjects;
import com.taskie.api.User;
import org.joda.time.DateTime;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserPrincipal implements Principal {

    public static final UserPrincipal NONE = new UserPrincipal("NONE");

    private final String name;
    private final List<Absence> absences = new ArrayList<>();

    public UserPrincipal(String name) {
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

    public User deriveUser() {
        // TODO wire together
        return new User("123", name, 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal user = (UserPrincipal) o;
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
