package com.example.demo.model.auth;

import com.example.demo.model.Base;
import com.example.demo.model.auth.ERole;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role extends Base {

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    public Role() {
    }

    public Role(ERole name) {
        this.name = name;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }
}