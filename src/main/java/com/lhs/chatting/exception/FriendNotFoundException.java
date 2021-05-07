package com.lhs.chatting.exception;

import org.springframework.http.HttpStatus;

import com.lhs.chatting.model.type.EntityType;

import lombok.Getter;
import net.bytebuddy.implementation.bind.annotation.Super;

public class FriendNotFoundException extends NotFoundException {
    public FriendNotFoundException(Long friendId) {
        super(EntityType.FRIEND, friendId);
    }
}
