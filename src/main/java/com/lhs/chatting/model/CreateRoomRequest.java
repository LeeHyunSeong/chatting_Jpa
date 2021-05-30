package com.lhs.chatting.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateRoomRequest {
    List<Long> userIds;
}
