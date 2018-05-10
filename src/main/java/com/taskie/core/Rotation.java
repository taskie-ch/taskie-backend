package com.taskie.core;

import com.google.common.base.MoreObjects;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;

/**
 * Rotation sequence set for individual tasks
 */
public class Rotation {

    private final Map<DateTime, UserPrincipal> rotation;

    private Rotation(Map<DateTime, UserPrincipal> rotation) {
        this.rotation = rotation;
    }

    public Map<DateTime, UserPrincipal> getRotation() {
        return rotation;
    }

    public UserPrincipal getCurrent() {
        return get(dateTime -> dateTime.isBefore(DateTime.now()));
    }

    public UserPrincipal getNext() {
        return get(dateTime -> dateTime.isAfter(DateTime.now()));
    }

    private UserPrincipal get(Predicate<DateTime> predicate) {

//        Set<UserPrincipal> result = rotation.entrySet().stream()
//                .filter(entry -> predicate.test(entry.getKey()))
//                .map(Map.Entry::getValue)
//                .collect(Collectors.toSet());


        UserPrincipal user = UserPrincipal.NONE;
        for (Map.Entry<DateTime, UserPrincipal> entry : rotation.entrySet()) {
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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("rotation", rotation)
                .toString();
    }

    public static class Builder {

        private final Map<DateTime, UserPrincipal> rotation = new TreeMap<>(DateTimeComparator.getInstance());

        private Builder() {
            // private constructor
        }

        public Builder addRotation(DateTime start, UserPrincipal user) {
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
