package io.dropwizard.auth;

import org.glassfish.jersey.server.model.AnnotatedMethod;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import java.lang.annotation.Annotation;
import java.util.Optional;

/**
 * Copy of {@link AuthDynamicFeature} that fixes a serialization issue resulting in warnings.
 *
 * @see AuthDynamicFeature
 */
public class SafeAuthDynamicFeature implements DynamicFeature {

    private final ContainerRequestFilter authFilter;

    public SafeAuthDynamicFeature(ContainerRequestFilter authFilter) {
        this.authFilter = authFilter;
    }

    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext context) {
        final AnnotatedMethod am = new AnnotatedMethod(resourceInfo.getResourceMethod());
        final Annotation[][] parameterAnnotations = am.getParameterAnnotations();
        final Class<?>[] parameterTypes = am.getParameterTypes();

        // First, check for any @Auth annotations on the method.
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (final Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof Auth) {
                    // Optional auth requires that a concrete AuthFilter be provided.
                    if (parameterTypes[i].equals(Optional.class) && authFilter != null) {
                        context.register(new WebApplicationExceptionCatchingFilter(authFilter));
                        return;
                    } else {
                        registerAuthFilter(context);
                        return;
                    }
                }
            }
        }

        // Second, check for any authorization annotations on the class or method.
        // Note that @DenyAll shouldn't be attached to classes.
        final boolean annotationOnClass = (resourceInfo.getResourceClass().getAnnotation(RolesAllowed.class) != null) ||
                (resourceInfo.getResourceClass().getAnnotation(PermitAll.class) != null);
        final boolean annotationOnMethod = am.isAnnotationPresent(RolesAllowed.class) || am.isAnnotationPresent(DenyAll.class) ||
                am.isAnnotationPresent(PermitAll.class);

        if (annotationOnClass || annotationOnMethod) {
            registerAuthFilter(context);
        }
    }

    private void registerAuthFilter(FeatureContext context) {
        if (authFilter != null) {
            context.register(authFilter);
        }
    }
}