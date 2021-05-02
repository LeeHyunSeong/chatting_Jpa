package com.lhs.chatting.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private HttpStatus status;
    
    public NotFoundException(HttpStatus status, String type, Long userId) {
        super(String.format("Can not fount %s entity (userId = %d)", type, userId));
        this.status = status;
    }

    public NotFoundException(HttpStatus status, String type, String email) {
        super(String.format("Can not fount %s entity (email = %s)", type, email));
        this.status = status;
    }

}
