package com.taskie.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;

/**
 * Rotation sequence set for individual tasks
 */
public class Rotation {

    private final Map<DateTime, User> rotation;

    private Rotation(Map<DateTime, User> rotation) {
        this.rotation = rotation;
    }

    @JsonProperty
    public User getCurrent() {
        return get(dateTime -> dateTime.isBefore(DateTime.now()));
    }

    @JsonProperty
    public User getNext() {
        return get(dateTime -> dateTime.isAfter(DateTime.now()));
    }

    private User get(Predicate<DateTime> predicate) {

//        Set<User> result = rotation.entrySet().stream()
//                .filter(entry -> predicate.test(entry.getKey()))
//                .map(Map.Entry::getValue)
//                .collect(Collectors.toSet());


        User user = User.NONE;
        for (Map.Entry<DateTime, User> entry : rotation.entrySet()) {
            if (predicate.test(entry.getKey())) {
                user = entry.getValue();
                break;
            }
        }
        return user;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private final Map<DateTime, User> rotation = new TreeMap<>(DateTimeComparator.getInstance());

        private Builder() {
            // private constructor
        }

        public Builder addRotation(DateTime start, User user) {
            rotation.put(start, user);
            return this;
        }

        public Rotation build() {
            if (rotation.size() < 1) {
                throw new IllegalStateException("Need to add at least one user to the rotation");
            }
            return new Rotation(rotation);
        }
    }
}
