package org.opensource.todo.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.opensource.todo.constants.TaskStatuses;
import org.opensource.todo.exception.InvalidTODOTaskStatusException;
import org.opensource.todo.exception.NoTODOTaskFoundException;
import org.opensource.todo.model.TODOEntity;
import org.opensource.todo.model.TodoTask;
import org.opensource.todo.respository.TODOManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class TODOManagementService {

    private TODOManagementRepository todoManagementRepository;

    @Autowired
    public void setTodoManagementRepository(TODOManagementRepository todoManagementRepository) {
        this.todoManagementRepository = todoManagementRepository;
    }

    public TODOEntity findByTaskId(Long taskId) throws NoTODOTaskFoundException {
        Optional<TODOEntity> todoTask = todoManagementRepository.findById(taskId);
        if(todoTask.isPresent()) {
            return todoTask.get();
        } else {
            throw new NoTODOTaskFoundException(taskId.toString());
        }
    }

    public String addTodoItem(TodoTask todo) throws InvalidTODOTaskStatusException {
        String status = todo.getStatus();
        try {
            String validStatus = TaskStatuses.valueOf(status).getTaskStatus();
            log.info("$$$$$$$$$$$$$$$"+validStatus);
            TODOEntity todoEntity = new TODOEntity();
            todoEntity.setDescription(todo.getDescription());
            todoEntity.setCreationDate(todo.getCreationDate());
            todoEntity.setDueDate(todo.getDueDate());
            todoEntity.setCompletionDate(todo.getCompletionDate());
            todoManagementRepository.save(todoEntity);
            return HttpStatus.ACCEPTED.toString();
        } catch (IllegalArgumentException ex) {
            throw new InvalidTODOTaskStatusException(status);
        }
    }
}
