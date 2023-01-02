package com.example.demo.controllers.organization;

import com.example.demo.dto.general.response.MessageResponse;
import com.example.demo.dto.organization.request.PersonRequest;
import com.example.demo.model.auth.User;
import com.example.demo.model.organization.Person;
import com.example.demo.services.organization.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/person")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('LOCAL_ADMIN') or hasRole('ADMIN')")
    public List<Person> getList() {
        return personService.getList();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('LOCAL_ADMIN') or hasRole('ADMIN')")
    public Person getOne(
            @PathVariable("id") Person person
    ) {
        return person;
    }

    @PostMapping
    @PreAuthorize("hasRole('LOCAL_ADMIN') or hasRole('ADMIN')")
    public Person create(
            @RequestBody PersonRequest person
    ) {
        return personService.create(person);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('LOCAL_ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> update (
            @PathVariable("id") Person personFromDb,
            @RequestBody Person person,
            @PathVariable Long id
    ) {
        return personService.update(person, personFromDb, Long.valueOf(id));
    };

    @PutMapping("/profile/{id}")
    @PreAuthorize("(hasRole('USER') or hasRole('MODERATOR') or hasRole('LOCAL_ADMIN') or hasRole('ADMIN'))")
    // todo добавить проверку, что меняется профиль пользователя от которого пришел запрос
    public ResponseEntity<MessageResponse> updateProfile (
            @PathVariable("id") Person personFromDb,
            @RequestBody Person person,
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        if (user.getId() == id)
            return personService.updateProfile(person, personFromDb, Long.valueOf(id));
        return ResponseEntity.badRequest().body(new MessageResponse("Ошибка: Нельзя изменить данные другого пользователя."));
    };

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('LOCAL_ADMIN') or hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable("id") Person personFromDb,
            @PathVariable Long id
    ) {
        personService.delete(personFromDb, Long.valueOf(id));
    }
}
