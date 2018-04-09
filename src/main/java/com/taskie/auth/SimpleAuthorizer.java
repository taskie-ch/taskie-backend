package com.taskie.auth;

import com.taskie.core.User;
import io.dropwizard.auth.Authorizer;

public class SimpleAuthorizer implements Authorizer<User> {

    /**
     * Authorizes a user to perform an action based on roles.
     *
     * @param user user to be authorized
     * @param role role required to perform an action
     * @return {@code true} if the user is authorized
     */
    @Override
    public boolean authorize(User user, String role) {
        // authorizes all user for all actions
        return true;
    }
}
