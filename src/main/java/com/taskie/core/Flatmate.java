package com.taskie.core;

import com.google.common.base.MoreObjects;
import com.taskie.api.User;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A user in the context of the application.
 */
public class Flatmate {

    private final UserPrincipal principal;
    private final Email email;
    private final Score score;
    private final List<HolidayAbsence> absences;

    private Flatmate(UserPrincipal principal, Email email, Score score) {
        this.principal = principal;
        this.email = email;
        this.score = score;
        this.absences = new ArrayList<>();
    }

    public static Flatmate create(UserPrincipal principal, Email email, Score score) {
        return new Flatmate(principal, email, score);
    }

    /**
     * Adds an absence to the user..
     *
     * @param start from date
     * @param end   to date
     */
    public void updateAbsence(DateTime start, DateTime end) {
        absences.add(new HolidayAbsence(start, end));
    }

    /**
     * Increments the user score.
     *
     * @param points increment value
     */

    void incrementScore(int points) {
        score.increment(points);
    }

    /**
     * Decrements the user score.
     *
     * @param points decrement value
     */
    void decrementScore(int points) {
        score.decrement(points);
    }

    /**
     * Check if the user has an absence for the given date.
     *
     * @param date absence date
     * @return {@code true} if the user is absent
     */
    boolean isAbsent(DateTime date) {
        return absences.stream().anyMatch(holidayAbsence -> holidayAbsence.matches(date));
    }

    /**
     * @return user id
     */
    public String getId() {
        return principal.getId();
    }

    /**
     * @return user name
     */
    public String getName() {
        return principal.getName();
    }

    /**
     * @return user email
     */
    public Email getEmail() {
        return email;
    }

    /**
     * Derives the JSON API representation of the current user.
     *
     * @return JSON API representation
     */
    public User deriveUser() {
        return new User(principal.getId(), principal.getName(), score.intValue());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flatmate flatmate = (Flatmate) o;
        return Objects.equals(principal, flatmate.principal) &&
                Objects.equals(email, flatmate.email) &&
                Objects.equals(score, flatmate.score) &&
                Objects.equals(absences, flatmate.absences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(principal, email, score, absences);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("principal", principal)
                .add("email", email)
                .add("score", score)
                .add("absences", absences)
                .toString();
    }
}
