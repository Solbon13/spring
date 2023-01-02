package com.example.demo.controllers.organization;

import com.example.demo.dto.auth.request.LoginRequest;
import com.example.demo.dto.auth.request.SignupRequest;
import com.example.demo.model.auth.User;
import com.example.demo.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('LOCAL_ADMIN') or hasRole('ADMIN')")
    public List<User> getList() {
        return userService.getList();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('LOCAL_ADMIN') or hasRole('ADMIN')")
    public User getOne(
            @PathVariable("id") User user
    ) {
        return user;
    }

    @PostMapping
    @PreAuthorize("hasRole('LOCAL_ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<?> create(
            @Valid @RequestBody SignupRequest signUpRequest
    ) {
        return  userService.create(signUpRequest);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('LOCAL_ADMIN') or hasRole('ADMIN')")
    public User update (
            @PathVariable("id") User userFromDb,
            @RequestBody User user,
            @PathVariable Long id
    ) {
        return userService.update(user, userFromDb, Long.valueOf(id));
    };

    @PutMapping("/profile/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('LOCAL_ADMIN') or hasRole('ADMIN')")
    public ResponseEntity updateProfile (
            @PathVariable("id") User userFromDb,
            @RequestBody User user,
            @PathVariable Long id
    ) {
        return userService.updateProfile(user, userFromDb, Long.valueOf(id));
    };

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('LOCAL_ADMIN') or hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable("id") User userFromDb,
            @PathVariable Long id
    ) {
        userService.delete(userFromDb, Long.valueOf(id));
    }
}
