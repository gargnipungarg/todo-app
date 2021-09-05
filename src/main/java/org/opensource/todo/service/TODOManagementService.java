package org.opensource.todo.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.opensource.todo.constants.AppConstants;
import org.opensource.todo.constants.TaskStatuses;
import org.opensource.todo.exception.*;
import org.opensource.todo.mappers.TodoMapper;
import org.opensource.todo.model.TODOEntity;
import org.opensource.todo.model.TodoTask;
import org.opensource.todo.respository.TODOManagementRepository;
import org.opensource.todo.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public String addTodoItem(TodoTask todo) throws InvalidTODOTaskStatusException, TODOTaskMappingException {
        String status = todo.getStatus();
        try {
            String validStatus = TaskStatuses.valueOf(status.toUpperCase().replace(AppConstants.SPACE, AppConstants.UNDERSCORE)).getTaskStatus();
            log.info("Valid status found, persisting item with status : "+validStatus);
            todo.setStatus(validStatus);
            TODOEntity todoEntity = TodoMapper.INSTANCE.sourceToDestination(todo);
            if(AppUtils.validatePastDueStatus(todoEntity)){
                todoEntity.setStatus(TaskStatuses.PAST_DUE.getTaskStatus());
            }
            todoManagementRepository.save(todoEntity);
            return AppConstants.ITEM_CREATED_MSG + todoEntity.getId();
        } catch (IllegalArgumentException ex) {
            throw new InvalidTODOTaskStatusException(status);
        } catch (Exception e) {
            throw new TODOTaskMappingException(e.getMessage());
        }
    }

    public String changeDesc(TodoTask todoTask) throws TODODescriptionInvalidException, InvalidTODORequestException, TODOPastDueException {
        try {
            log.info("Todo task: "+todoTask);
            Long todoId = Long.valueOf(todoTask.getId());
            log.info("Querying for TODO id: "+todoId);
            if(todoId == 0) {
                throw new InvalidTODORequestException(AppConstants.ID_REQUIRED_MSG);
            }
            TODOEntity item = todoManagementRepository.getById(todoId);
            if(AppUtils.validatePastDueStatus(item)){
                item.setStatus(TaskStatuses.PAST_DUE.getTaskStatus());
                todoManagementRepository.save(item);
                throw new TODOPastDueException(AppConstants.PAST_DUE_MSG);
            }
            String description = todoTask.getDescription();
            if(StringUtils.isNotEmpty(description) && !StringUtils.equals(description, item.getDescription())) {
                item.setDescription(description);
            } else {
                throw new TODODescriptionInvalidException(item.getDescription());
            }
            log.info("Description updated, persisting item.");
            todoManagementRepository.save(item);
            return AppConstants.ITEM_UPDATED_MSG + todoId;
        } catch(NumberFormatException ex) {
            throw new InvalidTODORequestException(AppConstants.INVALID_ID_MSG);
        }
    }

    public String changeStatus(TodoTask todoTask) throws InvalidTODORequestException, InvalidTODOTaskStatusException, TODOPastDueException {
        try {
            log.info("Todo task: "+todoTask);
            Long todoId = Long.valueOf(todoTask.getId());
            log.info("Querying for TODO id: "+todoId);
            if(todoId == 0) {
                throw new InvalidTODORequestException(AppConstants.ID_REQUIRED_MSG);
            }
            TODOEntity item = todoManagementRepository.getById(todoId);
            if(AppUtils.validatePastDueStatus(item)){
                item.setStatus(TaskStatuses.PAST_DUE.getTaskStatus());
                todoManagementRepository.save(item);
                throw new TODOPastDueException(AppConstants.PAST_DUE_MSG);
            }
            String status = todoTask.getStatus();
            String validStatus = TaskStatuses.valueOf(status.toUpperCase().replace(AppConstants.SPACE, AppConstants.UNDERSCORE)).getTaskStatus();
            log.info("Valid status found, persisting item with status : "+validStatus);
            item.setStatus(validStatus);
            if(StringUtils.equals(validStatus, TaskStatuses.DONE.getTaskStatus())) {
                // If status is getting done, mark current time as completion date
                item.setCompletionDate(new Date());
            } else {
                // If status is getting not done, clear the completion date from DB
                item.setCompletionDate(null);
            }
            log.info("Status updated, persisting item.");
            todoManagementRepository.save(item);
            return AppConstants.ITEM_UPDATED_MSG + todoId;
        } catch(NumberFormatException ex) {
            throw new InvalidTODORequestException(AppConstants.INVALID_ID_MSG);
        } catch (IllegalArgumentException ex) {
            throw new InvalidTODOTaskStatusException(todoTask.getStatus());
        }
    }

    public List<TODOEntity> findAllByStatus(String status) throws InvalidTODOTaskStatusException {
        List<TODOEntity> allItems = todoManagementRepository.findAll();
        if(StringUtils.isNotEmpty(status)) {
            log.info("Filtering TODO items on criteria of status: "+ status);
            try {
                String validStatus = TaskStatuses.valueOf(status.toUpperCase().replace(AppConstants.SPACE, AppConstants.UNDERSCORE)).getTaskStatus();
                log.info("Valid status found, persisting item with status : " + validStatus);
                allItems = allItems.stream().filter(item -> StringUtils.equals(item.getStatus(), validStatus)).collect(Collectors.toList());
            } catch (IllegalArgumentException ex) {
                throw new InvalidTODOTaskStatusException(status);
            }
        }
        return allItems;
    }
}