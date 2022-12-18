package com.example.demo.services;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.organization.Organization;
import com.example.demo.repository.organization.OrganizationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {
    private final OrganizationRepository organizationRepository;

    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public List<Organization> getList() {
        return organizationRepository.findAll();
    }

    public Organization create(Organization organization) {
        return organizationRepository.save(organization);
    }

    public Organization update(Organization organization, Organization organizationFromDb, Long id) {
        if (!organizationRepository.existsById(id)) {
            throw new NotFoundException(id);
        }
        BeanUtils.copyProperties(organization, organizationFromDb, "id");
        return organizationRepository.save(organizationFromDb);
    }

    public void delete(Organization organizationFromDb, Long id) {
        if (!organizationRepository.existsById(id)) {
            throw new NotFoundException(id);
        }
        organizationRepository.delete(organizationFromDb);
    }
}
