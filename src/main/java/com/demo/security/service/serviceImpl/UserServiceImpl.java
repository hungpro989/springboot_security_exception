package com.demo.security.service.serviceImpl;

import com.demo.security.persistance.entity.ERole;
import com.demo.security.persistance.entity.Role;
import com.demo.security.persistance.entity.User;
import com.demo.security.persistance.mapper.UserAdapter;
import com.demo.security.persistance.model.UserModel;
import com.demo.security.payload.RegisterRequest;
import com.demo.security.persistance.repository.UserRepository;
import com.demo.security.service.RoleService;
import com.demo.security.service.UserService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder encoder;
    Gson gson = new Gson();
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username) {

        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
//    @Async
    public UserModel createUser(RegisterRequest registerRequest) {
        log.info("Async method createUser called");
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setStatus(true);
        user.setCreatedAt(new Date());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(encoder.encode(registerRequest.getPassword()));

        Set<String> strRole = registerRequest.getListRole();
        Set<Role> listRole = new HashSet<>();
        if(strRole.isEmpty()){
            // nếu không truyền gì vào, thì mặc định là role USER
            Role userRole = roleService.findByName(ERole.USER).orElseThrow(()->new RuntimeException("Error: Role is not found"));
            listRole.add((userRole));
        }else{
            // lựa chọn role khi tạo user
            strRole.forEach(role->{
                switch (role) {
                    case "ADMIN" -> {
                        Role adminRole = roleService.findByName(ERole.ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found" + registerRequest.getListRole()));
                        listRole.add(adminRole);
                    }
                    case "SALE" -> {
                        Role saleRole = roleService.findByName(ERole.SALE)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found" + registerRequest.getListRole()));
                        listRole.add(saleRole);
                    }
                    case "MARKETING" -> {
                        Role marketingRole = roleService.findByName(ERole.MARKETING)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found" + registerRequest.getListRole()));
                        listRole.add(marketingRole);
                    }
                    case "OPERATOR" -> {
                        Role operatorRole = roleService.findByName(ERole.OPERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found" + registerRequest.getListRole()));
                        listRole.add(operatorRole);
                    }
                    case "USER" -> {
                        Role userRole = roleService.findByName(ERole.USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found" + registerRequest.getListRole()));
                        listRole.add(userRole);
                    }
                }
            });

        }
        user.setListRoles(listRole);
        User usersave =  save(user);

        UserModel userModel = UserAdapter.userModelNoPassword(usersave);
        log.info("Async method createUser completed");
        if (userModel == null) {
            return null;
        }
        return userModel;
    }

    @Override
    public User add(User a) {

            return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public User edit(User a) {
        return null;
    }

    @Override
    public boolean remove(Long id) {
        return false;
    }
}
