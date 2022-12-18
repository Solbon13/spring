package com.example.demo.model.organization;

import com.example.demo.model.Base;

import javax.persistence.Entity;

@Entity
public class Position extends Base {
    private String name;

    public Position(String name) {
        super();
        this.name = name;
    }

    public Position() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
