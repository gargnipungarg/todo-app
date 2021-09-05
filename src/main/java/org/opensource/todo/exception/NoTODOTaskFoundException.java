package org.opensource.todo.exception;

public class NoTODOTaskFoundException extends Exception{
    private static final long serialVersionUID = 2591564985344581663L;
    public NoTODOTaskFoundException(String taskId) {
        super("No TODO item found with id: "+taskId);
    }
}
