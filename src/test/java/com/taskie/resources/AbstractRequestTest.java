package com.taskie.resources;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.taskie.resources.error.IllegalArgumentExceptionMapper;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Rule;

import javax.ws.rs.client.Invocation;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests endpoints with HTTP requests
 */
public abstract class AbstractRequestTest {

    /**
     * Request path
     */
    private final String path;

    /**
     * Resource endpoint as rule
     */
    @Rule
    public final ResourceTestRule resources;

    /**
     * @param resources endpoint rule
     * @param path      request path
     */
    AbstractRequestTest(ResourceTestRule resources, String path) {
        this.resources = resources;
        this.path = path;
    }

    /**
     * @return response builder
     */
    Invocation.Builder request() {
        return resources.target(path).request();
    }

    /**
     * Builds a test rule from resource using own exception mappers.
     *
     * @param resource endpoint resource
     * @return endpoint rule
     */
    static ResourceTestRule resourceRule(Object resource) {

        MetricRegistry metricRegistry = mock(MetricRegistry.class);
        when(metricRegistry.meter(anyString())).thenReturn(mock(Meter.class));

        return ResourceTestRule.builder()
                .setRegisterDefaultExceptionMappers(false)
                .addResource(new IllegalArgumentExceptionMapper(metricRegistry))
                .addResource(resource)
                .build();
    }
}
