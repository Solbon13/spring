package com.example.demo.controllers.organization;

import com.example.demo.dto.general.response.MessageResponse;
import com.example.demo.dto.organization.request.MenuSiteRequest;
import com.example.demo.model.organization.MenuSite;
import com.example.demo.services.organization.MenuSiteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/menu")
public class MenuSiteController {
    private final MenuSiteService menuSiteService;

    public MenuSiteController(MenuSiteService menuSiteService) {
        this.menuSiteService = menuSiteService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('LOCAL_ADMIN') or hasRole('ADMIN')")
    public List<MenuSite> getList() {
        return menuSiteService.getList();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('LOCAL_ADMIN') or hasRole('ADMIN')")
    public MenuSite getOne(
            @PathVariable("id") MenuSite menuSite
    ) {
        return menuSite;
    }

    @PostMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('LOCAL_ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> create(
            @RequestBody MenuSiteRequest menuSiteRequest
    ) {
        return  menuSiteService.create(menuSiteRequest);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('LOCAL_ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> update (
            @PathVariable("id") MenuSite menuSiteFromDb,
            @RequestBody MenuSiteRequest menuSiteRequest,
            @PathVariable Long id
    ) {
        return menuSiteService.update(menuSiteRequest, menuSiteFromDb, Long.valueOf(id));
    };

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('LOCAL_ADMIN') or hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable("id") MenuSite menuSiteFromDb,
            @PathVariable Long id
    ) {
        menuSiteService.delete(menuSiteFromDb, Long.valueOf(id));
    }
}
