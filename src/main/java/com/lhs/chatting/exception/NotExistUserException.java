package com.lhs.chatting.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class NotExistUserException extends RuntimeException {
    private HttpStatus status;
    
    public NotExistUserException(HttpStatus status, Long userId) {
        super(String.format("Can not fount User entity (userId = %d)", userId));
        this.status = status;
    }

    public NotExistUserException(HttpStatus status, String email) {
        super(String.format("Can not fount User entity (email = %s)", email));
        this.status = status;
    }

}
