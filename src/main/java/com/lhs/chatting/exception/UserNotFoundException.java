package com.lhs.chatting.exception;

import com.lhs.chatting.model.entity.User;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(Object id) {
        super(User.builder().build(), String.format("Id = %s", String.valueOf(id)));
    }

    public UserNotFoundException(String email) {
        super(User.builder().build(), String.format("Email = %s", email));
    }
}