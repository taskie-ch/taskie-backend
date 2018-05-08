package com.taskie.auth;

import com.codahale.metrics.MetricRegistry;
import com.google.common.cache.CacheBuilderSpec;
import com.taskie.core.UserPrincipal;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.CachingAuthenticator;
import io.dropwizard.auth.SafeAuthDynamicFeature;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import javax.ws.rs.container.DynamicFeature;

public final class AuthConfiguration {

    private AuthConfiguration() {
        // utility constructor
    }

    public static void configure(Environment env) {
        env.jersey().register(buildAuthFilter(env.metrics()));
        env.jersey().register(RolesAllowedDynamicFeature.class);
        //If you want to use @Auth to inject a custom Principal type into your resource
        env.jersey().register(buildAuthBinder());
    }

    private static DynamicFeature buildAuthFilter(MetricRegistry metricRegistry) {

        CachingAuthenticator<BasicCredentials, UserPrincipal> cachingAuthenticator =
                new CachingAuthenticator<>(metricRegistry, new SimpleAuthenticator(),
                        CacheBuilderSpec.parse("maximumSize=10000, expireAfterAccess=10m"));

        return new SafeAuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<UserPrincipal>()
                        .setAuthenticator(cachingAuthenticator)
                        .setAuthorizer(new SimpleAuthorizer())
                        .buildAuthFilter());
    }

    private static AuthValueFactoryProvider.Binder buildAuthBinder() {
        return new AuthValueFactoryProvider.Binder<>(UserPrincipal.class);
    }
}
