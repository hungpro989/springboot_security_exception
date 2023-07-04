package com.demo.security.service;

import com.demo.security.persistance.entity.User;
import com.demo.security.persistance.model.UserModel;
import com.demo.security.payload.RegisterRequest;

public interface UserService extends BaseService<User, Long> {
    User findByUsername (String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    User save(User user);

    UserModel createUser(RegisterRequest registerRequest);
}
