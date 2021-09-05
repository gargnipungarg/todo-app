package org.opensource.todo.exception;

public class InvalidTODOTaskStatusException extends Exception{
    private static final long serialVersionUID = 8137571652479439524L;
    public InvalidTODOTaskStatusException(String taskStatus) {
        super("Task status is not valid: "+taskStatus);
    }
}
