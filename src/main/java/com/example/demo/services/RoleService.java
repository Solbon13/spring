package com.example.demo.services;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.organization.Position;
import com.example.demo.repository.auth.RoleRepository;
import com.example.demo.repository.organization.PositionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.example.demo.model.auth.Role;


import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getList() {
        return roleRepository.findAll();
    }

}
