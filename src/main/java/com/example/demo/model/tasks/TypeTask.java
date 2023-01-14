package com.example.demo.model.tasks;

import com.example.demo.model.Base;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class TypeTask extends Base {
    private String name;

    public TypeTask(String name) {
        super();
        this.name = name;
    }

    public TypeTask() {
    }

}
