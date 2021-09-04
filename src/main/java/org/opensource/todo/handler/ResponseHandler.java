package org.opensource.todo.handler;

import lombok.extern.slf4j.Slf4j;
import org.opensource.todo.exception.InvalidTODOTaskStatusException;
import org.opensource.todo.exception.NoTODOTaskFoundException;
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
        log.error(ex.getMessage());
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(InvalidTODOTaskStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String todoAddFailedHandler(InvalidTODOTaskStatusException ex) {
        log.error(ex.getMessage());
        return ex.getMessage();
    }

}
