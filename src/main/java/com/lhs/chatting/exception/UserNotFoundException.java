package com.lhs.chatting.exception;

import com.lhs.chatting.model.entity.User;

import java.util.Collection;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(Long userId) {
        super(User.class, String.format("Id = %d", userId));
    }

    public UserNotFoundException(Collection<Long> userIds) {
        super(User.class, String.format("Ids = %s", userIds.toString()));
    }

    public UserNotFoundException(String email) {
        super(User.class, String.format("Email = %s", email));
    }
}
