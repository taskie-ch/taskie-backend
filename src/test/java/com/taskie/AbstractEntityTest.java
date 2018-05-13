package com.taskie;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractEntityTest<T> {

    protected final T testEntity;
    protected final Class<?> testEntityClass;

    public AbstractEntityTest(T testEntity) {
        this.testEntity = requireNonNull(testEntity, "Object is required");
        this.testEntityClass = testEntity.getClass();
    }

    /**
     * Verifies that the equals and hashCode contract holds.
     */
    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(testEntityClass).verify();
    }

    /**
     * Verifies that all non-static fields are exposed.
     */
    @Test
    public void anyToString() {
        String entityString = testEntity.toString();
        for (Field field : testEntityClass.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) {
                assertThat(entityString).contains(field.getName());
            }
        }
    }
}
