package com.lhs.chatting.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class InviteUserRequest {
    Long userId;
    Long targetUserId;
    Long roomId;
}