package com.example.demo.controllers.organization;

import com.example.demo.model.organization.Departament;
import com.example.demo.model.organization.Menu;
import com.example.demo.services.organization.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/menu")
public class MenuController {
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Menu> getList() {
        return menuService.getList();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Menu getOne(
            @PathVariable("id") Menu menu
    ) {
        return menu;
    }

    @PostMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Menu create(
            @RequestBody Menu menu
    ) {
        return  menuService.create(menu);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Menu update (
            @PathVariable("id") Menu menuFromDb,
            @RequestBody Menu menu,
            @PathVariable Long id
    ) {
        return menuService.update(menu, menuFromDb, Long.valueOf(id));
    };

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable("id") Menu menuFromDb,
            @PathVariable Long id
    ) {
        menuService.delete(menuFromDb, Long.valueOf(id));
    }
}
