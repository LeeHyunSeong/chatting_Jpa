package com.lhs.chatting.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lhs.chatting.entity.Member;
import com.lhs.chatting.model.RoomRequest;
import com.lhs.chatting.service.RoomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomApiController {
    private final RoomService roomService;

    @PostMapping
    public void makeRoom(@RequestParam("request") RoomRequest request) {
        roomService.makeRoom(request.getUserIds(), request.getRoomName());
    }

    @GetMapping("/{roomId}/users/{userId}")
    public Member getRoom(@PathVariable Long roomId, @PathVariable Long userId) {
        return roomService.getRoom(roomId, userId);
    }
}