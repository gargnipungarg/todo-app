package org.opensource.todo.service;

import lombok.extern.slf4j.Slf4j;
import org.opensource.todo.exception.NoTODOTaskFoundException;
import org.opensource.todo.model.TODOEntity;
import org.opensource.todo.respository.TODOManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}
