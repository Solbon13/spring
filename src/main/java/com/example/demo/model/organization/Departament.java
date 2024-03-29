package com.example.demo.model.organization;

import com.example.demo.model.Base;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
public class Departament extends Base {
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "org_id")
    private Organization organization;

    public Departament(String name) {
        super();
        this.name = name;
    }

    public Departament() {
    }

}
