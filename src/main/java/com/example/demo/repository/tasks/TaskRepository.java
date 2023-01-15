package com.example.demo.repository.tasks;

import com.example.demo.model.organization.Person;
import com.example.demo.model.tasks.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Collection<Object> findByPerson(Optional<Person> person);
}
