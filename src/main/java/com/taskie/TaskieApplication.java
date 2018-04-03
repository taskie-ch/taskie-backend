package com.taskie;

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
    public void run(final TaskieConfiguration conf,
                    final Environment env) {

        env.jersey().register(new IllegalArgumentExceptionMapper(env.metrics()));

        final HelloWorldResource resource = new HelloWorldResource(conf.getTemplate(), conf.getDefaultName());

        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(conf.getTemplate());
        env.healthChecks().register("template", healthCheck);

        env.jersey().register(resource);

        TaskDao taskDao = new TaskDao();
        env.jersey().register(new TaskResource(taskDao));
        env.jersey().register(new TaskScheduleResource(taskDao));
    }

}
