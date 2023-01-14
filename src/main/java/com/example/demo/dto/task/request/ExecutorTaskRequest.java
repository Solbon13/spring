package com.example.demo.dto.task.request;

import com.example.demo.model.Base;
import com.example.demo.model.organization.Person;
import com.example.demo.model.tasks.StatusTask;
import com.example.demo.model.tasks.Task;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
public class ExecutorTaskRequest extends Base {
    // todo продумать еще
    private Long id;
    private Long status_id;
    private Long person_id;
    private Long task_id;
}
