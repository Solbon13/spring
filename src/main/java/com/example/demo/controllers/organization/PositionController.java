package com.example.demo.controllers.organization;

import com.example.demo.model.organization.Departament;
import com.example.demo.model.organization.Position;
import com.example.demo.services.organization.PositionService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/position")
public class PositionController {
    private final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Position> getList() {
        return positionService.getList();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Position getOne(
            @PathVariable("id") Position position
    ) {
        return position;
    }

    @PostMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Position create(
            @RequestBody Position position
    ) {
        return  positionService.create(position);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Position update (
            @PathVariable("id") Position positionFromDb,
            @RequestBody Position position,
            @PathVariable Long id
    ) {
        return positionService.update(position, positionFromDb, Long.valueOf(id));
    };

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable("id") Position positionFromDb,
            @PathVariable Long id
    ) {
        positionService.delete(positionFromDb, Long.valueOf(id));
    }
}
