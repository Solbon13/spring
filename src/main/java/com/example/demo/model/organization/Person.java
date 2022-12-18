package com.example.demo.model.organization;

import com.example.demo.model.Base;
import com.example.demo.model.auth.User;

import javax.persistence.*;

@Entity
public class Person {

    @Id
    private long id;
    private String fastName;
    private String lastName;
    private String middleName;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private User user;
    @OneToOne(fetch = FetchType.LAZY)
    private Departament departament;
    @OneToOne(fetch = FetchType.LAZY)
    private Position position;

    public Person(String fastName, String lastName, String middleName, User user, Departament departament, Position position) {
        super();
        this.fastName = fastName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.user = user;
        this.departament = departament;
        this.position = position;
    }

    public Person() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFastName() {
        return fastName;
    }

    public void setFastName(String fastName) {
        this.fastName = fastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Departament getDepartament() {
        return departament;
    }

    public void setDepartament(Departament departament) {
        this.departament = departament;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
