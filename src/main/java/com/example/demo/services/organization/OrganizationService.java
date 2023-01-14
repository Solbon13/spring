package com.example.demo.services.organization;

import com.example.demo.dto.general.response.MessageResponse;
import com.example.demo.dto.organization.request.OrganizationRequest;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.organization.Organization;
import com.example.demo.repository.organization.OrganizationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity create(OrganizationRequest organizationRequest) {
        if (organizationRepository.existsByName(organizationRequest.getName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Ошибка: Организация с таким наименованием есть в БД!"));
        }
        Organization organization = new Organization(
                organizationRequest.getName()
        );

        organizationRepository.save(organization);

        return ResponseEntity.ok(new MessageResponse("Организация добавлена!"));
    }

    public ResponseEntity<MessageResponse> update(Organization organization, Organization organizationFromDb, Long id) {
        if (!organizationRepository.existsById(id)) {
            throw new NotFoundException(id);
        }
        BeanUtils.copyProperties(organization, organizationFromDb, "id");
        organizationRepository.save(organizationFromDb);
        return ResponseEntity.ok(new MessageResponse("Организация изменена!"));
    }

    public void delete(Organization organizationFromDb, Long id) {
        if (!organizationRepository.existsById(id)) {
            throw new NotFoundException(id);
        }
        organizationRepository.delete(organizationFromDb);
    }
}
