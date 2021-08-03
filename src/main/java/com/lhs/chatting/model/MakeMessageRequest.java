package com.lhs.chatting.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MakeMessageRequest {
    Long roomId;
    Long userId;
    String contents;
}
