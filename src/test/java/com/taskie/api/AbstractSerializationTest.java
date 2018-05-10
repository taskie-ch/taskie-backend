package com.taskie.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import java.io.IOException;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests serialization and deserialization of API objects.
 * <p>
 * <b>Prerequisites:</b> Requires a json file named after the object type in src/test/resources/fixtures
 *
 * @param <T> object type
 */
abstract class AbstractSerializationTest<T> {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    private final T object;
    private final Class<?> objectClass;
    private final String fixture;

    /**
     * @param object API object under test
     */
    AbstractSerializationTest(T object) {
        this.object = requireNonNull(object, "Object is required");
        this.objectClass = object.getClass();
        this.fixture = fixture("fixtures/" + objectClass.getSimpleName() + ".json");
    }

    /**
     * Serialize {@code object} and compares against json fixture
     */
    @Test
    public void serializeToJSON() throws IOException {
        assertThat(MAPPER.writeValueAsString(object))
                .isEqualTo(MAPPER.writeValueAsString(MAPPER.readValue(fixture, objectClass)));
    }

    /**
     * Deserialize json fixture and compare against {@code object}
     */
    @Test
    public void deserializeFromJSON() throws IOException {
        assertThat(MAPPER.readValue(fixture, objectClass))
                .isEqualTo(object);
    }
}
