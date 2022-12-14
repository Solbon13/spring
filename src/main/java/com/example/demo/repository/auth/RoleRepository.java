package com.example.demo.repository.auth;

import java.util.Optional;

import com.example.demo.model.auth.ERole;
import com.example.demo.model.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
