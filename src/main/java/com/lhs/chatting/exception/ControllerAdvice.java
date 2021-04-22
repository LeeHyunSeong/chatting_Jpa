package com.lhs.chatting.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(NotExistedUserException.class)
    public ResponseEntity<Map<String, Object>> handler(NotExistedUserException e) {
        Map<String, Object> resBody = new HashMap<>();
        resBody.put("message", e.getMessage());
        
        return new ResponseEntity<>(resBody, e.getStatus());
    }
}