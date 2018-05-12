package com.taskie.api;

import com.taskie.util.TestData;

/**
 * Tests serialization of {@link User} objects.
 */
public class UserTest extends AbstractSerializationTest<User> {

    public UserTest() {
        super(TestData.flatmate().deriveUser());
    }
}
