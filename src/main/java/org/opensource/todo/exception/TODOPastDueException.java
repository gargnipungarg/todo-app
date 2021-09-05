package org.opensource.todo.exception;

public class TODOPastDueException extends Exception{
    private static final long serialVersionUID = 5920614104724859145L;
    public TODOPastDueException(String message) {
        super(message);
    }
}
