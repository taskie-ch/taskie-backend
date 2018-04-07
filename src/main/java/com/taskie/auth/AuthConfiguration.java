package com.taskie.auth;

import com.google.common.cache.CacheBuilderSpec;
import com.taskie.core.User;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.CachingAuthenticator;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

public class AuthConfiguration {

    public static void configure(Environment env) {

        CachingAuthenticator<BasicCredentials, User> cachingAuthenticator = new CachingAuthenticator<>(
                env.metrics(), new SimpleAuthenticator(),
                CacheBuilderSpec.parse("maximumSize=10000, expireAfterAccess=10m"));

        env.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(cachingAuthenticator)
                        .setAuthorizer(new SimpleAuthorizer())
                        .buildAuthFilter()));

        env.jersey().register(RolesAllowedDynamicFeature.class);
        //If you want to use @Auth to inject a custom Principal type into your resource
        env.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
    }
}
