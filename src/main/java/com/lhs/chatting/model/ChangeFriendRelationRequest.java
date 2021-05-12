package com.lhs.chatting.model;

import com.lhs.chatting.model.type.FriendRelationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChangeFriendRelationRequest {

    private FriendRelationType relationType;

}
