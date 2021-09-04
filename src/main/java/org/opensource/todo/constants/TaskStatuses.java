package org.opensource.todo.constants;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
/*

@Component
public class TaskStatuses {

    private static final String NOT_DONE = "NOT DONE";
    private static final String DONE = "DONE";
    private static final String PAST_DUE = "PAST DUE";
    private static final Set<String> statuses = new HashSet<>();

    private void initMap() {
        statuses.add(NOT_DONE);
        statuses.add(DONE);
        statuses.add(PAST_DUE);
    }

    public TaskStatuses() {
        this.initMap();
    }

    public boolean validateTaskStatus (final String status) {
        return statuses.contains(status);
    }

}
*/



public enum TaskStatuses {

    NOT_DONE("NOT DONE"),
    DONE("DONE"),
    PAST_DUE("PAST DUE");

    private String taskStatus;

    TaskStatuses(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskStatus() { return taskStatus; }

    @Override public String toString() { return taskStatus; }
}