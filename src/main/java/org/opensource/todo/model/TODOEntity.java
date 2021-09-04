package org.opensource.todo.model;

import lombok.*;
import org.opensource.todo.constants.TaskStatuses;

import javax.persistence.*;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "TODO")
@Data
@NoArgsConstructor
public class TODOEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @Column(name = "creationDate")
    private Date creationDate;

    @Column(name = "dueDate")
    private Date dueDate;

    @Column(name = "completionDate")
    private Date completionDate;

}
