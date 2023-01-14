package com.example.demo.model.tasks;

import com.example.demo.model.Base;
import com.example.demo.model.organization.Person;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class TaskExecutor extends Base {
    //    задача, персона, статус
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    private StatusTask statusTask;
    @ManyToOne(fetch = FetchType.EAGER)
    private Person person;
//    @ManyToOne()
//    @JsonBackReference
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Long task_id;

    public TaskExecutor(StatusTask statusTask, Person person, Long task_id) {
        super();
        this.statusTask = statusTask;
        this.person = person;
        this.task_id = task_id;
    }

    public TaskExecutor() {

    }
}
