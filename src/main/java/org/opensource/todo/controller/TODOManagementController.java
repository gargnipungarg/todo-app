package org.opensource.todo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.opensource.todo.constants.TaskStatuses;
import org.opensource.todo.exception.NoTODOTaskFoundException;
import org.opensource.todo.exception.TODOPastDueException;
import org.opensource.todo.exception.TODOTaskMappingException;
import org.opensource.todo.exception.TODODescriptionInvalidException;
import org.opensource.todo.exception.InvalidTODORequestException;
import org.opensource.todo.model.TodoAddItemRequest;
import org.opensource.todo.model.TodoUpdateDescRequest;
import org.opensource.todo.model.TODOEntity;
import org.opensource.todo.service.TODOManagementService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/todoservicev1/todos")
@RequiredArgsConstructor
public class TODOManagementController {

    private final TODOManagementService todoManagementService;

    @Cacheable("todos")
    @GetMapping("/list")
    public ResponseEntity<List<TODOEntity>> getAllItemsOfStatus(@RequestParam Optional<Boolean> notDone) {
        return new ResponseEntity<>(todoManagementService.findAllByStatus(notDone), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<TODOEntity> getTaskDetails(@RequestParam Long id) throws NoTODOTaskFoundException {
        return new ResponseEntity<>(todoManagementService.findByTaskId(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addTodo(@Valid @RequestBody TodoAddItemRequest todoItem) throws TODOTaskMappingException, TODOPastDueException {
        return new ResponseEntity<>(todoManagementService.addTodoItem(todoItem), HttpStatus.ACCEPTED);
    }

    @PostMapping("/updateDesc")
    public ResponseEntity<String> updateDesc(@Valid @RequestBody TodoUpdateDescRequest todoItem) throws InvalidTODORequestException, TODODescriptionInvalidException, TODOPastDueException {
        return new ResponseEntity<>(todoManagementService.changeDesc(todoItem), HttpStatus.OK);
    }

    @PostMapping("/markDone")
    public ResponseEntity<String> updateDoneStatus(@RequestParam Long id) throws TODOPastDueException, InvalidTODORequestException {
        return new ResponseEntity<>(todoManagementService.changeStatus(id, TaskStatuses.DONE.getTaskStatus()), HttpStatus.OK);
    }

    @PostMapping("/markNotDone")
    public ResponseEntity<String> updateNotDoneStatus(@RequestParam Long id) throws TODOPastDueException, InvalidTODORequestException {
        return new ResponseEntity<>(todoManagementService.changeStatus(id, TaskStatuses.NOT_DONE.getTaskStatus()), HttpStatus.OK);
    }
}
