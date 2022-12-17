package com.example.demo.model.organization;

import com.example.demo.model.Base;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Organization extends Base {
    @NotBlank
    @Size(max = 200)
    private String name;

    public Organization(String name) {
        this.name = name;
    }

    public Organization() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
