package com.demo.security.persistance.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserModel {
    private Long id;

    private String username;

    private String password;

    private String email;

    private Boolean status;

    private Date createdAt;

    private Date updatedAt;

    private List<String> listRole;
}
