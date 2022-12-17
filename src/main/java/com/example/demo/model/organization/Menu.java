package com.example.demo.model.organization;

import com.example.demo.model.Base;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Menu extends Base {
    private String name;

    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;

    public Menu(String name, Organization organization) {
        this.name = name;
        this.organization = organization;
    }

    public Menu() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
