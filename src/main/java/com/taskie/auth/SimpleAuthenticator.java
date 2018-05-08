package com.taskie.auth;

import com.taskie.core.UserPrincipal;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import java.util.Optional;

public class SimpleAuthenticator implements Authenticator<BasicCredentials, UserPrincipal> {

    @Override
    public Optional<UserPrincipal> authenticate(BasicCredentials credentials) {
        if ("secret".equals(credentials.getPassword())) {
            return Optional.of(new UserPrincipal(credentials.getUsername()));
        }
        return Optional.empty();
    }
}
