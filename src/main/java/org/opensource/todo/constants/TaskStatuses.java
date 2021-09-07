package org.opensource.todo.constants;


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