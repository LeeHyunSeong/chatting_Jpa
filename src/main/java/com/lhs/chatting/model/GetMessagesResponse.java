package com.lhs.chatting.model;

import java.util.List;

import com.lhs.chatting.model.entity.Friend;
import com.lhs.chatting.model.entity.Message;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetMessagesResponse {
       
    private final int displayedMsgNum;
    private final List<Message> messages;
    
}
