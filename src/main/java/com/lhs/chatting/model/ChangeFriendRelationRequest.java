package com.lhs.chatting.model;

import com.lhs.chatting.model.type.FriendRelationType;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChangeFriendRelationRequest {
    private FriendRelationType relationType;
}
