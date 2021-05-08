package com.lhs.chatting.exception;

import com.lhs.chatting.model.entity.Friend;

public class FriendNotFoundException extends NotFoundException {
    public FriendNotFoundException(Object id) {
        super(Friend.builder().build(), String.format("Id = %s", String.valueOf(id)));
    }
}
