package com.example.demo.services.organization;

import com.example.demo.dto.general.response.MessageResponse;
import com.example.demo.dto.organization.request.DepartamentRequest;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.organization.Departament;
import com.example.demo.model.organization.Organization;
import com.example.demo.repository.organization.DepartamentRepository;
import com.example.demo.repository.organization.OrganizationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartamentService {
    private final DepartamentRepository departamentRepository;
    private final OrganizationRepository organizationRepository;

    public DepartamentService(DepartamentRepository departamentRepository, OrganizationRepository organizationRepository) {
        this.departamentRepository = departamentRepository;
        this.organizationRepository = organizationRepository;
    }

    public List<Departament> getList() {
        return departamentRepository.findAll();
    }

    public ResponseEntity create(DepartamentRequest departamentRequest) {

        Organization organization = organizationRepository.findById(departamentRequest.getOrg_id())
                .orElseThrow(() -> new RuntimeException("Ошибка: Организации нет."));

        Departament departament = new Departament(departamentRequest.getName());
        departament.setOrganization(organization);
        System.out.println(departament);
        departamentRepository.save(departament);

        return ResponseEntity.ok(new MessageResponse("Отдел добавлен!"));
    }

    public ResponseEntity update(DepartamentRequest departamentRequest, Departament departamentFromDb, Long id) {
        if (!departamentRepository.existsById(id)) {
            throw new NotFoundException(id);
        }
        if (departamentFromDb.getId() == id) {
            Organization organization = organizationRepository.findById(departamentRequest.getOrg_id())
                    .orElseThrow(() -> new RuntimeException("Ошибка: Организации нет."));
            departamentFromDb.setOrganization(organization);
            departamentFromDb.setName(departamentRequest.getName());
        }
        departamentRepository.save(departamentFromDb);
        return ResponseEntity.ok(new MessageResponse("Отдел изменен!"));
    }

    public void delete(Departament departamentFromDb, Long id) {
        if (!departamentRepository.existsById(id)) {
            throw new NotFoundException(id);
        }
        departamentRepository.delete(departamentFromDb);
    }
}
