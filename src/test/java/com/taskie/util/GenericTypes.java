package com.taskie.util;

import com.taskie.api.TaskInfo;
import com.taskie.api.User;

import javax.ws.rs.core.GenericType;
import java.util.List;

/**
 * Generic types util for resource testing
 */
public final class GenericTypes {

    private GenericTypes() {
        // utility constructor
    }

    public static GenericType<List<User>> userList() {
        return new GenericType<List<User>>() {
            // User list type for testing
        };
    }

    public static GenericType<List<TaskInfo>> taskInfoList() {
        return new GenericType<List<TaskInfo>>() {
            // TaskInfo list type for testing
        };
    }
}
