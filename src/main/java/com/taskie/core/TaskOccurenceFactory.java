package com.taskie.core;

import com.taskie.api.TaskCreate;
import com.taskie.db.UserDao;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TaskOccurenceFactory {

    private final UserDao userDao;

    public TaskOccurenceFactory(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<TaskOccurence> apply(TaskCreate task, Collection<UserPrincipal> users) {

        List<TaskOccurence> occurences = new ArrayList<>(users.size());
        DateTime date = DateTime.parse(task.getStart());

        for (UserPrincipal user : users) {
            occurences.add(new TaskOccurence(date, user));
            date = Frequency.valueOf(task.getFrequency().toUpperCase()).apply(date);
        }

        return occurences;
    }

    public List<TaskOccurence> applyOrdered(TaskCreate task, List<String> users) {

        List<TaskOccurence> occurences = new ArrayList<>(users.size());
        DateTime date = DateTime.parse(task.getStart());

        for (String name : users) {
            UserPrincipal user = userDao.findByName(name);
            occurences.add(new TaskOccurence(date, user));
            date = Frequency.valueOf(task.getFrequency().toUpperCase()).apply(date);
        }

        return occurences;
    }
}