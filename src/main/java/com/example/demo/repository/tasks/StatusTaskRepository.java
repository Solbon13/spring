package com.example.demo.repository.tasks;

import com.example.demo.model.tasks.StatusTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusTaskRepository extends JpaRepository<StatusTask, Long> {}
