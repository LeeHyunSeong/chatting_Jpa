package com.lhs.chatting.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FriendRequest {
    private Long userId;
    private Long friendId;
}
