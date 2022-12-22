package com.example.demo.services.organization;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.organization.Position;
import com.example.demo.repository.organization.PositionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionService {
    private final PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public List<Position> getList() {
        return positionRepository.findAll();
    }

    public Position create(Position position) {
        return positionRepository.save(position);
    }

    public Position update(Position position, Position positionFromDb, Long id) {
        if (!positionRepository.existsById(id)) {
            throw new NotFoundException(id);
        }
        BeanUtils.copyProperties(position, positionFromDb, "id");
        return positionRepository.save(positionFromDb);
    }

    public void delete(Position positionFromDb, Long id) {
        if (!positionRepository.existsById(id)) {
            throw new NotFoundException(id);
        }
        positionRepository.delete(positionFromDb);
    }
}
