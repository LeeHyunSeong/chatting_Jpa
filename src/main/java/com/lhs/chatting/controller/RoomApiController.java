package com.lhs.chatting.controller;

import com.lhs.chatting.model.Room;
import com.lhs.chatting.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/api/rooms")
@RequiredArgsConstructor
public class RoomApiController {
    private final RoomService roomService;

    @PostMapping
    public List<Room> makeRoom(@RequestParam("roomName") String name) {
        if (StringUtils.hasText(name)) {
            roomService.makeRoom(name);
        }
        return roomService.getRooms();
    }

    @GetMapping
    public List<Room> getRooms() {
        return roomService.getRooms();
    }

    @GetMapping("/{roomNumber}")
    public Room getRoom(@PathVariable Integer roomNumber) {
        return roomService.getRoom(roomNumber);
    }
}
