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

    private TestData() {
        // utility constructor
    }

    private static final UserPrincipal USER_PRINCIPAL = new UserPrincipal("Joe");

    public static final Task TASK = Task.newBuilder()
            .setId(1)
            .setTitle("Some task")
            .setFrequency(Frequency.DAILY)
            .setEffort(Effort.LOW)
            .setStartDate(DateTime.now())
            .addOccurence(new TaskOccurence(DateTime.now(), new UserPrincipal("Joe")))
            .addOccurence(new TaskOccurence(DateTime.now().plusDays(1), new UserPrincipal("Jane")))
            .build();

    public static final TaskInfo TASK_INFO = new TaskInfo(1, "My Task", "Weekly",
            "2018-05-20T08:40:19.172Z", 1, false, Arrays.asList("Joe", "Jane"));

    public static final TaskCreate TASK_CREATE =
            new TaskCreate("My Task", Frequency.WEEKLY.toString(), "2018-05-20T08:40:19.172Z", 1);

    public static Id id() {
        return TASK.deriveId();
    }

    public static UserPrincipal userPrincipal() {
        return USER_PRINCIPAL;
    }
}
