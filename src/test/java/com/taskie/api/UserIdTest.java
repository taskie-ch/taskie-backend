package com.taskie.api;

import com.taskie.util.TestData;

/**
 * Tests serialization of {@link UserId} objects.
 */
public class UserIdTest extends AbstractSerializationTest<UserId> {

    public UserIdTest() {
        super(TestData.userId());
    }
}

