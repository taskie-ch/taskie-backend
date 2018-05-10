package com.taskie.resources;

public class ResourcePath {

    static final String FLAT_ID = "flatId";
    static final String TASK_ID = "taskId";

    private static final String FLAT_ID_PARAM = "/{" + FLAT_ID + "}";

    static final String FLATS = "/flats";
    static final String LOGIN = FLAT_ID_PARAM + "/auth";
    static final String TASKS = FLAT_ID_PARAM + "/tasks";
    static final String TASK = TASKS + "/{" + TASK_ID + "}";
    static final String TASK_COMPLETE = TASK + "/complete";
    static final String TASK_UNCOMPLETE = TASK + "/uncomplete";
}
