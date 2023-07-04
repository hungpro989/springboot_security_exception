package com.demo.security.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;
@Data
@SuperBuilder
public class JwtResponse {
    private String token;
    private String type="Bearer";
    private String username;
    private String email;
    private Boolean status;
    private List<String> listRole;

    public JwtResponse(String token, String username, String email, Boolean status, List<String> listRole) {
        this.token = token;
        this.username = username;
        this.email = email;
        this.status = status;
        this.listRole = listRole;
    }


}
