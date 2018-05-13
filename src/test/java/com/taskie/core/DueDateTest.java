package com.taskie.core;

import com.taskie.AbstractEntityTest;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.joda.time.DateTime;
import org.junit.Test;

public class DueDateTest extends AbstractEntityTest<DueDate> {

    public DueDateTest() {
        super(new DueDate(DateTime.now()));
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