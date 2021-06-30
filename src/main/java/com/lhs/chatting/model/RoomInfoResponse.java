package com.lhs.chatting.model;

import java.time.LocalDateTime;

import com.lhs.chatting.model.entity.Room;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class RoomInfoResponse {
    long roomId;
    String roomAlias;
    LocalDateTime lastEntranceTime;
}
