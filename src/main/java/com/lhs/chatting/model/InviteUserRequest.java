package com.lhs.chatting.model;

import com.lhs.chatting.entity.FriendRelationType;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class InviteUserRequest {
    Long roomId;
    String roomAlias;
}
