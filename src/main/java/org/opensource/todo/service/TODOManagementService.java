package org.opensource.todo.service;

import org.opensource.todo.exception.*;
import org.opensource.todo.model.TODOEntity;
import org.opensource.todo.model.TodoTask;
import java.util.List;
import java.util.Optional;

public interface TODOManagementService {

    TODOEntity findByTaskId(Long taskId) throws NoTODOTaskFoundException;
    String addTodoItem(TodoTask todo) throws InvalidTODOTaskStatusException, TODOTaskMappingException;
    String changeDesc(TodoTask todoTask) throws TODODescriptionInvalidException, InvalidTODORequestException, TODOPastDueException;
    String changeStatus(TodoTask todoTask) throws InvalidTODORequestException, InvalidTODOTaskStatusException, TODOPastDueException;
    List<TODOEntity> findAllByStatus(Optional<String> status) throws InvalidTODOTaskStatusException;
}