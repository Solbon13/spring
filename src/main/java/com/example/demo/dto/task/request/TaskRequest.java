package com.example.demo.dto.task.request;

import com.example.demo.model.Base;
import com.example.demo.model.organization.Person;
import com.example.demo.model.tasks.TypeTask;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class TaskRequest extends Base {
    private Long id;
    private String title;
    private String description;
    private String phone;
    private LocalDateTime deadline;
    private Set<ExecutorTaskRequest> executor;
    private Long person_id;
    private Long typeTaskId;
//    private MultipartFile files;
}
