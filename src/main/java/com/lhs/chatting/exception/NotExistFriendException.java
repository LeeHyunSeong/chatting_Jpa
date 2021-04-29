package com.lhs.chatting.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class NotExistFriendException extends RuntimeException {
    private HttpStatus status;
    
    public NotExistFriendException(HttpStatus status, Long friendId) {
        super(String.format("Can not fount Friend entity (friendId = %d)", friendId));
        this.status = status;
    }
    
}
