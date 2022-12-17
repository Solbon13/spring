package com.example.demo.repository.auth;

import com.example.demo.model.auth.User;
import com.example.demo.model.organization.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
