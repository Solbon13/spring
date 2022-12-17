package com.example.demo.repository.auth;

import com.example.demo.model.organization.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Person, Long> {
}
