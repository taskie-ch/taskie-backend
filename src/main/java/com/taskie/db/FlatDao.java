package com.taskie.db;

import com.taskie.core.Flat;
import com.taskie.core.HallOfFame;
import com.taskie.core.User;

import java.util.*;

public class FlatDao {

    private static final Map<Long, Flat> FLATS = new HashMap<>();

    static {
        Set<User> users = new HashSet<>(3);
        users.add(new User("Tom"));
        users.add(new User("Jane"));
        users.add(new User("Joe"));
        FLATS.put(1L, new Flat(1, "Taskie Home", users, new HallOfFame(Collections.emptyMap())));
    }

    public Flat findById(long id) {
        return FLATS.get(id);
    }
}
