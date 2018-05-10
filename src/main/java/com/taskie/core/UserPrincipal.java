package com.taskie.core;

import com.google.common.base.MoreObjects;
import com.taskie.api.User;
import org.joda.time.DateTime;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserPrincipal implements Principal {

    public static final UserPrincipal NONE = new UserPrincipal("x", "NONE");

    private final String id;
    private final String name;
    private final List<Absence> absences = new ArrayList<>();
    private int score = 0;

    public UserPrincipal(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserPrincipal(String id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addAbsence(Absence absence) {
        absences.add(absence);
    }

    public boolean isAbsent(final DateTime date) {
        return absences.stream().anyMatch(absence -> absence.match(date));
    }

    public User deriveUser() {
        return new User(id, name, score);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return score == that.score &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(absences, that.absences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, absences, score);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("absences", absences)
                .add("score", score)
                .toString();
    }
}
