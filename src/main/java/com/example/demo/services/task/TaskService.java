package com.example.demo.services.task;

import com.example.demo.dto.general.response.MessageResponse;
import com.example.demo.dto.task.request.ExecutorTaskRequest;
import com.example.demo.dto.task.request.TaskRequest;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.auth.User;
import com.example.demo.model.organization.Person;
import com.example.demo.model.tasks.*;
import com.example.demo.repository.organization.PersonRepository;
import com.example.demo.repository.tasks.*;
import com.example.demo.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final PersonRepository personRepository;
    private final StatusTaskRepository statusTaskRepository;
    private final TypeTaskRepository typeTaskRepository;
    private final TaskExecutorRepository taskExecutorRepository;
    private final TaskFileRepository taskFileRepository;

    @Value("${upload.path.task}")
    private String uploadPathTask;


    public TaskService(TaskRepository taskRepository, PersonRepository personRepository, StatusTaskRepository statusTaskRepository, TypeTaskRepository typeTaskRepository, TaskExecutorRepository taskExecutorRepository, TaskFileRepository taskFileRepository) {
        this.taskRepository = taskRepository;
        this.personRepository = personRepository;
        this.statusTaskRepository = statusTaskRepository;
        this.typeTaskRepository = typeTaskRepository;
        this.taskExecutorRepository = taskExecutorRepository;
        this.taskFileRepository = taskFileRepository;
    }

    public List<Object> getList(UserDetailsImpl currentUser) {
//        System.out.println(taskRepository.findAll().get(0).getId());
        Optional<Person> person = personRepository.findById(currentUser.getId());
        return taskRepository.findByPerson(person).stream().toList();
//        return taskRepository.findAll();
    }

    private void saveUploadedFiles(List<MultipartFile> files, Long taskId) throws IOException {
        for (MultipartFile file : files) {
            // todo проверить наличие записи по имени на сервере, а пока с клиента только новые будут приходить
            if (file.isEmpty()) {
                continue;
            }
            byte[] bytes = file.getBytes();
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            Path path = Paths.get(uploadPathTask + resultFilename);
            Files.write(path, bytes);

            TaskFile taskFile = new TaskFile(
                    file.getOriginalFilename(),
                    resultFilename,
                    taskId
            );
            taskFileRepository.save(taskFile);
        }
    }

    private void addExecutorTask(TypeTask typeTask, Task newTask, Set<ExecutorTaskRequest> executorTask) {
        Person person;
        for (ExecutorTaskRequest executor : executorTask) {
            if (null == executor.getId()) {
                person = personRepository.findById(executor.getPerson_id())
                        .orElseGet(null);
                StatusTask statusTask = statusTaskRepository.findById(executor.getStatus_id())
                        .orElseGet(null);
                if (person != null && typeTask != null) {
                    TaskExecutor taskExecutor = new TaskExecutor(
                            statusTask,
                            person,
                            newTask.getId()
                    );
                    taskExecutorRepository.save(taskExecutor);
                }
            }
        }
    }

    public ResponseEntity create(TaskRequest taskRequest, User user, MultipartFile[] files) throws IOException {
        Person person = personRepository.findById(taskRequest.getPerson_id())
                .orElseThrow(() -> new RuntimeException("Ошибка: Сотрудника нет."));
        TypeTask typeTask = typeTaskRepository.findById(taskRequest.getTypeTaskId())
                .orElseThrow(() -> new RuntimeException("Ошибка: Типа задания нет."));

        Task newTask = new Task(
                taskRequest.getTitle(),
                taskRequest.getDescription(),
                taskRequest.getPhone(),
                taskRequest.getDeadline(),
                person,
                typeTask
        );
        taskRepository.save(newTask);
        Set<ExecutorTaskRequest> executorTask = taskRequest.getExecutor();
        addExecutorTask(typeTask, newTask, executorTask);

        // Получить имя файла
        if (files != null) {
            String uploadedFileName = Arrays.stream(files).map(x -> x.getOriginalFilename())
                    .filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(" , "));
            if (!StringUtils.isEmpty(uploadedFileName)) {
                try {
                    saveUploadedFiles(Arrays.asList(files), newTask.getId());
                } catch (IOException e) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        }
        return ResponseEntity.ok(new MessageResponse("Задание создано!"));
    }

    public ResponseEntity<MessageResponse> update(Task task, TaskRequest taskRequest, Long valueOf, MultipartFile[] files) {
        if (!taskRepository.existsById(valueOf)) {
            throw new NotFoundException(valueOf);
        }
        System.out.println(valueOf);
        System.out.println(task.getId());
//        if (task.getId().equals(valueOf)) {
//            throw new NotFoundException(valueOf);
//        }
        Person person = personRepository.findById(taskRequest.getPerson_id())
                .orElseThrow(() -> new RuntimeException("Ошибка: Сотрудника нет."));
        TypeTask typeTask = typeTaskRepository.findById(taskRequest.getTypeTaskId())
                .orElseThrow(() -> new RuntimeException("Ошибка: Типа задания нет."));
        task.setTitle(taskRequest.getTitle());
        task.setPerson(person);
        task.setPhone(taskRequest.getPhone());
        task.setDescription(taskRequest.getDescription());
        task.setTypeTask(typeTask);
        task.setDeadline(taskRequest.getDeadline());
        taskRepository.save(task);

        Set<ExecutorTaskRequest> executorTask = taskRequest.getExecutor();
        Set<TaskExecutor> executorTaskFromDb = task.getExecutor();
//        удаление
        for (TaskExecutor executorFromDB : executorTaskFromDb) {
            for (ExecutorTaskRequest executor : executorTask) {
                if (executorFromDB.getPerson().getId() == executor.getPerson_id()) {
                    System.out.println("exit");
                    break;
                }
                taskExecutorRepository.delete(executorFromDB);
            }
        }
        addExecutorTask(typeTask, task, executorTask);

        // Получить имя файла
        String uploadedFileName = Arrays.stream(files).map(x -> x.getOriginalFilename())
                .filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(" , "));
        if (!StringUtils.isEmpty(uploadedFileName)) {
            try {
                saveUploadedFiles(Arrays.asList(files), task.getId());
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return ResponseEntity.ok(new MessageResponse("Задание изменено!"));
    }
}
