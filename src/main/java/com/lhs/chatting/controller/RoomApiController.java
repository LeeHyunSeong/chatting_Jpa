package com.lhs.chatting.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lhs.chatting.entity.Member;
import com.lhs.chatting.entity.Room;
import com.lhs.chatting.model.RoomRequest;
import com.lhs.chatting.service.RoomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomApiController {
    private final RoomService roomService;

    @PostMapping("/{userId}")
    public List<Member> makeRoom(@RequestBody RoomRequest request, @PathVariable Long userId) {
        roomService.makeRoom(request.getRoomName(), request.getUserIds());

        return roomService.getRooms(userId);
    }

    @GetMapping("/{userId}")
    public List<Member> getRooms(@PathVariable Long userId) {
        return roomService.getRooms(userId);
    }
}