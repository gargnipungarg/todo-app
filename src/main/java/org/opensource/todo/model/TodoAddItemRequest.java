package org.opensource.todo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoAddItemRequest {

    @NotBlank(message = "Description is mandatory")
    private String description;
    @NotNull(message = "Item Due date is mandatory")
    private Date dueDate;
}