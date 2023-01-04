package com.example.demo.controllers.organization;

import com.example.demo.dto.auth.request.LoginRequest;
import com.example.demo.dto.auth.request.SignupRequest;
import com.example.demo.dto.auth.response.UserInfoResponse;
import com.example.demo.dto.organization.request.PersonRequest;
import com.example.demo.model.auth.User;
import com.example.demo.repository.auth.UserRepository;
import com.example.demo.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
//        return userService.getList().stream().map(UserInfoResponse::new).collect(Collectors.toList());
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
            @Valid @RequestBody PersonRequest personRequest
    ) {
        return  userService.create(personRequest);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('LOCAL_ADMIN') or hasRole('ADMIN')")
    public User update (
            @PathVariable("id") User userFromDb,
            @RequestBody PersonRequest personRequest,
            @PathVariable Long id
    ) {
        return userService.update(personRequest, userFromDb, Long.valueOf(id));
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
