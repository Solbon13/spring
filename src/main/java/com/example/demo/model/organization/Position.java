package com.example.demo.model.organization;

import com.example.demo.model.Base;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Position extends Base {
    private String name;

    public Position(String name) {
        super();
        this.name = name;
    }

    public Position() {
    }

}
