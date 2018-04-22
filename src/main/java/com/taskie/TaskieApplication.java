package com.taskie;

import com.taskie.auth.AuthConfiguration;
import com.taskie.db.FlatDao;
import com.taskie.db.TaskDao;
import com.taskie.health.FlatServiceHealthCheck;
import com.taskie.health.TaskServiceHealthCheck;
import com.taskie.resources.TaskResource;
import com.taskie.resources.TaskScheduleResource;
import com.taskie.resources.error.IllegalArgumentExceptionMapper;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class TaskieApplication extends Application<TaskieConfiguration> {

    public static void main(final String[] args) throws Exception {
        new TaskieApplication().run(args);
    }

    @Override
    public void initialize(final Bootstrap<TaskieConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<TaskieConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(final TaskieConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

    @Override
    public void run(final TaskieConfiguration config,
                    final Environment env) {

        AuthConfiguration.configure(env);

        configureExceptionMappers(env);

        FlatDao flatDao = new FlatDao();
        TaskDao taskDao = new TaskDao(flatDao);

        env.healthChecks().register("flatService", new FlatServiceHealthCheck(flatDao));
        env.healthChecks().register("taskService", new TaskServiceHealthCheck(taskDao));

        env.jersey().register(new TaskResource(taskDao));
        env.jersey().register(new TaskScheduleResource(taskDao));
    }

    private static void configureExceptionMappers(Environment env) {
        env.jersey().register(new IllegalArgumentExceptionMapper(env.metrics()));
    }
}
