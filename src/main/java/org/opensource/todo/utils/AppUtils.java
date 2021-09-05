package org.opensource.todo.utils;

import org.apache.commons.lang3.StringUtils;
import org.opensource.todo.constants.TaskStatuses;

public class AppUtils {

    public static boolean isDue(String status) {
        return StringUtils.equals(TaskStatuses.PAST_DUE.getTaskStatus(), status);
    }
}
