package com.example.demo.controllers.tasks;

import com.example.demo.dto.organization.request.OrganizationRequest;
import com.example.demo.model.auth.User;
import com.example.demo.model.organization.Organization;
import com.example.demo.model.tasks.StatusTask;
import com.example.demo.model.tasks.TypeTask;
import com.example.demo.services.task.StatusTaskService;
import com.example.demo.services.task.TypeTaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/type_task")
public class TypeTaskController {
    private final TypeTaskService typeTaskService;

    public TypeTaskController(TypeTaskService typeTaskService) {
        this.typeTaskService = typeTaskService;
    }


    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('LOCAL_ADMIN') or hasRole('ADMIN')")
    public List<TypeTask> getList() {
        return typeTaskService.getList();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public TypeTask getOne(
            @PathVariable("id") TypeTask typeTask
    ) {
        return typeTask;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity create(
            @RequestBody TypeTask typeTask,
            @AuthenticationPrincipal User user
    ) {
        return typeTaskService.create(typeTask);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity update(
            @PathVariable("id") TypeTask typeTaskFromDb,
            @RequestBody TypeTask typeTask,
            @PathVariable Long id
    ) {
        return typeTaskService.update(typeTask, typeTaskFromDb, Long.valueOf(id));
    }

//    @DeleteMapping("{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void delete(
//            @PathVariable("id") Organization organizationFromDb,
//            @PathVariable Long id
//    ) {
//        System.out.println(id);
//        organizationService.delete(organizationFromDb, Long.valueOf(id));
//    }
}
