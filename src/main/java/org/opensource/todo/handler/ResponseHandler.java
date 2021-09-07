package org.opensource.todo.handler;

import lombok.extern.slf4j.Slf4j;
import org.opensource.todo.exception.NoTODOTaskFoundException;
import org.opensource.todo.exception.TODOPastDueException;
import org.opensource.todo.exception.TODOTaskMappingException;
import org.opensource.todo.exception.TODODescriptionInvalidException;
import org.opensource.todo.exception.InvalidTODORequestException;
import org.opensource.todo.exception.InvalidTODOTaskStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class ResponseHandler {

    @ResponseBody
    @ExceptionHandler(NoTODOTaskFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String todoTaskNotFoundHandler(NoTODOTaskFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(TODOTaskMappingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String todoMappingFailedHandler(TODOTaskMappingException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler({InvalidTODORequestException.class, TODODescriptionInvalidException.class, InvalidTODOTaskStatusException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String todoInvalidRequestHandler(Exception ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(TODOPastDueException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String todoMappingFailedHandler(TODOPastDueException ex) {
        return ex.getMessage();
    }

}
