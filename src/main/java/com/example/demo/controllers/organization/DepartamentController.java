package com.example.demo.controllers.organization;

import com.example.demo.dto.organization.request.DepartamentRequest;
import com.example.demo.model.organization.Departament;
import com.example.demo.model.organization.Organization;
import com.example.demo.services.organization.DepartamentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/departament")
public class DepartamentController {
    private final DepartamentService departamentService;

    public DepartamentController(DepartamentService departamentService) {
        this.departamentService = departamentService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('LOCAL_ADMIN') or hasRole('ADMIN')")
    public List<Departament> getList() {
        return departamentService.getList();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('LOCAL_ADMIN') or hasRole('ADMIN')")
    public Departament getOne(
            @PathVariable("id") Departament departament
    ) {
        return departament;
    }

    @PostMapping
    @PreAuthorize("hasRole('LOCAL_ADMIN') or hasRole('ADMIN')")
    public ResponseEntity create(
            @RequestBody DepartamentRequest departamentRequest
    ) {
        return  departamentService.create(departamentRequest);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('LOCAL_ADMIN') or hasRole('ADMIN')")
    public Departament update (
            @PathVariable("id") Departament departamentFromDb,
            @RequestBody Departament departament,
            @PathVariable Long id
    ) {
        return departamentService.update(departament, departamentFromDb, Long.valueOf(id));
    };

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('LOCAL_ADMIN') or hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable("id") Departament departamentFromDb,
            @PathVariable Long id
    ) {
        departamentService.delete(departamentFromDb, Long.valueOf(id));
    }
}
