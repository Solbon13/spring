package com.example.demo.repository.tasks;

import com.example.demo.model.tasks.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {}
