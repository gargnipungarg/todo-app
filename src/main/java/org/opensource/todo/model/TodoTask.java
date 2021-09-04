package org.opensource.todo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.opensource.todo.constants.TaskStatuses;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoTask {

    private String id;
    private String description;
    private TaskStatuses status;
    private Date creationDate;
    private Date completionDate;
    private Date dueDate;
}