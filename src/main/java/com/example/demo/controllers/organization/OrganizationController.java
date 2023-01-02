package com.example.demo.controllers.organization;

import com.example.demo.dto.organization.request.OrganizationRequest;
import com.example.demo.model.auth.User;
import com.example.demo.model.organization.Organization;
import com.example.demo.services.organization.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('LOCAL_ADMIN') or hasRole('ADMIN')")
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
    public ResponseEntity create(
            @RequestBody OrganizationRequest organizationRequest,
            @AuthenticationPrincipal User user
    ) {
        return organizationService.create(organizationRequest);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('LOCAL_ADMIN') or hasRole('ADMIN')")
    public Organization update(
            @PathVariable("id") Organization organizationFromDb,
            @RequestBody Organization organization,
            @PathVariable Long id
    ) {
        return organizationService.update(organization, organizationFromDb, Long.valueOf(id));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable("id") Organization organizationFromDb,
            @PathVariable Long id
    ) {
        System.out.println(id);
        organizationService.delete(organizationFromDb, Long.valueOf(id));
    }
}
