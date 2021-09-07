package org.opensource.todo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoUpdateDescRequest {

    @NotBlank(message = "Description is mandatory")
    private String description;
    @NotNull(message = "Task Id is mandatory")
    private Long id;
}