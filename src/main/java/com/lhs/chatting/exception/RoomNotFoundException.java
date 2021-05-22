package com.lhs.chatting.exception;

import com.lhs.chatting.model.entity.Room;

public class RoomNotFoundException extends NotFoundException{
    public RoomNotFoundException(Long roomId) {
        super(Room.class, String.format("Id = %d", roomId));
    }
}
