package com.taskie.resources.error;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import io.dropwizard.jersey.errors.ErrorMessage;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import static com.codahale.metrics.MetricRegistry.name;

/**
 * Exception mapper that is used in resources to generate HTTP responses that are sent to the client
 * if an {@link IllegalArgumentException} is thrown during the request process.
 */
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

    /**
     * Metric element for exceptions of type {@link IllegalArgumentException}
     */
    private final Meter exceptions;

    /**
     * @param metrics registry for collecting exception metrics
     */
    public IllegalArgumentExceptionMapper(MetricRegistry metrics) {
        exceptions = metrics.meter(name(getClass(), "exceptions"));
    }

    /**
     * Maps an {@link IllegalArgumentException} to a useful HTTP response.
     * Returns status code 400 'Bad Request' in JSON including the message of the exception.
     *
     * @param e exception to map
     * @return HTTP response
     */
    @Override
    public Response toResponse(IllegalArgumentException e) {
        exceptions.mark();
        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new ErrorMessage(Response.Status.BAD_REQUEST.getStatusCode(), e.getMessage()))
                .build();
    }
}
