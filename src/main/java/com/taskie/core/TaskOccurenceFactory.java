package com.taskie.core;

import com.taskie.api.TaskCreate;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TaskOccurenceFactory {

    public List<TaskOccurence> apply(TaskCreate task, Collection<User> users) {

        List<TaskOccurence> occurences = new ArrayList<>(users.size());
        DateTime date = rand(DateTime.parse(task.getStart()));

        for (User user : users) {
            occurences.add(new TaskOccurence(date, user));
            date = Frequency.valueOf(task.getFrequency().toUpperCase()).apply(date);
        }

        for (int i = 0; i < ThreadLocalRandom.current().nextInt(3); i++) {
            rotate(occurences);
        }

        return occurences;
    }

    private DateTime rand(DateTime date) {
        return date.plusDays(ThreadLocalRandom.current().nextInt(20));
    }

    private void rotate(List<TaskOccurence> occurences) {
        occurences.add(occurences.remove(0));
    }
}
