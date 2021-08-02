package com.lhs.chatting.model;

import java.util.List;

import com.lhs.chatting.model.entity.Message;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatMessages {
    private int displayedMsgNum;
    private List<Message> messages;
}
