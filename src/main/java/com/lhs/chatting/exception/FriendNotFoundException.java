package com.lhs.chatting.exception;

import com.lhs.chatting.model.entity.Friend;

public class FriendNotFoundException extends NotFoundException {
    public FriendNotFoundException(Long friendId) {
        super(Friend.class, String.format("Id = %s", friendId));
    }
}
