package com.lhs.chatting.model;

import java.util.List;

import com.lhs.chatting.model.entity.Friend;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetAllFriendResponse {

    private final List<Friend> friends;

}
