package com.taskie;

import ca.grimoire.dropwizard.cors.CorsBundle;
import com.taskie.api.FlatService;
import com.taskie.api.TaskService;
import com.taskie.auth.AuthConfiguration;
import com.taskie.db.InMemoryFlatDao;
import com.taskie.db.InMemoryTaskDao;
import com.taskie.health.FlatServiceHealthCheck;
import com.taskie.health.TaskServiceHealthCheck;
import com.taskie.resources.HallOfFameResource;
import com.taskie.resources.LoginResource;
import com.taskie.resources.TaskResource;
import com.taskie.resources.error.IllegalArgumentExceptionMapper;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Taskie application main, wiring together the environment.
 */
public class TaskieApplication extends Application<TaskieConfiguration> {

    private static final Logger LOG = LoggerFactory.getLogger(TaskieApplication.class);

    /**
     * Main entry point. Requires command line arguments
     * e.g. {"server", "config.yml"} to start a server.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        try {
            new TaskieApplication().run(args);
        } catch (Exception exception) {
            LOG.error("Exception running Taskie", exception);
        }
    }

    /**
     * Registers configuration bundles for the application.
     *
     * @param bootstrap application bootstrap
     */
    @Override
    public void initialize(final Bootstrap<TaskieConfiguration> bootstrap) {
        bootstrap.addBundle(new CorsBundle<>());
        bootstrap.addBundle(new SwaggerBundle<TaskieConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(final TaskieConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

    /**
     * Registers and initialises resources and health checks.
     *
     * @param config taskie configuration
     * @param env    environment container
     */
    @Override
    public void run(final TaskieConfiguration config,
                    final Environment env) {

        AuthConfiguration.configure(env);

        configureExceptionMappers(env);

        FlatService flatService = InMemoryFlatDao.create();
        TaskService taskService = InMemoryTaskDao.create(flatService);

        env.healthChecks().register("flatService", new FlatServiceHealthCheck(flatService));
        env.healthChecks().register("taskService", new TaskServiceHealthCheck(taskService));

        env.jersey().register(new TaskResource(taskService));
        env.jersey().register(new HallOfFameResource(flatService));
        env.jersey().register(new LoginResource(flatService));
    }

    private static void configureExceptionMappers(Environment env) {
        env.jersey().register(new IllegalArgumentExceptionMapper(env.metrics()));
    }
}
