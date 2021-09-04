package org.opensource.todo.exception;

public class InvalidTODOTaskStatusException extends Exception{
    public InvalidTODOTaskStatusException(String taskStatus) {
        super("Task status is not valid: "+taskStatus);
    }
}
