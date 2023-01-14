package com.example.demo.services.task;

import com.example.demo.model.tasks.StatusTask;
import com.example.demo.repository.tasks.StatusTaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusTaskService {

    private final StatusTaskRepository statusTaskRepository;

    public StatusTaskService(StatusTaskRepository statusTaskRepository) {
        this.statusTaskRepository = statusTaskRepository;
    }

    public List<StatusTask> getList() {
        return statusTaskRepository.findAll();
    }

}
