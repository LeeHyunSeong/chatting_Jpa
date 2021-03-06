package com.lhs.chatting.model;


import com.lhs.chatting.entity.FriendRelationType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChangeFriendRelationRequest {
    private Long userId;
    private FriendRelationType relationType;
}
