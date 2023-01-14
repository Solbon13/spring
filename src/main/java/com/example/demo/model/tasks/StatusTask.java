package com.example.demo.model.tasks;

import com.example.demo.model.Base;
import com.example.demo.model.organization.Organization;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
public class StatusTask extends Base {
    private String name;

    public StatusTask(String name) {
        super();
        this.name = name;
    }

    public StatusTask() {
    }

}
