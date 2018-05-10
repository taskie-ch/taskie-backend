package com.taskie.auth;

import com.taskie.core.UserPrincipal;
import io.dropwizard.auth.Authorizer;

public class SimpleAuthorizer implements Authorizer<UserPrincipal> {

    /**
     * Authorizes a user to perform an action based on roles.
     *
     * @param user user to be authorized
     * @param role role required to perform an action
     * @return {@code true} if the user is authorized
     */
    @Override
    public boolean authorize(UserPrincipal user, String role) {
        // authorizes all user for all actions
        return true;
    }
}
