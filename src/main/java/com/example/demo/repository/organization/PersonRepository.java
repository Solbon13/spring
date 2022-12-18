package com.example.demo.repository.organization;

import com.example.demo.model.organization.Menu;
import com.example.demo.model.organization.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
