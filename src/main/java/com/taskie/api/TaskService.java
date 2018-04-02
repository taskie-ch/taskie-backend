package com.taskie.api;

import com.taskie.core.Task;

public interface TaskService {

    Task complete(long taskId);
}
