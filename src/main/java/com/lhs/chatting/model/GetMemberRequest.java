package com.lhs.chatting.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetMemberRequest {
    Long userId;
    Long roomId;
}
