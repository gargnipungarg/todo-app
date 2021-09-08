package org.opensource.todo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.opensource.todo.constants.AppConstants;
import org.opensource.todo.constants.TaskStatuses;
import org.opensource.todo.mappers.TodoAddItemMapper;
import org.opensource.todo.respository.TODOManagementRepository;
import org.opensource.todo.utils.AppUtils;
import org.springframework.stereotype.Service;
import org.opensource.todo.exception.NoTODOTaskFoundException;
import org.opensource.todo.exception.TODOPastDueException;
import org.opensource.todo.exception.TODOTaskMappingException;
import org.opensource.todo.exception.TODODescriptionInvalidException;
import org.opensource.todo.exception.InvalidTODORequestException;
import org.opensource.todo.model.TodoAddItemRequest;
import org.opensource.todo.model.TodoUpdateDescRequest;
import org.opensource.todo.model.TODOEntity;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TODOManagementServiceImpl implements TODOManagementService{

    private final TODOManagementRepository todoManagementRepository;

    public TODOEntity findByTaskId(Long taskId) throws NoTODOTaskFoundException {
        return todoManagementRepository.findById(taskId).orElseThrow(() -> new NoTODOTaskFoundException(String.valueOf(taskId)));
    }

    public String addTodoItem(TodoAddItemRequest todo) throws TODOTaskMappingException, TODOPastDueException {
        try {
            if(AppUtils.isDateDue(todo.getDueDate())) {
                throw new TODOPastDueException(AppConstants.PAST_DUE_MSG);
            }
            log.info("Received request for adding todo item={}", todo);
            TODOEntity todoEntity = TodoAddItemMapper.INSTANCE.sourceToDestination(todo);
            todoEntity.setCreationDate(new Date());
            todoEntity.setStatus(TaskStatuses.NOT_DONE.getTaskStatus());
            todoManagementRepository.save(todoEntity);
            log.info("TODO item successfully saved.");
            return AppConstants.ITEM_CREATED_MSG + todoEntity.getId();
        } catch (TODOPastDueException ex) {
            throw new TODOPastDueException(ex.getMessage());
        } catch (Exception e) {
            throw new TODOTaskMappingException(e.getMessage());
        }
    }

    public String changeDesc(TodoUpdateDescRequest todoTask) throws TODODescriptionInvalidException, InvalidTODORequestException, TODOPastDueException {
        try {
            log.info("Todo task: "+todoTask);
            Long todoId = todoTask.getId();
            log.info("Querying for TODO id: "+todoId);
            TODOEntity item = todoManagementRepository.getById(todoId);
            if(AppUtils.isDateDue(item.getDueDate())){
                item.setStatus(TaskStatuses.PAST_DUE.getTaskStatus());              // If date is due by the time update request was triggered, update the status as Past Due and reject the incoming request.
                todoManagementRepository.save(item);
                throw new TODOPastDueException(AppConstants.PAST_DUE_MSG);
            }
            String description = todoTask.getDescription();
            if(StringUtils.isNotEmpty(description)) {
                item.setDescription(description);
            } else {
                throw new TODODescriptionInvalidException(item.getDescription());
            }
            log.info("Description updated, persisting item.");
            todoManagementRepository.save(item);
            return AppConstants.ITEM_UPDATED_MSG + todoId;
        } catch(javax.persistence.EntityNotFoundException ex) {
            throw new InvalidTODORequestException(AppConstants.INVALID_ID_MSG);
        }
    }

    public String changeStatus(Long todoId, String status) throws InvalidTODORequestException, TODOPastDueException {
        try {
            log.info("Querying for TODO id: "+todoId);
            if(todoId == 0) {
                throw new InvalidTODORequestException(AppConstants.ID_REQUIRED_MSG);
            }
            TODOEntity item = todoManagementRepository.getById(todoId);
            if(AppUtils.isDateDue(item.getDueDate())){
                item.setStatus(TaskStatuses.PAST_DUE.getTaskStatus());              // If date is due by the time update request was triggered, update the status as Past Due and reject the incoming request.
                todoManagementRepository.save(item);
                throw new TODOPastDueException(AppConstants.PAST_DUE_MSG);
            }
            log.info("Current status of item={}" + item.getStatus());
            item.setStatus(status);
            if(StringUtils.equals(status, TaskStatuses.DONE.getTaskStatus())) {
                // If status is getting done, mark current time as completion date
                item.setCompletionDate(new Date());
            } else {
                // If status is getting not done, clear the completion date from DB
                item.setCompletionDate(null);
            }
            log.info("Status updated, persisting item.");
            todoManagementRepository.save(item);
            return AppConstants.ITEM_UPDATED_MSG + todoId;
        } catch(javax.persistence.EntityNotFoundException ex) {
            throw new InvalidTODORequestException(AppConstants.INVALID_ID_MSG);
        }
    }

    public List<TODOEntity> findAllByStatus(Optional<Boolean> status) {
        List<TODOEntity> allItems = todoManagementRepository.findAll();
        if(status.isPresent() && status.get().equals(Boolean.TRUE)) {
            log.info("Filtering TODO items on criteria of not done status");
            return allItems.stream().filter(item -> StringUtils.equals(item.getStatus(), TaskStatuses.NOT_DONE.getTaskStatus())).collect(Collectors.toList());
        }
        return allItems;
    }
}