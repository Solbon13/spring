package com.example.demo.repository.tasks;

import com.example.demo.model.tasks.StatusTask;
import com.example.demo.model.tasks.TypeTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeTaskRepository extends JpaRepository<TypeTask, Long> {
    Boolean existsByName(String name);
}
