package com.taskie.api;

import com.taskie.util.TestData;

/**
 * Tests serialization of {@link TaskCreate} objects.
 */
public class TaskCreateTest extends AbstractSerializationTest<TaskCreate> {

    public TaskCreateTest() {
        super(TestData.TASK_CREATE);
    }
}