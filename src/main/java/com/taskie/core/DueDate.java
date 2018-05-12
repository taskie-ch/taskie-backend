package com.taskie.core;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class DueDate {

    private final Logger LOG = LoggerFactory.getLogger(Task.class);

    @GuardedBy("this")
    private DateTime date;

    public DueDate(DateTime date) {
        this.date = date;
    }

    public synchronized void update(Frequency frequency) {
        DateTime next = frequency.apply(date);
        LOG.info("Updating due date to {}", next);
        date = next;
    }

    public synchronized DateTime getDate() {
        return date;
    }

    /**
     * @return date as string
     * @see DateTime#toString()
     */
    public synchronized String asString() {
        return date.toString();
    }
}
