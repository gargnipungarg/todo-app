package org.opensource.todo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TODO")
@Data
@NoArgsConstructor
public class TODOEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
