package com.taskie;

import com.taskie.auth.AuthConfiguration;
import com.taskie.db.TaskDao;
import com.taskie.health.TemplateHealthCheck;
import com.taskie.resources.HelloWorldResource;
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
    public String getName() {
        return "Taskie Server";
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
        configureHelloWorld(config, env);


        TaskDao taskDao = new TaskDao();
        env.jersey().register(new TaskResource(taskDao));
        env.jersey().register(new TaskScheduleResource(taskDao));
    }

    private static void configureExceptionMappers(Environment env) {
        env.jersey().register(new IllegalArgumentExceptionMapper(env.metrics()));
    }

    private static void configureHelloWorld(TaskieConfiguration config,
                                            Environment env) {

        final HelloWorldResource resource = new HelloWorldResource(config.getTemplate(), config.getDefaultName());

        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(config.getTemplate());
        env.healthChecks().register("template", healthCheck);

        env.jersey().register(resource);

    }
}
