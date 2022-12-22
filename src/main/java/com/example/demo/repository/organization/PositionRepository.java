package com.example.demo.repository.organization;

import com.example.demo.model.organization.Person;
import com.example.demo.model.organization.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {
}
