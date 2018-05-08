package com.taskie.db;

import com.taskie.core.Flat;
import com.taskie.core.HallOfFame;
import com.taskie.core.UserPrincipal;

import java.util.*;

public class FlatDao {

    private static final Map<Long, Flat> FLATS = new HashMap<>();

    static {
        Set<UserPrincipal> users = new HashSet<>(3);
        users.add(new UserPrincipal("Tom"));
        users.add(new UserPrincipal("Jane"));
        users.add(new UserPrincipal("Joe"));
        FLATS.put(1L, new Flat(1, "Taskie Home", users, new HallOfFame(Collections.emptyMap())));
    }

    public Flat findById(long id) {
        return FLATS.get(id);
    }
}
