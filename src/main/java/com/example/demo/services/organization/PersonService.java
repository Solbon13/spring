package com.example.demo.services.organization;

import com.example.demo.dto.general.response.MessageResponse;
import com.example.demo.dto.organization.request.PersonRequest;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.auth.User;
import com.example.demo.model.organization.Departament;
import com.example.demo.model.organization.Person;
import com.example.demo.model.organization.Position;
import com.example.demo.repository.auth.UserRepository;
import com.example.demo.repository.organization.DepartamentRepository;
import com.example.demo.repository.organization.PersonRepository;
import com.example.demo.repository.organization.PositionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final DepartamentRepository departamentRepository;
    private final PositionRepository positionRepository;
    private final UserRepository userRepository;

    public PersonService(PersonRepository personRepository, DepartamentRepository departamentRepository, PositionRepository positionRepository, UserRepository userRepository) {
        this.personRepository = personRepository;
        this.departamentRepository = departamentRepository;
        this.positionRepository = positionRepository;
        this.userRepository = userRepository;
    }

    public List<Person> getList() {
        return personRepository.findAll();
    }

    public ResponseEntity<MessageResponse> create(PersonRequest personRequest) {
        Departament departament = departamentRepository.findById(personRequest.getDepartament_id())
                .orElseThrow(() -> new RuntimeException("Ошибка: Отдела нет."));
        Position position = positionRepository.findById(personRequest.getPosition_id())
                .orElseThrow(() -> new RuntimeException("Ошибка: Должности нет."));
        User user = userRepository.findById(personRequest.getId())
                .orElseThrow(() -> new RuntimeException("Ошибка: Пользователя нет."));

        Person person = new Person(
                personRequest.getFirstName(),
                personRequest.getLastName(),
                personRequest.getMiddleName(),
                user,
                departament,
                position
        );
        personRepository.save(person);
        return ResponseEntity.ok(new MessageResponse("Сотрудник добавлен!"));
    }

    public ResponseEntity<MessageResponse> update(Person person, Person personFromDb, Long id) {
        if (!personRepository.existsById(id)) {
            throw new NotFoundException(id);
        }
        BeanUtils.copyProperties(person, personFromDb, "id");
        personRepository.save(personFromDb);
        return ResponseEntity.ok(new MessageResponse("Данные сотрудника изменены!"));
    }

    public void delete(Person personFromDb, Long id) {
        if (!personRepository.existsById(id)) {
            throw new NotFoundException(id);
        }
        personRepository.delete(personFromDb);
    }

    public ResponseEntity<MessageResponse> updateProfile(Person person, Person personFromDb, Long id) {
        if (!personRepository.existsById(id)) {
            throw new NotFoundException(id);
        }
        BeanUtils.copyProperties(person, personFromDb, "id");
        personRepository.save(personFromDb);
        return ResponseEntity.ok(new MessageResponse("Данные изменены!"));
    }
}
