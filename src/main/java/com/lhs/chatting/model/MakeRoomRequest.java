package com.lhs.chatting.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MakeRoomRequest {
    List<Long> userIds;
}
