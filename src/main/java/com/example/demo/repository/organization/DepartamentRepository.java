package com.example.demo.repository.organization;

import com.example.demo.model.organization.Departament;
import com.example.demo.model.organization.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartamentRepository extends JpaRepository<Departament, Long> {
}
