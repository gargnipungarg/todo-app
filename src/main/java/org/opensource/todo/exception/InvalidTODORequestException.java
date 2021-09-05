package org.opensource.todo.exception;

public class InvalidTODORequestException extends Exception {
    private static final long serialVersionUID = -3943122579759210863L;
    public InvalidTODORequestException(String message) {
        super(message);
    }
}
