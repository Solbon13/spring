package com.example.demo.model.organization;

import com.example.demo.model.Base;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
public class MenuSite extends Base {
    private String name;

    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;

    public MenuSite(String name, Organization organization) {
        super();
        this.name = name;
        this.organization = organization;
    }

    public MenuSite() {
    }

}
