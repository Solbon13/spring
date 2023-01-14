package com.example.demo.services.organization;

import com.example.demo.dto.general.response.MessageResponse;
import com.example.demo.dto.organization.request.MenuSiteRequest;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.organization.MenuSite;
import com.example.demo.model.organization.Organization;
import com.example.demo.repository.organization.MenuSiteRepository;
import com.example.demo.repository.organization.OrganizationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuSiteService {
    private final MenuSiteRepository menuSiteRepository;
    private final OrganizationRepository organizationRepository;

    public MenuSiteService(MenuSiteRepository menuSiteRepository, OrganizationRepository organizationRepository) {
        this.menuSiteRepository = menuSiteRepository;
        this.organizationRepository = organizationRepository;
    }

    public List<MenuSite> getList() {
        return menuSiteRepository.findAll();
    }

    public ResponseEntity<MessageResponse> create(MenuSiteRequest menuSiteRequest) {
        Organization organization = organizationRepository.findById(menuSiteRequest.getOrg_id())
                .orElseThrow(() -> new RuntimeException("Ошибка: Организации нет."));

        MenuSite menuSite = new MenuSite(
                menuSiteRequest.getName(),
                organization
        );
        menuSiteRepository.save(menuSite);

        return ResponseEntity.ok(new MessageResponse("Меню сайта добавлен!"));
    }

    public ResponseEntity<MessageResponse> update(MenuSiteRequest menuSiteRequest, MenuSite menuSiteFromDb, Long id) {
        if (!menuSiteRepository.existsById(id)) {
            throw new NotFoundException(id);
        }
        if (menuSiteFromDb.getId() == id) {
            Organization organization = organizationRepository.findById(menuSiteRequest.getOrg_id())
                    .orElseThrow(() -> new RuntimeException("Ошибка: Организации нет."));
            menuSiteFromDb.setOrganization(organization);
            menuSiteFromDb.setName(menuSiteRequest.getName());
        }
        menuSiteRepository.save(menuSiteFromDb);
        return ResponseEntity.ok(new MessageResponse("Меню сайта изменена!"));
    }

    public void delete(MenuSite menuSiteFromDb, Long id) {
        if (!menuSiteRepository.existsById(id)) {
            throw new NotFoundException(id);
        }
        menuSiteRepository.delete(menuSiteFromDb);
    }
}
