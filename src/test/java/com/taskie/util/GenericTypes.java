package com.taskie.util;

import com.taskie.api.TaskInfo;

import javax.ws.rs.core.GenericType;
import java.util.List;

/**
 * Generic types util for resource testing
 */
public final class GenericTypes {

    private GenericTypes() {
        // utility constructor
    }

    public static GenericType<List<TaskInfo>> taskInfoList() {
        return new GenericType<List<TaskInfo>>() {
            // TaskInfo list type for testing
        };
    }
}
