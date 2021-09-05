package org.opensource.todo.controller;

import lombok.extern.slf4j.Slf4j;
import org.opensource.todo.exception.*;
import org.opensource.todo.model.TODOEntity;
import org.opensource.todo.model.TodoTask;
import org.opensource.todo.service.TODOManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/todoservicev1")
public class TODOManagementController {

    private TODOManagementService todoManagementService;

    @Autowired
    public void setTodoManagementService(TODOManagementService todoManagementService) {
        this.todoManagementService = todoManagementService;
    }

    @Cacheable("todos")
    @GetMapping("/todos/list")
    ResponseEntity<List<TODOEntity>> getAllItemsOfStatus(@RequestParam Optional<String> status) throws InvalidTODOTaskStatusException {
        return new ResponseEntity<>(todoManagementService.findAllByStatus(status), HttpStatus.OK);
    }

    @GetMapping("/todos")
    ResponseEntity<TODOEntity> getTaskDetails(@RequestParam Long id) throws NoTODOTaskFoundException {
        return new ResponseEntity<>(todoManagementService.findByTaskId(id), HttpStatus.OK);
    }

    @PostMapping("/todos/add")
    ResponseEntity<String> addTodo(@RequestBody TodoTask todo) throws InvalidTODOTaskStatusException, TODOTaskMappingException {
        return new ResponseEntity<>(todoManagementService.addTodoItem(todo), HttpStatus.ACCEPTED);
    }

    @PostMapping("/todos/updateDesc")
    ResponseEntity<String> updateDesc(@RequestBody TodoTask todo) throws InvalidTODORequestException, TODODescriptionInvalidException, TODOPastDueException {
        return new ResponseEntity<>(todoManagementService.changeDesc(todo), HttpStatus.OK);
    }

    @PostMapping("/todos/updateStatus")
    ResponseEntity<String> updateStatus(@RequestBody TodoTask todo) throws InvalidTODOTaskStatusException, TODOPastDueException, InvalidTODORequestException {
        return new ResponseEntity<>(todoManagementService.changeStatus(todo), HttpStatus.OK);
    }
}
