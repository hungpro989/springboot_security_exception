package com.demo.security.service.serviceImpl;

import com.demo.security.persistance.entity.ERole;
import com.demo.security.persistance.entity.Role;
import com.demo.security.persistance.repository.RoleRepository;
import com.demo.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private    RoleRepository roleRepository;
    @Override
    public Optional<Role> findByName(ERole name) {
        return roleRepository.findByName(name);
    }
}
