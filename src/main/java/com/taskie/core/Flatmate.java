package com.taskie.core;

import com.taskie.api.User;
import org.joda.time.DateTime;

public class Flatmate {

    private final UserPrincipal principal;
    private final Email email;
    private final Score score;
    private final HolidayAbsenceList absences;

    private Flatmate(UserPrincipal principal, Email email, Score score) {
        this.principal = principal;
        this.email = email;
        this.score = score;
        this.absences = new HolidayAbsenceList();
    }

    public static Flatmate create(UserPrincipal principal, Email email, Score score) {
        return new Flatmate(principal, email, score);
    }

    public void updateAbsence(DateTime start, DateTime end) {
        absences.addIfAbsent(start, end);
    }

    void incrementScore(int points) {
        score.increment(points);
    }

    void decrementScore(int points) {
        score.decrement(points);
    }

    boolean isAbsent(DateTime date) {
        return absences.containsAbsence(date);
    }

    public String getId() {
        return principal.getId();
    }

    public String getName() {
        return principal.getName();
    }

    public Email getEmail() {
        return email;
    }

    public User deriveUser() {
        return new User(principal.getId(), principal.getName(), score.getPoints());
    }
}
