package com.example.demo.controllers;

import com.example.demo.model.auth.User;
import com.example.demo.model.organization.Organization;
import com.example.demo.repository.auth.OrganizationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/organization")
public class OrganizationController {
    private final OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationController(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Organization> getList() {
        return organizationRepository.findAll();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Organization getOne(
            @PathVariable("id") Organization organization
    ) {
        return organization;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Organization create(
            @RequestBody Organization organization,
            @AuthenticationPrincipal User user
    ) {
        organization.setCreationDate();
        return organizationRepository.save(organization);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Organization update(
            @PathVariable("id") Organization organizationFromDb,
            @RequestBody Organization organization
    ) {

        BeanUtils.copyProperties(organization, organizationFromDb, "id");
        return organizationRepository.save(organizationFromDb);
    }
}
