package com.lhs.chatting.model;

import java.time.LocalDateTime;

public interface ChatMessage {
    long getId();
    long getUserId();
    String getContents();
    LocalDateTime getCreatedTime();
}
