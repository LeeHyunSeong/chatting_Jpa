package com.lhs.chatting.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(NotExistUserException.class)
    public ResponseEntity<Map<String, Object>> handler(NotExistUserException e) {
        Map<String, Object> resBody = new HashMap<>();
        resBody.put("message", e.getMessage());
        
        return new ResponseEntity<>(resBody, e.getStatus());
    }
    
    @ExceptionHandler(NotExistFriendException.class)
    public ResponseEntity<Map<String, Object>> handler(NotExistFriendException e) {
        Map<String, Object> resBody = new HashMap<>();
        resBody.put("message", e.getMessage());
        
        return new ResponseEntity<>(resBody, e.getStatus());
    }
}