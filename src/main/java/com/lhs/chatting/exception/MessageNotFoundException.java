package com.lhs.chatting.exception;

import com.lhs.chatting.model.entity.Message;

public class MessageNotFoundException extends NotFoundException{
    public MessageNotFoundException(Long messageId) {
        super(Message.class, String.format("Id = %d", messageId));
    }
}
