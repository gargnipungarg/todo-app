package org.opensource.todo.controller;

import lombok.extern.slf4j.Slf4j;
import org.opensource.todo.exception.InvalidTODOTaskStatusException;
import org.opensource.todo.exception.NoTODOTaskFoundException;
import org.opensource.todo.exception.TODOTaskMappingException;
import org.opensource.todo.model.TODOEntity;
import org.opensource.todo.model.TodoTask;
import org.opensource.todo.service.TODOManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/todoservicev1")
public class TODOManagementController {

    private TODOManagementService todoManagementService;

    @Autowired
    public void setTodoManagementService(TODOManagementService todoManagementService) {
        this.todoManagementService = todoManagementService;
    }

    @GetMapping("/todos/{taskId}")
    ResponseEntity<TODOEntity> getTaskDetails(@PathVariable Long taskId) throws NoTODOTaskFoundException {
        return new ResponseEntity<>(todoManagementService.findByTaskId(taskId), HttpStatus.OK);
    }

    @PostMapping("/todos/add")
    ResponseEntity<String> addTodo(@RequestBody TodoTask todo) throws InvalidTODOTaskStatusException, TODOTaskMappingException {
        return new ResponseEntity<>(todoManagementService.addTodoItem(todo), HttpStatus.ACCEPTED);
    }
}
