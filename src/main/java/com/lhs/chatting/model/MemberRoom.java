package com.lhs.chatting.model;

import java.time.LocalDateTime;

public interface MemberRoom {
    long getRoomId();
    String getRoomAlias();
    LocalDateTime getLastEntranceTime();
}
