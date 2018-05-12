package com.taskie.core;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

/**
 * Date when a task has to be completed.
 */
@ThreadSafe
class DueDate {

    private static final Logger LOG = LoggerFactory.getLogger(Task.class);

    @GuardedBy("this")
    private DateTime date;

    DueDate(DateTime date) {
        this.date = date;
    }

    /**
     * Update to the next due date given the frequency.
     *
     * @param frequency time that is added to the due date
     */
    synchronized void update(Frequency frequency) {
        DateTime next = frequency.apply(date);
        LOG.info("Updating due date to {}", next);
        date = next;
    }

    /**
     * @return date as string
     * @see DateTime#toString()
     */
    synchronized String asString() {
        return date.toString();
    }
}
