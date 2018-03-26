package com.taskie;

import com.taskie.db.TaskDao;
import com.taskie.health.TemplateHealthCheck;
import com.taskie.resources.HelloWorldResource;
import com.taskie.resources.TaskResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

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
        // TODO: application initialization
    }

    @Override
    public void run(final TaskieConfiguration configuration,
                    final Environment environment) {


        final HelloWorldResource resource = new HelloWorldResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );

        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);

        environment.jersey().register(resource);
        environment.jersey().register(new TaskResource(new TaskDao()));
    }

}
