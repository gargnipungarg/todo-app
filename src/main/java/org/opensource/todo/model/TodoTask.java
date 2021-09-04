package org.opensource.todo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoTask {

    private String description;
    private String status;
    private Date creationDate;
    private Date completionDate;
    private Date dueDate;
}