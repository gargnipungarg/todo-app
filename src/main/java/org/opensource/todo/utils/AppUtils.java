package org.opensource.todo.utils;

import java.util.Date;

public class AppUtils {
    public static boolean isDateDue(Date dueDate) {
        return new Date().getTime() > dueDate.getTime();
    }
}
