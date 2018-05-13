package com.taskie.auth;

import com.taskie.api.FlatService;
import com.taskie.core.Flatmate;
import com.taskie.core.UserPrincipal;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import java.util.Optional;

/**
 * Simple authenticator using {@link UserPrincipal} credentials.
 */
public class SimpleAuthenticator implements Authenticator<BasicCredentials, UserPrincipal> {

    private final FlatService flatService;

    public SimpleAuthenticator(FlatService flatService) {
        this.flatService = flatService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<UserPrincipal> authenticate(BasicCredentials credentials) {

        Optional<Flatmate> flatmate = flatService.findById(1)
                .findUserByName(credentials.getUsername());

        if (flatmate.isPresent() &&
                flatmate.get().getPrincipal().authenticate(credentials.getPassword())) {
            return Optional.of(flatmate.get().getPrincipal());
        }
        return Optional.empty();
    }
}
