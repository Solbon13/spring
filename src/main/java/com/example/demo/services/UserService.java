package com.example.demo.services;

import com.example.demo.dto.auth.request.LoginRequest;
import com.example.demo.dto.auth.request.SignupRequest;
import com.example.demo.dto.general.response.MessageResponse;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.auth.ERole;
import com.example.demo.model.auth.Role;
import com.example.demo.model.auth.User;
import com.example.demo.repository.auth.RoleRepository;
import com.example.demo.repository.auth.UserRepository;
import com.example.demo.security.services.MailSenderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private MailSenderService mailSenderService;

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);
        userRepository.save(user);

        return true;
    }

    public List<User> getList() {

        return userRepository.findAll();
    }

    public ResponseEntity<?> create(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Логин занят!"));
        }

        if (!signUpRequest.getEmail().equals("test@test.org"))
            if (userRepository.existsByEmail(signUpRequest.getEmail()))
                return ResponseEntity.badRequest().body(new MessageResponse("Email занят!"));

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ROLE_ADMIN":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "ROLE_LOCAL_ADMIN":
                        Role localAdminRole = roleRepository.findByName(ERole.ROLE_LOCAL_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(localAdminRole);

                        break;
                    case "ROLE_MODERATOR":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        user.setActive(true);
        user.setActivationCode(UUID.randomUUID().toString());

        userRepository.save(user);

        if (!signUpRequest.getEmail().equals("test@test.org"))
            if (!StringUtils.isEmpty(user.getEmail())) {
//      пока на сервер, потом переделать на клиента(ангуляр), а она при открытии сразу кидает get запрос на сервер. наверное так
                String message = String.format(
                        "Привет, %s\n" +
                                "Для активации пользователя перейдите по ссылке: http://localhost:8080/activate/%s",
                        user.getUsername(),
                        user.getActivationCode()
                );
                mailSenderService.send(user.getEmail(), "Activated code", message);
            }

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public User update(User user, User userFromDb, Long id) {
//       verification fields and rewrite fields
        if (!userRepository.existsById(id)) {
            throw new NotFoundException(id);
        }
        BeanUtils.copyProperties(user, userFromDb, "id");
        return userRepository.save(userFromDb);
    }

    public ResponseEntity updateProfile(User user, User userFromDb, Long valueOf) {
        //       verification fields and access and rewrite fields
        if (!StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
        }
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public void delete(User userFromDb, Long id) {
//        check records and field(delete) to true
        if (!userRepository.existsById(id)) {
            throw new NotFoundException(id);
        }
        userRepository.delete(userFromDb);
    }
}
