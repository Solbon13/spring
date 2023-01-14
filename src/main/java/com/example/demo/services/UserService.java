package com.example.demo.services;

import com.example.demo.dto.auth.request.LoginRequest;
import com.example.demo.dto.auth.request.SignupRequest;
import com.example.demo.dto.auth.response.UserInfoResponse;
import com.example.demo.dto.general.response.MessageResponse;
import com.example.demo.dto.organization.request.PersonRequest;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.auth.ERole;
import com.example.demo.model.auth.Role;
import com.example.demo.model.auth.User;
import com.example.demo.model.organization.Departament;
import com.example.demo.model.organization.Organization;
import com.example.demo.model.organization.Person;
import com.example.demo.model.organization.Position;
import com.example.demo.repository.auth.RoleRepository;
import com.example.demo.repository.auth.UserRepository;
import com.example.demo.repository.organization.DepartamentRepository;
import com.example.demo.repository.organization.PersonRepository;
import com.example.demo.repository.organization.PositionRepository;
import com.example.demo.security.services.MailSenderService;
import com.example.demo.services.organization.PersonService;
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
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    DepartamentRepository departamentRepository;
    @Autowired
    PositionRepository positionRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private MailSenderService mailSenderService;
    @Autowired
    private PersonRepository personRepository;

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

    public ResponseEntity<?> create(PersonRequest personRequest) {
        if (userRepository.existsByUsername(personRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Логин занят!"));
        }

        if (!personRequest.getEmail().equals("test@test.org"))
            if (userRepository.existsByEmail(personRequest.getEmail()))
                return ResponseEntity.badRequest().body(new MessageResponse("Email занят!"));

        Departament departament = departamentRepository.findById(personRequest.getDepartament_id())
                .orElseThrow(() -> new RuntimeException("Ошибка: Отдела нет."));
        Position position = positionRepository.findById(personRequest.getPosition_id())
                .orElseThrow(() -> new RuntimeException("Ошибка: Должности нет."));

        // Create new user's account
        User user = new User(personRequest.getUsername(),
                personRequest.getEmail(),
                encoder.encode(personRequest.getPassword()));

        Set<String> strRoles = personRequest.getRole();
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

        if (!personRequest.getEmail().equals("test@test.org"))
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


        Person person = new Person(
                personRequest.getFirstName(),
                personRequest.getLastName(),
                personRequest.getMiddleName(),
                user,
                departament,
                position);
        personRepository.save(person);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public User update(PersonRequest personRequest, User userFromDb, Long id) {
        //todo вывести дублирующий код
        if (!userRepository.existsById(id)) {
            throw new NotFoundException(id);
        }
        if (!StringUtils.isEmpty(personRequest.getPassword())){
            userFromDb.setPassword(encoder.encode(personRequest.getPassword()));
        }
        // todo проверка что емаил поменялся тогда активет код и активед фалс
        userFromDb.setEmail(personRequest.getEmail());
        userFromDb.setUsername((personRequest.getUsername()));
        Set<String> strRoles = personRequest.getRole();
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

        userFromDb.setRoles(roles);

        System.out.println("new person");
        Person person = personRepository.findById(personRequest.getId())
                .orElseGet(() -> new Person());
        System.out.println("new departament");
        Departament departament = departamentRepository.findById(personRequest.getDepartament_id())
                .orElseThrow(() -> new RuntimeException("Ошибка: Отдела нет."));
        System.out.println("new position");
        Position position = positionRepository.findById(personRequest.getPosition_id())
                .orElseThrow(() -> new RuntimeException("Ошибка: Должности нет."));
        System.out.println("person");
        person.setId(userFromDb.getId());
        person.setDepartament(departament);
        person.setPosition(position);
        person.setFirstName(personRequest.getFirstName());
        person.setLastName(personRequest.getLastName());
        person.setMiddleName(personRequest.getMiddleName());
        personRepository.save(person);

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
