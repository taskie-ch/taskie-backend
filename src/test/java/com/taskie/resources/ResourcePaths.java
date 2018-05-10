package com.taskie.resources;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class ResourcePaths {

    private ResourcePaths() {
        // utility constructor
    }


    static String withBaseAndIds(String path, long flatId, long taskId) {
        return ResourcePath.FLATS + withIds(path, flatId, taskId);
    }

    static String withBaseAndFlatId(String path, long flatId) {
        return ResourcePath.FLATS + withFlatId(path, flatId);
    }

    private static String withIds(String path, long flatId, long taskId) {
        return withTaskId(withFlatId(path, flatId), taskId);
    }

    private static String withTaskId(String path, long id) {
        return path.replace("{" + ResourcePath.TASK_ID + "}", String.valueOf(id));
    }

    private static String withFlatId(String path, long id) {
        return path.replace("{" + ResourcePath.FLAT_ID + "}", String.valueOf(id));
    }

    public static class ResourcePathsTest {

        @Test
        public void pathWithFlatId() {
            assertThat(withBaseAndFlatId(ResourcePath.LOGIN, 1)).isEqualTo("/flats/1/auth");
        }
    }
}
