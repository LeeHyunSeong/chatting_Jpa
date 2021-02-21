package com.lhs.chatting.repository;

import com.lhs.chatting.model.Room;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RoomRepository {
    private static int NEXT_ROOM_NUMBER = 0;
    private final Map<Integer, Room> roomMap = new HashMap<>();

    public Room saveByName(String name) {
        Room room = Room.builder()
                .name(name)
                .number(++NEXT_ROOM_NUMBER)
                .build();
        roomMap.put(room.getNumber(), room);
        return room;
    }

    public Room findByNumber(int number) {
        return roomMap.get(number);
    }

    public List<Room> findAll() {
        return new ArrayList<>(roomMap.values());
    }
}
