package com.example.demo.repository.tasks;

import com.example.demo.model.auth.ERole;
import com.example.demo.model.auth.Role;
import com.example.demo.model.tasks.Task;
import com.example.demo.model.tasks.TaskExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskExecutorRepository extends JpaRepository<TaskExecutor, Long> {
//    Optional<TaskExecutor> findByTask_id(Long id);
}
