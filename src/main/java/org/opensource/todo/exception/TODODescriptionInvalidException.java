package org.opensource.todo.exception;

public class TODODescriptionInvalidException extends Exception{
    public TODODescriptionInvalidException(String desc) {
        super("Invalid new description, old description is: "+desc);
    }
}
