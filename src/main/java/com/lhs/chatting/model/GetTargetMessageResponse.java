package com.lhs.chatting.model;

import java.util.List;

import com.lhs.chatting.model.entity.Friend;
import com.lhs.chatting.model.entity.Message;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class GetTargetMessageResponse {
    
    private final List<Message> messages;
    
}
