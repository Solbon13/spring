package com.example.demo.controllers.tasks;

import com.example.demo.model.tasks.StatusTask;
import com.example.demo.services.task.StatusTaskService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/status_task")
public class StatusTaskController {
    private final StatusTaskService statusTaskService;

    public StatusTaskController(StatusTaskService statusTaskService) {
        this.statusTaskService = statusTaskService;
    }


    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('LOCAL_ADMIN') or hasRole('ADMIN')")
    public List<StatusTask> getList() {
        return statusTaskService.getList();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('LOCAL_ADMIN') or hasRole('ADMIN')")
    public StatusTask getOne(
            @PathVariable("id") StatusTask statusTask
    ) {
        return statusTask;
    }

}
