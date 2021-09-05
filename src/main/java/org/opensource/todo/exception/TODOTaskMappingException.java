package org.opensource.todo.exception;

public class TODOTaskMappingException extends Exception{
    public TODOTaskMappingException(String errorMsg) {
        super("Invalid request with message: "+errorMsg);
    }
}
