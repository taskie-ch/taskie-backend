package com.taskie.core;

import com.taskie.api.TaskCreate;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TaskOccurenceFactory {

    public List<TaskOccurence> apply(TaskCreate task, Collection<User> users) {

        List<TaskOccurence> occurences = new ArrayList<>(users.size());
        DateTime date = DateTime.parse(task.getStart());

        for (User user : users) {
            occurences.add(new TaskOccurence(date, user));
            date = Frequency.valueOf(task.getFrequency().toUpperCase()).apply(date);
        }
        return occurences;
    }
}
