package com.taskie.auth;

import com.taskie.core.UserPrincipal;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import java.util.Optional;

/**
 * Simple authenticator using a default credential (for the prototype).
 */
public class SimpleAuthenticator implements Authenticator<BasicCredentials, UserPrincipal> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<UserPrincipal> authenticate(BasicCredentials credentials) {
        if ("secret".equals(credentials.getPassword())) {
            return Optional.of(new UserPrincipal("x", credentials.getUsername()));
        }
        return Optional.empty();
    }
}
