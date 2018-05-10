package com.taskie.db;

import com.taskie.core.Flat;
import com.taskie.core.HallOfFame;
import com.taskie.core.UserPrincipal;

import java.util.*;

public class FlatDao {

    private final Map<Long, Flat> flats = new HashMap<>();

    private final UserDao userDao;

    public FlatDao(UserDao userDao) {
        this.userDao = userDao;
        Set<UserPrincipal> users = new HashSet<>(3);
        users.add(userDao.findByName("Tom"));
        users.add(userDao.findByName("Jane"));
        users.add(userDao.findByName("Joe"));
        flats.put(1L, new Flat(1, "Taskie Home", users, new HallOfFame(Collections.emptyMap())));
    }

    public Flat findById(long id) {
        return flats.get(id);
    }
}
