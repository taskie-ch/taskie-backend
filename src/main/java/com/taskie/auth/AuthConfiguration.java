package com.taskie.auth;

import com.google.common.cache.CacheBuilderSpec;
import com.taskie.api.FlatService;
import com.taskie.core.UserPrincipal;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.CachingAuthenticator;
import io.dropwizard.auth.SafeAuthDynamicFeature;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import javax.ws.rs.container.DynamicFeature;

/**
 * Configuration holder for service authentication.
 */
public final class AuthConfiguration {

    private AuthConfiguration() {
        // utility constructor
    }

    /**
     * Enable authentication.
     *
     * @param env environment container
     * @param flatService flat service providing user data
     */
    public static void configure(Environment env, FlatService flatService) {

        // enables caching for authentication
        CachingAuthenticator<BasicCredentials, UserPrincipal> cachingAuthenticator =
                new CachingAuthenticator<>(env.metrics(), new SimpleAuthenticator(flatService),
                        CacheBuilderSpec.parse("maximumSize=10000, expireAfterAccess=10m"));

        DynamicFeature authFilter = new SafeAuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<UserPrincipal>()
                        .setAuthenticator(cachingAuthenticator)
                        .setAuthorizer(new SimpleAuthorizer())
                        .buildAuthFilter());

        env.jersey().register(authFilter);
        env.jersey().register(RolesAllowedDynamicFeature.class);
        //If you want to use @Auth to inject a custom Principal type into your resource
        env.jersey().register(buildAuthBinder());
    }

    private static AuthValueFactoryProvider.Binder buildAuthBinder() {
        return new AuthValueFactoryProvider.Binder<>(UserPrincipal.class);
    }
}
