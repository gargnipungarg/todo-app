package org.opensource.todo.exception;

public class TODOTaskMappingException extends Exception{
    private static final long serialVersionUID = -5972698945699902402L;
    public TODOTaskMappingException(String errorMsg) {
        super("Invalid request with message: "+errorMsg);
    }
}
