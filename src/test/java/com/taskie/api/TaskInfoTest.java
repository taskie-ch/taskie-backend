package com.taskie.api;

import com.taskie.util.TestData;

/**
 * Tests serialization of {@link TaskInfo} objects.
 */
public class TaskInfoTest extends AbstractSerializationTest<TaskInfo> {

    public TaskInfoTest() {
        super(TestData.TASK_INFO);
    }
}
