package com.taskie.util;

import com.taskie.api.Id;
import com.taskie.api.TaskCreate;
import com.taskie.api.TaskInfo;
import com.taskie.api.UserId;
import com.taskie.core.*;
import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.Collections;

/**
 * Test data util for convenience
 */
public final class TestData {


    private static final UserPrincipal USER_PRINCIPAL = new UserPrincipal("id_joe", "Joe");

    private static final UserId USER_ID = new UserId("id_joe");

    private static final Flatmate FLATMATE = Flatmate.create(USER_PRINCIPAL,
            new Email("a@b.c"),
            new Score(1));

    public static final Task TASK = Task.newBuilder()
            .setId(1)
            .setTitle("My Task")
            .setFrequency(Frequency.WEEKLY)
            .setEffort(Effort.LOW)
            .setStartDate(DateTime.now())
            .build();

    public static final TaskInfo TASK_INFO = new TaskInfo(TASK.getId(), TASK.getTitle(), TASK.getFrequency().name(),
            "2018-05-20T08:40:19.172Z", TASK.getEffort().intValue(), Arrays.asList("id_joe", "id_jane"));

    private static final TaskCreate TASK_CREATE = new TaskCreate(TASK.getTitle(), TASK.getFrequency().name(),
            "2018-05-20T08:40:19.172Z", TASK.getEffort().intValue(), Collections.singletonList(USER_PRINCIPAL.getId()));

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

    public static Flatmate flatmate() {
        return FLATMATE;
    }

    public static UserId userId() {
        return USER_ID;
    }

    public static UserPrincipal userPrincipal() {
        return USER_PRINCIPAL;
    }
}
