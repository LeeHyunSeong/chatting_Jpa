package com.lhs.chatting.exception;

import com.lhs.chatting.model.entity.User;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(Long userId) {
        super(User.class, String.format("Id = %", userId));
    }

    public UserNotFoundException(String email) {
        super(User.class, String.format("Email = %s", email));
    }
}
