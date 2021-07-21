package com.lhs.chatting.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
public class GetRoomsResponse {
    
    private final List<MemberRoom> members;
    
}
