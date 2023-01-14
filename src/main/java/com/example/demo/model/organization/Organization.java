package com.example.demo.model.organization;

import com.example.demo.model.Base;
import lombok.Data;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
public class Organization extends Base {
    @NotBlank
    @Size(max = 200)
    private String name;

    public Organization(String name) {
        super();
        this.name = name;
    }

    public Organization() {
    }

}
