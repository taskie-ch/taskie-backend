package com.taskie.db;

import com.taskie.core.UserPrincipal;

import java.util.HashMap;
import java.util.Map;

public class UserDao {

    private final Map<String, UserPrincipal> users = new HashMap<>();

    public UserDao() {
        save(new UserPrincipal("Tom"));
        save(new UserPrincipal("Jane"));
        save(new UserPrincipal("Joe"));
    }

    public void save(UserPrincipal user) {
        users.put(user.getName(), user);
    }

    public UserPrincipal findByName(String name) {
        UserPrincipal user = users.get(name);
        return user != null ? user : UserPrincipal.NONE;
    }
}
