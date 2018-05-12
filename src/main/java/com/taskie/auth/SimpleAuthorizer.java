package com.taskie.auth;

import com.taskie.core.UserPrincipal;
import io.dropwizard.auth.Authorizer;

/**
 * Simple authorizer permitting all actions (for the prototype).
 */
public class SimpleAuthorizer implements Authorizer<UserPrincipal> {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean authorize(UserPrincipal user, String role) {
        // authorizes all user for all actions
        return true;
    }
}
