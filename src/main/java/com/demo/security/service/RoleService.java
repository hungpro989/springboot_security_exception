package com.demo.security.service;

import com.demo.security.persistance.entity.ERole;
import com.demo.security.persistance.entity.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(ERole name);
}
