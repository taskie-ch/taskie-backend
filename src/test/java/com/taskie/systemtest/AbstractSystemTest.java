package com.taskie.systemtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

abstract class AbstractSystemTest {

    static final Logger LOG = LoggerFactory.getLogger("IntegrationTest");

    private static final String BASE_PATH = System.getProperty("it.base.url", "http://localhost:9090");
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    static String path(String pathSuffix) {
        String fullPath = BASE_PATH + pathSuffix;
        LOG.info("Using path: {}", fullPath);
        return fullPath;
    }

    static <T> T readJson(Response response, Class<T> inObject) throws IOException {
        return MAPPER.readValue(response.asInputStream(), inObject);
    }
}
