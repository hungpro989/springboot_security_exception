package com.demo.security.persistance.mapper;

import com.demo.security.persistance.entity.User;
import com.demo.security.persistance.model.UserModel;

public class UserAdapter {
    public static UserModel UserModel(User user){
        new UserModel();
        return UserModel.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
    public static User user(UserModel userModel){
        new User();
        return User.builder()
                .id(userModel.getId())
                .username(userModel.getUsername())
                .password(userModel.getPassword())
                .email(userModel.getEmail())
                .status(userModel.getStatus())
                .createdAt(userModel.getCreatedAt())
                .updatedAt(userModel.getUpdatedAt())
                .build();
    }

    public static UserModel userModelNoPassword(User user){
        new UserModel();
        return UserModel.builder()
                .id(user.getId())
                .username(user.getUsername())
//                .password(user.getPassword())
                .email(user.getEmail())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
