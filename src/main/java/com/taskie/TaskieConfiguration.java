package com.taskie;

import ca.grimoire.dropwizard.cors.config.CrossOriginFilterFactory;
import ca.grimoire.dropwizard.cors.config.CrossOriginFilterFactoryHolder;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

/**
 * Main configuration for Taskie. Represents values configured in a YAML file.
 */
public class TaskieConfiguration extends Configuration implements CrossOriginFilterFactoryHolder {


    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;

    private CrossOriginFilterFactory cors = new CrossOriginFilterFactory();

    public void setCors(CrossOriginFilterFactory cors) {
        this.cors = cors;
    }

    @Override
    public CrossOriginFilterFactory getCors() {
        return cors;
    }
}