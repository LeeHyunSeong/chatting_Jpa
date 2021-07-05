package com.lhs.chatting.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class GetRoomListResponse {
    
    private final List<RoomInfoResponse> members;
    
}
