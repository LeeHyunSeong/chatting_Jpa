package com.lhs.chatting.exception;

import org.springframework.http.HttpStatus;

import com.lhs.chatting.model.type.EntityType;

import lombok.Getter;
import net.bytebuddy.implementation.bind.annotation.Super;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(Long userId) {
        super(EntityType.USER, userId);
    }
    
    public UserNotFoundException(String email){
        super(EntityType.USER, email);
    }
}
