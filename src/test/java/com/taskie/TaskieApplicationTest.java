package com.taskie;

import ca.grimoire.dropwizard.cors.CorsBundle;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.taskie.resources.HallOfFameResource;
import com.taskie.resources.LoginResource;
import com.taskie.resources.TaskResource;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TaskieApplicationTest {

    @SuppressWarnings("unchecked")
    @Test
    public void corsIsConfigured() {
        Bootstrap<TaskieConfiguration> bootstrap = mock(Bootstrap.class);
        new TaskieApplication().initialize(bootstrap);
        verify(bootstrap).addBundle(any(CorsBundle.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void swaggerIsConfigured() {
        Bootstrap<TaskieConfiguration> bootstrap = mock(Bootstrap.class);
        new TaskieApplication().initialize(bootstrap);
        verify(bootstrap).addBundle(any(SwaggerBundle.class));
    }

    @Test
    public void resourcesAreConfigured() {
        TaskieConfiguration config = mock(TaskieConfiguration.class);
        JerseyEnvironment jersey = mock(JerseyEnvironment.class);
        Environment env = mock(Environment.class);
        when(env.jersey()).thenReturn(jersey);
        when(env.metrics()).thenReturn(mock(MetricRegistry.class));
        when(env.healthChecks()).thenReturn(mock(HealthCheckRegistry.class));
        new TaskieApplication().run(config, env);
        verify(jersey).register(any(LoginResource.class));
        verify(jersey).register(any(HallOfFameResource.class));
        verify(jersey).register(any(TaskResource.class));
    }
}