package com.example.demo.repository.auth;

import com.example.demo.model.organization.Departament;
import com.example.demo.model.organization.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
