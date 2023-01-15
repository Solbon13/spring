package com.example.demo.controllers.tasks;

import com.example.demo.dto.general.response.MessageResponse;
import com.example.demo.dto.task.request.TaskRequest;
import com.example.demo.model.auth.User;
import com.example.demo.model.tasks.Task;
import com.example.demo.security.services.UserDetailsImpl;
import com.example.demo.services.task.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('LOCAL_ADMIN') or hasRole('ADMIN')")
    public List<Object> getList(
            Authentication authentication
    ) {
        UserDetailsImpl currentUser = (UserDetailsImpl) authentication.getPrincipal();
        System.out.println("Id пользователя: " + currentUser.getId());
        return taskService.getList(currentUser);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('LOCAL_ADMIN') or hasRole('ADMIN')")
    public Task getOne(
            @PathVariable("id") Task task
    ) {
        return task;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('LOCAL_ADMIN') or hasRole('ADMIN')")
    public ResponseEntity create(
            @RequestPart("properties") TaskRequest taskRequest,
            @AuthenticationPrincipal User user,
            @RequestPart(value = "files", required = false) MultipartFile[] files
            ) throws IOException {
        return taskService.create(taskRequest, user, files);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> update (
            @PathVariable("id") Task task,
            @RequestPart("properties") TaskRequest taskRequest,
            @PathVariable Long id,
            @RequestPart(value = "files", required = false) MultipartFile[] files
    ) {
        return taskService.update(task, taskRequest, Long.valueOf(id), files);
    };

}
