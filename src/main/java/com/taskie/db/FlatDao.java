package com.taskie.db;

import com.taskie.core.*;

import java.util.*;

public class FlatDao {

    private final Map<Long, Flat> flats = new HashMap<>();

    public FlatDao(UserDao userDao) {
        Flat flat = Flat.create(1, "Taskie Home");
        Set<Flatmate> users = new HashSet<>(3);
        users.add(Flatmate.create(userDao.findByName("Tom"), new Email("tom@students.zhaw.ch"), new Score(5)));
        users.add(Flatmate.create(userDao.findByName("Jane"), new Email("jane@students.zhaw.ch"), new Score(6)));
        users.add(Flatmate.create(userDao.findByName("Joe"), new Email("joe@students.zhaw.ch"), new Score(4)));
        flat.addAllFlatmates(users);
        save(flat);
    }

    public void save(Flat flat) {
        flats.put(flat.getId(), flat);
    }

    public Flat findById(long id) {
        return flats.get(id);
    }

    public List<Flatmate> findUsers(long flatId, List<String> names) {

        Flat flat = findById(flatId);
        final List<Flatmate> users = new ArrayList<>();

        for (String name : names) {
            flat.findUser(name).ifPresent(users::add);
        }
        return users;
    }
}
