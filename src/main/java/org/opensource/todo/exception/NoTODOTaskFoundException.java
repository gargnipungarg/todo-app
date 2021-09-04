package org.opensource.todo.exception;

public class NoTODOTaskFoundException extends Exception{
    public NoTODOTaskFoundException(String taskId) {
        super("No TODO item found with id: "+taskId);
    }
}
