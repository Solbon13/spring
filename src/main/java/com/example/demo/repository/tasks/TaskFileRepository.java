package com.example.demo.repository.tasks;

import com.example.demo.model.tasks.Task;
import com.example.demo.model.tasks.TaskFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskFileRepository extends JpaRepository<TaskFile, Long> {}
