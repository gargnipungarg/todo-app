package org.opensource.todo.exception;

public class TODODescriptionInvalidException extends Exception{
    private static final long serialVersionUID = 266511023483841233L;
    public TODODescriptionInvalidException(String desc) {
        super("Invalid new description, old description is: "+desc);
    }
}
