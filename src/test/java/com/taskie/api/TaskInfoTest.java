package com.taskie.api;

import com.taskie.util.TestData;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests serialization of {@link TaskInfo} objects.
 */
public class TaskInfoTest extends AbstractSerializationTest<TaskInfo> {

    public TaskInfoTest() {
        super(TestData.taskInfo());
    }

    @Test
    public void deriveTaskCreate() {
        TaskCreate create = testEntity.deriveTaskCreate();
        assertThat(create.getTitle()).isEqualTo(testEntity.getTitle());
        assertThat(create.getEffort()).isEqualTo(testEntity.getEffort());
        assertThat(create.getFrequency()).isEqualTo(testEntity.getFrequency());
        assertThat(create.getStart()).isEqualTo(testEntity.getStart());
        assertThat(create.getUserIds()).containsAll(testEntity.getUserIds());
    }
}
