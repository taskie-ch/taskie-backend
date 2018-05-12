package com.taskie.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * Rotation sequence set for individual tasks
 */
public class Rotation {

    private final Logger LOG = LoggerFactory.getLogger(Rotation.class);

    private Queue<Flatmate> rotation = new LinkedList<>();

    public void addAll(Collection<Flatmate> users) {
        rotation.addAll(users);
    }

    public Flatmate currentUser() {
        return rotation.peek();
    }

    public void update() {
        Flatmate current = rotation.remove();
        LOG.info("Rotating {} to the end", current);
        rotation.add(current);

        // TODO skip if user is absent
    }

    public void rollback() {
        // TODO do we need this even?
    }

    List<String> getRotationUserIds() {
        return rotation.stream()
                .map(Flatmate::getId)
                .collect(Collectors.toList());
    }
}
