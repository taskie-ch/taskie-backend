package com.taskie.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Rotation sequence set for individual tasks
 */
public class Rotation {

    private static final Logger LOG = LoggerFactory.getLogger(Rotation.class);

    private Queue<Flatmate> rotation = new LinkedList<>();

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
}
