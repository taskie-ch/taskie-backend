package com.taskie.core;

import com.google.common.base.MoreObjects;

import java.security.Principal;
import java.util.Objects;

/**
 * User principal required for authentication.
 */
public final class UserPrincipal implements Principal {

    private final String id;
    private final String name;
    /**
     * Static password for the prototype.
     */
    // TODO use hash
    private static final String PASSWORD = "secret";

    /**
     * Creates an user principal.
     * Both id and name have to be unique for a user.
     *
     * @param id   unique user id
     * @param name unique user name
     */
    public UserPrincipal(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Authenticates a user with a password.
     *
     * @param password user password
     * @return {@code true} if password matches
     */
    public boolean authenticate(String password) {
        return PASSWORD.equals(password);
    }

    /**
     * @return user id
     */
    public String getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UserPrincipal)) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .toString();
    }
}
