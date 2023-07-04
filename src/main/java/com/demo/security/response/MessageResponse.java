package com.demo.security.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageResponse {
    private Integer status;
    private String code;
    private String message;
    private Object data;
}
