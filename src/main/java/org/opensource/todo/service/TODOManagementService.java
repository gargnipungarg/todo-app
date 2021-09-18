package org.opensource.todo.service;

import org.opensource.todo.exception.NoTODOTaskFoundException;
import org.opensource.todo.exception.TODOPastDueException;
import org.opensource.todo.exception.TODOTaskMappingException;
import org.opensource.todo.exception.TODODescriptionInvalidException;
import org.opensource.todo.exception.InvalidTODORequestException;
import org.opensource.todo.model.TodoAddItemRequest;
import org.opensource.todo.model.TodoUpdateDescRequest;
import org.opensource.todo.model.TODOEntity;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Optional;

public interface TODOManagementService {

    TODOEntity findByTaskId(Long taskId) throws NoTODOTaskFoundException;
    String addTodoItem(TodoAddItemRequest todo) throws TODOTaskMappingException, TODOPastDueException;
    String changeDesc(TodoUpdateDescRequest todoTask) throws TODODescriptionInvalidException, InvalidTODORequestException, TODOPastDueException;
    String changeStatus(Long id, String status) throws InvalidTODORequestException, TODOPastDueException;
    @Cacheable("todos")
    List<TODOEntity> findAllByStatus(Optional<Boolean> status);
}