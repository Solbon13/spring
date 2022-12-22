package com.example.demo.repository.organization;

import com.example.demo.model.organization.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Optional<Organization> findById(Long id);
    Boolean existsByName(String name);
}
