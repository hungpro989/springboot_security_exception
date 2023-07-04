package com.demo.security.exception;

import com.demo.security.response.MessageResponse;
import com.demo.security.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
@Slf4j
public class ExceptionAdvide {
    @ExceptionHandler
    public ResponseEntity<?> handleException(Exception ex){
        log.error("Unknown internal server error: "+ex.getMessage());
        log.error("Exception class: "+ex.getClass());
        log.error("Exception cause: "+ex.getCause());
        return new ResponseEntity<>(new MessageResponse(500, Constant.ERROR_100, ex.getMessage(),null), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler
    public ResponseEntity<?> handleCommonException(CommonException ex){
        log.error(String.format("Common error: %s %s %s", ex.getCode(), ex.getStatus(),ex.getMessage()));
        return new ResponseEntity<>(new MessageResponse(400, Constant.ERROR_104,ex.getMessage(),null),HttpStatus.BAD_GATEWAY);
    }
    @ExceptionHandler
    public ResponseEntity<?> handleValidateException(ValidateException ex){
        return new ResponseEntity<>(new MessageResponse(400, Constant.ERROR_103,"Validate input",ex.getMessageMap()),HttpStatus.BAD_REQUEST);

    }
}
