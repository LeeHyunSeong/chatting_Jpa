package com.lhs.chatting.exception;

import org.springframework.http.HttpStatus;

import com.lhs.chatting.model.type.EntityType;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException{
    private HttpStatus status;
    
    public NotFoundException(EntityType type, Long Id) {
        super(String.format("Can not fount %s entity (Id = %d)", type, Id));
        this.status = HttpStatus.NOT_FOUND;
    }

    public NotFoundException(EntityType type, String email) {
        super(String.format("Can not fount %s entity (email = %s)", type, email));
        this.status = HttpStatus.NOT_FOUND;
    }
}
