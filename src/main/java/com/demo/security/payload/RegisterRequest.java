package com.demo.security.payload;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;
@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private Boolean status;
    private Date createdAt;
    private Date updatedAt;
    private Set<String> listRole;
}
