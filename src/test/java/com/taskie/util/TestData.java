package com.taskie.util;

import com.taskie.api.Id;
import com.taskie.api.TaskCreate;
import com.taskie.api.TaskInfo;
import com.taskie.core.*;
import org.joda.time.DateTime;

import java.util.Arrays;

/**
 * Test data util for convenience
 */
public final class TestData {


    private static final UserPrincipal USER_PRINCIPAL = new UserPrincipal("id_joe", "Joe");
    public static final Task TASK = Task.newBuilder()
            .setId(1)
            .setTitle("My Task")
            .setFrequency(Frequency.WEEKLY)
            .setEffort(Effort.LOW)
            .setStartDate(DateTime.now())
            .addOccurence(new TaskOccurence(DateTime.now(), USER_PRINCIPAL))
            .addOccurence(new TaskOccurence(DateTime.now().plusDays(1), new UserPrincipal("id_jane", "Jane")))
            .build();

    public static final TaskInfo TASK_INFO = new TaskInfo(TASK.getId(), TASK.getTitle(), TASK.getFrequency().name(),
            "2018-05-20T08:40:19.172Z", TASK.getEffort().getValue(), Arrays.asList("id_joe", "id_jane"));

    private static final TaskCreate TASK_CREATE = new TaskCreate(TASK.getTitle(), TASK.getFrequency().name(),
            "2018-05-20T08:40:19.172Z", TASK.getEffort().getValue());

    private TestData() {
        // utility constructor
    }

    public static Id id() {
        return TASK.deriveId();
    }

    public static TaskInfo taskInfo() {
        return TASK_INFO;
    }

    public static TaskCreate taskCreate() {
        return TASK_CREATE;
    }

    public static UserPrincipal userPrincipal() {
        return USER_PRINCIPAL;
    }
}
