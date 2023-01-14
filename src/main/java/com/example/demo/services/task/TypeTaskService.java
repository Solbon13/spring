package com.example.demo.services.task;

import com.example.demo.dto.general.response.MessageResponse;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.organization.Organization;
import com.example.demo.model.tasks.StatusTask;
import com.example.demo.model.tasks.TypeTask;
import com.example.demo.repository.tasks.StatusTaskRepository;
import com.example.demo.repository.tasks.TypeTaskRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeTaskService {

    private final TypeTaskRepository typeTaskRepository;

    public TypeTaskService(TypeTaskRepository typeTaskRepository) {
        this.typeTaskRepository = typeTaskRepository;
    }

    public List<TypeTask> getList() {
        return typeTaskRepository.findAll();
    }

    public ResponseEntity create(TypeTask typeTask) {
        if (typeTaskRepository.existsByName(typeTask.getName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Ошибка: Тип задания с таким наименованием есть в БД!"));
        }
        TypeTask typeTaskDB = new TypeTask(
                typeTask.getName()
        );

        typeTaskRepository.save(typeTaskDB);

        return ResponseEntity.ok(new MessageResponse("Тип задания добавлена!"));
    }

    public ResponseEntity update(TypeTask typeTask, TypeTask typeTaskFromDb, Long valueOf) {
        if (!typeTaskRepository.existsById(valueOf)) {
            throw new NotFoundException(valueOf);
        }
        BeanUtils.copyProperties(typeTask, typeTaskFromDb, "id");
        typeTaskRepository.save(typeTaskFromDb);
        return ResponseEntity.ok(new MessageResponse("Тип задания изменена!"));
    }
}
