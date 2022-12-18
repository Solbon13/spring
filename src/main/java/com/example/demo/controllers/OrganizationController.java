package com.example.demo.controllers;

import com.example.demo.model.auth.User;
import com.example.demo.model.organization.Organization;
import com.example.demo.repository.organization.OrganizationRepository;
import com.example.demo.services.OrganizationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/organization")
public class OrganizationController {
    private final OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Organization> getList() {
        return organizationService.getList();
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
        return organizationService.create(organization);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Organization update(
            @PathVariable("id") Organization organizationFromDb,
            @RequestBody Organization organization
    ) {
        return organizationService.update(organization, organizationFromDb, Long.valueOf("id"));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable("id") Organization organizationFromDb
    ) {
        organizationService.delete(organizationFromDb, Long.valueOf("id"));
    }
}
