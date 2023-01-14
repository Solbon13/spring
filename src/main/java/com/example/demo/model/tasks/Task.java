package com.example.demo.model.tasks;

import com.example.demo.model.Base;
import com.example.demo.model.organization.Person;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Task extends Base {
    private String title;
    private String description;
    private String phone;
    private LocalDateTime deadline;
    @OneToOne(fetch = FetchType.EAGER)
    private Person person;
    @OneToMany()
    @JoinColumn(name = "task_id")
//    @JsonManagedReference
    private Set<TaskExecutor> executor = new HashSet<>();
    @OneToMany()
    @JoinColumn(name = "task_id")
//    @JsonManagedReference
    private Set<TaskFile> files = new HashSet<>();
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_task_id")
    private TypeTask typeTask;

    public Task(String title, String description, String phone, LocalDateTime deadline, Person person, TypeTask typeTask) {
        super();
        this.title = title;
        this.description = description;
        this.phone = phone;
        this.deadline = deadline;
        this.person = person;
        this.typeTask = typeTask;
    }

    public Task() {

    }
}
