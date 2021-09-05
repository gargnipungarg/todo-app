package org.opensource.todo.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.opensource.todo.constants.AppConstants;
import org.opensource.todo.constants.TaskStatuses;
import org.opensource.todo.model.TODOEntity;
import java.util.Date;
import java.util.Locale;

@Slf4j
public class AppUtils {

    public static boolean isStatusDue(String status) {
        return StringUtils.equals(TaskStatuses.PAST_DUE.getTaskStatus(), status);
    }

    public static boolean isDateDue(Date dueDate) {
        return new Date().getTime() > dueDate.getTime();
    }

    public static boolean validatePastDueStatus(TODOEntity todoEntity) {
        if(isDateDue(todoEntity.getDueDate()) || isStatusDue(todoEntity.getStatus())) {
            log.info("Item is found to be due, updating status");
            return true;
        }
        return false;
    }

    public static String getTaskStatus(String status) {
        return TaskStatuses.valueOf(status.toUpperCase(Locale.getDefault()).replace(AppConstants.SPACE, AppConstants.UNDERSCORE)).getTaskStatus();
    }
}
