package com.example.demo.services.organization;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.organization.Person;
import com.example.demo.repository.organization.PersonRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getList() {
        return personRepository.findAll();
    }

    public Person create(Person person) {
        return personRepository.save(person);
    }

    public Person update(Person person, Person personFromDb, Long id) {
        if (!personRepository.existsById(id)) {
            throw new NotFoundException(id);
        }
        BeanUtils.copyProperties(person, personFromDb, "id");
        return personRepository.save(personFromDb);
    }

    public void delete(Person personFromDb, Long id) {
        if (!personRepository.existsById(id)) {
            throw new NotFoundException(id);
        }
        personRepository.delete(personFromDb);
    }
}
