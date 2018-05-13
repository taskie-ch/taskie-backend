package com.taskie.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskie.AbstractEntityTest;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import java.io.IOException;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests serialization and deserialization of API objects.
 * <p>
 * <b>Prerequisites:</b> Requires a json file named after the object type in src/test/resources/fixtures
 *
 * @param <T> object type
 */
abstract class AbstractSerializationTest<T> extends AbstractEntityTest<T> {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    private final String fixture;

    /**
     * @param object API object under test
     */
    AbstractSerializationTest(T object) {
        super(object);
        this.fixture = fixture("fixtures/" + testEntityClass.getSimpleName() + ".json");
    }

    /**
     * Serialize {@code object} and compares against json fixture
     */
    @Test
    public void serializeToJSON() throws IOException {
        assertThat(MAPPER.writeValueAsString(testEntity))
                .isEqualTo(MAPPER.writeValueAsString(MAPPER.readValue(fixture, testEntityClass)));
    }

    /**
     * Deserialize json fixture and compare against {@code object}
     */
    @Test
    public void deserializeFromJSON() throws IOException {
        assertThat(MAPPER.readValue(fixture, testEntityClass))
                .isEqualTo(testEntity);
    }
}
