package org.opensource.todo.utils;

import lombok.extern.slf4j.Slf4j;
import org.opensource.todo.constants.AppConstants;
import org.opensource.todo.constants.TaskStatuses;
import java.util.Date;
import java.util.Locale;

@Slf4j
public class AppUtils {

    public static boolean isDateDue(Date dueDate) {
        return new Date().getTime() > dueDate.getTime();
    }

    public static String getTaskStatus(String status) {
        return TaskStatuses.valueOf(status.toUpperCase(Locale.getDefault()).replace(AppConstants.SPACE, AppConstants.UNDERSCORE)).getTaskStatus();
    }
}
