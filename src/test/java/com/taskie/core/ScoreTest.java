package com.taskie.core;

import com.taskie.AbstractEntityTest;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class ScoreTest extends AbstractEntityTest<Score> {

    public ScoreTest() {
        super(new Score(5));
    }

    /**
     * Verifies that the equals and hashCode contract holds.
     * <p>
     * Entity uses a mutable field.
     */
    @Test
    @Override
    public void equalsContract() {
        EqualsVerifier.forClass(testEntityClass)
                .suppress(Warning.NONFINAL_FIELDS).verify();
    }
}