package com.taskie.db;

import com.taskie.core.UserPrincipal;
import org.eclipse.jetty.util.security.Credential;

import java.util.HashMap;
import java.util.Map;

public class UserDao {

    private final Map<String, UserPrincipal> users = new HashMap<>();

    public UserDao() {
        save("Tom", 5);
        save("Jane", 6);
        save("Joe", 4);
    }

    public UserPrincipal save(String name, int score) {
        UserPrincipal user = new UserPrincipal(generateId(name), name, score);
        users.put(name, user);
        return user;
    }

    public UserPrincipal findByName(String name) {
        UserPrincipal user = users.get(name);
        return user != null ? user : UserPrincipal.NONE;
    }

    private String generateId(String name) {
        return Credential.MD5.digest(name).substring(4);
    }
}
