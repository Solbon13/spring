package com.example.demo.model.post;

import com.example.demo.model.Base;
import com.example.demo.model.auth.Role;
import com.example.demo.model.organization.Departament;
import com.example.demo.model.organization.MenuSite;
import com.example.demo.model.organization.Person;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Post extends Base {
    private String title;
    private String description;
    @OneToOne(fetch = FetchType.EAGER)
    private MenuSite menuSite;
    @OneToOne(fetch = FetchType.EAGER)
    private Departament departament;
    @OneToOne(fetch = FetchType.EAGER)
    private Person person;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    private Set<PostFile> postFiles;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "post_likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private Set<Person> likes = new HashSet<>();

}
