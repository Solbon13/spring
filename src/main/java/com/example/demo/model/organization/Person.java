package com.example.demo.model.organization;

import com.example.demo.model.Base;
import com.example.demo.model.auth.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Person {

    @Id
    private long id;
    private String firstName;
    private String lastName;
    private String middleName;
    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "id")
    @JsonBackReference
    private User user;
    @OneToOne(fetch = FetchType.EAGER)
    private Departament departament;
    @OneToOne(fetch = FetchType.EAGER)
    private Position position;

    public Person(String firstName, String lastName, String middleName, User user, Departament departament, Position position) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.user = user;
        this.departament = departament;
        this.position = position;
    }

    public Person() {
    }

}
