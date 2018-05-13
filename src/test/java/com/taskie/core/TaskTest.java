package com.taskie.core;

import com.taskie.AbstractEntityTest;
import com.taskie.util.TestData;

public class TaskTest extends AbstractEntityTest<Task> {

    public TaskTest() {
        super(TestData.task());
    }
}