package com.taskie.api;

import com.taskie.util.TestData;

/**
 * Tests serialization of {@link TaskInfo} objects.
 */
public class IdTest extends AbstractSerializationTest<Id> {

    public IdTest() {
        super(TestData.id());
    }
}
