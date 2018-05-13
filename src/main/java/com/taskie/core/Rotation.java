package com.taskie.core;

import com.google.common.base.MoreObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Rotation sequence set for individual tasks
 */
class Rotation {

    private static final Logger LOG = LoggerFactory.getLogger(Rotation.class);

    private final Queue<Flatmate> rotation = new LinkedList<>();

    /**
     * Rotates the current user to the end.
     */
    synchronized void update() {
        Flatmate current = rotation.remove();
        LOG.info("Rotating {} to the end", current);
        rotation.add(current);

        // TODO skip if user is absent
    }

    /**
     * Extends the rotation with users. Ordering is preserved.
     *
     * @param users users to add to the rotation
     */
    synchronized void addAll(Collection<Flatmate> users) {
        rotation.addAll(users);
    }

    /**
     * Applies an action to a user in the rotation, if present.
     *
     * @param id       id of the user
     * @param consumer action to be applied
     */
    void applyToUser(String id, Consumer<Flatmate> consumer) {
        rotation.stream().filter(user -> user.getId().equals(id))
                .reduce((flatmate, flatmate2) -> flatmate)
                .ifPresent(consumer);
    }


    /**
     * Collects all ids of users in the rotation in the right order.
     *
     * @return list of user ids
     */
    List<String> getRotationUserIds() {
        return rotation.stream()
                .map(Flatmate::getId)
                .collect(Collectors.toList());
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Rotation)) return false;
        Rotation rotation1 = (Rotation) o;
        return Objects.equals(rotation, rotation1.rotation);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(rotation);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("rotation", rotation)
                .toString();
    }
}
